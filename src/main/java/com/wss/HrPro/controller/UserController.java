package com.wss.HrPro.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wss.HrPro.entity.po.AccountUser;
import com.wss.HrPro.entity.vo.AccountUserVo;
import com.wss.HrPro.entity.vo.AjaxResult;
import com.wss.HrPro.entity.vo.MsgResult;
import com.wss.HrPro.entity.vo.Query;
import com.wss.HrPro.service.AccountUserService;
import com.wss.HrPro.util.BeanCopyUtil;
import com.wss.HrPro.util.CsvImportUtil;
import com.wss.HrPro.util.MyThreadLocal;
import com.wss.HrPro.util.constans.MesgConst;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    @Qualifier("AccountUserServiceImpl")
    AccountUserService userService;


    @GetMapping()
    public ResponseEntity<Object> getList(@RequestParam(value = "minSalary", required = false) String minSalary,
                                          @RequestParam(value = "maxSalary", required = false) String maxSalary,
                                          @RequestParam(value = "offset", required = false) String offset,
                                          @RequestParam(value = "limit", required = false) String limit,
                                          @RequestParam(value = "sorting", required = false) String sorting,
                                          @RequestParam(value = "desc", required = false) String desc
    ) {
        try {
            int pStart = 0;
            int plimit = 0;
            if (StrUtil.isNotEmpty(offset)) {
                pStart = Integer.parseInt(offset);
            }
            if (StrUtil.isNotEmpty(limit)) {
                plimit = Integer.parseInt(limit);
            }
            Page<AccountUser> page = new Page<>(0, Long.MAX_VALUE);
            Query build = Query.builder().sorting(sorting)
                    .maxSalary(maxSalary)
                    .minSalary(minSalary)
                    .desc(desc)
                    .build();
            userService.pageQuery(page, build);
            List<AccountUser> list = page.getRecords();
            if (plimit == 0) {
                list = list.subList(pStart, list.size());
            } else if (pStart + plimit <= list.size()) {
                list = list.subList(pStart, pStart + plimit);
            } else {
                if (pStart>list.size()){
                    return ResponseEntity.status(200).body(AjaxResult.success(new ArrayList<AccountUser>()));
                }
                list = list.subList(pStart, list.size());
            }
            return ResponseEntity.status(200).body(AjaxResult.success(list));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(MsgResult.error(MesgConst.BADPARAMETER));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") String id) {
        AccountUser user = userService.getById(id);
        if (null != user) {
            AccountUserVo vo = new AccountUserVo();
            BeanUtils.copyProperties(user, vo);
            return ResponseEntity.status(200).body(vo);
        }
        return ResponseEntity.status(400).body(MesgConst.NO_EMPLOYEE);
    }

    @PostMapping
    public ResponseEntity<MsgResult> create(@RequestBody AccountUserVo uservo) throws URISyntaxException {
        if (null != uservo) {
            AccountUser user = new AccountUser();
            BeanUtils.copyProperties(uservo, user);
            if (userService.saveAcc(user)) {
                return ResponseEntity.status(200).body(MsgResult.success(MesgConst.SUCCESS_CREATED));
            }
            Map<String, String> codeMap = MyThreadLocal.get();
            MyThreadLocal.remove();
            for (Map.Entry<String, String> stringStringEntry : codeMap.entrySet()) {
                return ResponseEntity.status(400).body(MsgResult.error(stringStringEntry.getValue()));
            }
        }
        return ResponseEntity.status(400).body(MsgResult.error(MesgConst.NO_EMPLOYEE));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MsgResult> update(@PathVariable("id") String id, @RequestBody AccountUserVo uservo) throws URISyntaxException {
        if (null == userService.getById(id)) {
            return ResponseEntity.status(400).body(MsgResult.error(MesgConst.NO_EMPLOYEE));
        }
        if (null != uservo) {
            AccountUser user = new AccountUser();
            BeanUtils.copyProperties(uservo, user);
            user.setId(id);
            if (userService.update(user)) {
                return ResponseEntity.status(200).body(MsgResult.success(MesgConst.SUCCESS_UPDATED));
            }
            Map<String, String> codeMap = MyThreadLocal.get();
            MyThreadLocal.remove();
            for (Map.Entry<String, String> stringStringEntry : codeMap.entrySet()) {
                return ResponseEntity.status(400).body(MsgResult.error(stringStringEntry.getValue()));
            }
        }
        return ResponseEntity.status(400).body(MsgResult.error(MesgConst.NO_EMPLOYEE));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MsgResult> deleteUserById(@PathVariable("id") String id) {
        if (userService.removeById(id)) {
            return ResponseEntity.ok().body(MsgResult.success(MesgConst.SUCCESS_DELETED));
        }
        return ResponseEntity.status(400).body(MsgResult.error(MesgConst.NO_EMPLOYEE));
    }


    @PostMapping(value = "/upload")
    public ResponseEntity<MsgResult> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return ResponseEntity.status(400).body(MsgResult.error(MesgConst.FILE_EMPTY));
        }
//        String[] fileHeader = {"id", "login", "name", "salary", "startDa7777te"};
        File file = CsvImportUtil.uploadFile(multipartFile);
        List<CSVRecord> ll = null;
        try {
            ll = CsvImportUtil.read(file.getPath());
        } catch (IOException e) {
            return ResponseEntity.status(400).body(MsgResult.error(MesgConst.FILE_ERROR));
        }
        List<AccountUserVo> voList = new ArrayList<>();
        try {
            for (CSVRecord record : ll) {
                //support two date format input ,but when upload to database,it will store into yyyy-MM-dd
                voList.add(AccountUserVo.parse(record));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(MsgResult.error(e.getMessage()));
        } finally {
            file.delete();
        }
        List<AccountUser> list = (List<AccountUser>) BeanCopyUtil.copyList(voList, AccountUser.class);
        userService.uploadUser(list);
        Map<String, String> codeMap = MyThreadLocal.get();
        MyThreadLocal.remove();
        Set<String> keys = codeMap.keySet();
        for (String key : keys) {
            switch (key) {
                case "200": //a分支
                    return ResponseEntity.status(200).body(MsgResult.success(codeMap.get(key)));
                case "201": //b分支
                    return ResponseEntity.status(201).body(MsgResult.success(codeMap.get(key)));
                case "400": //c分支
                    return ResponseEntity.status(400).body(MsgResult.success(codeMap.get(key)));
            }
        }
        return ResponseEntity.status(200).body(MsgResult.success(""));
    }


}
