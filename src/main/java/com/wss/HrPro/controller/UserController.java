package com.wss.HrPro.controller;

import com.wss.HrPro.entity.po.AccountUser;
import com.wss.HrPro.entity.vo.AccountUserVo;
import com.wss.HrPro.entity.vo.AjaxResult;
import com.wss.HrPro.entity.vo.MsgResult;
import com.wss.HrPro.service.AccountUserService;
import com.wss.HrPro.util.BeanCopyUtil;
import com.wss.HrPro.util.CsvImportUtil;
import com.wss.HrPro.util.constans.HttpStatus;
import com.wss.HrPro.util.constans.MesgConst;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    AccountUserService userService;


    @RequestMapping("list")
    public List<AccountUser> getList() {
        List<AccountUser> list = userService.list();
        return list;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") String id) {
        AccountUser user = userService.getById(id);
        if (null != user) {
            AccountUserVo vo = new AccountUserVo();
            BeanUtils.copyProperties(user, vo);
            return ResponseEntity.ok().body(vo);
        }
        return ResponseEntity.badRequest().body(MesgConst.NO_EMPLOYEE);
    }

    @PostMapping
    public ResponseEntity<MsgResult> create(@RequestBody AccountUserVo uservo) throws URISyntaxException {
        if (null != uservo) {
            AccountUser user = new AccountUser();
            BeanUtils.copyProperties(uservo, user);
            if (userService.save(user)) {

                return ResponseEntity.created(new URI("test")).body(MsgResult.success(MesgConst.SUCCESS_CREATED));
            }
            return ResponseEntity.badRequest().body(MsgResult.error(MesgConst.NO_EMPLOYEE));
        }
        return ResponseEntity.badRequest().body(MsgResult.error(MesgConst.NO_EMPLOYEE));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MsgResult> deleteUserById(@PathVariable("id") String id) {
        if (userService.removeById(id)) {
            return ResponseEntity.ok().body(MsgResult.success(MesgConst.SUCCESS_DELETED));
        }
        return ResponseEntity.badRequest().body(MsgResult.error(MesgConst.NO_EMPLOYEE));
    }

    @PostMapping("/test")
    public ResponseEntity<AjaxResult> auth(@RequestParam String username) {

        switch (username) {

            case "200":
                return ResponseEntity.ok().body(AjaxResult.success(null));

            case "400":
                return ResponseEntity.badRequest().body(AjaxResult.success(null));
            case "404":
                return ResponseEntity.notFound().build();
            case "500":
                return ResponseEntity.status(HttpStatus.ERROR).body(AjaxResult.success(null));
            default:
                return ResponseEntity.status(203).body(AjaxResult.success(null));
        }
    }

    @PostMapping(value = "/upload")
    public AjaxResult upload2(@RequestParam("file") MultipartFile multipartFile) {
        try {
            //上传内容不能为空
            if (multipartFile.isEmpty()) {
                return AjaxResult.success("file can't be empty");
            }
            String[] fileHeader = {"id", "login", "name", "salary", "startDa66te"};
            File file = CsvImportUtil.uploadFile(multipartFile);
            List<CSVRecord> ll = CsvImportUtil.read(file.getPath(), fileHeader);
            List<AccountUserVo> voList = new ArrayList<>();
            for (CSVRecord record : ll) {
                voList.add(AccountUserVo.parse(record));
            }
            file.delete();
            List<AccountUser> list = (List<AccountUser>) BeanCopyUtil.copyList(voList, AccountUser.class);
            userService.uploadUser(list);
        } catch (Exception e) {

        }
        return AjaxResult.success(null);
    }
}
