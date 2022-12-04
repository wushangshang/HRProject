package com.wss.HrPro.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wss.HrPro.entity.po.AccountUser;
import com.wss.HrPro.entity.vo.Query;
import com.wss.HrPro.mapper.AccountUserMapper;
import com.wss.HrPro.service.AccountUserService;
import com.wss.HrPro.util.MyDateUtil;
import com.wss.HrPro.util.MyThreadLocal;
import com.wss.HrPro.util.constans.MesgConst;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("AccountUserServiceImpl")
public class AccountUserServiceImpl extends ServiceImpl<AccountUserMapper, AccountUser> implements AccountUserService {
    @Override
    public boolean validateAccId(List<AccountUser> list) {
        Map<String, List<AccountUser>> employeesByCity =
                list.stream().collect(Collectors.groupingBy(AccountUser::getId));
        for (List<AccountUser> value : employeesByCity.values()) {
            if (value.size() > 1) {
                MyThreadLocal.set("400", MesgConst.DUP_ID);
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean uploadUser(List<AccountUser> list) {
        if (this.validateAccId(list) & this.validateAccLogin(list)) {
            List<AccountUser> usersWithSameId = this.baseMapper.getUsersWithSameId(list);
            if (usersWithSameId.size() > 0) {
                MyThreadLocal.set("201", MesgConst.UPDATED);
            } else {
                MyThreadLocal.set("200", MesgConst.CREATED);
            }
            return this.saveOrUpdateBatch(list);
        }
        return false;
    }

    @Override
    public boolean validateAccLogin(List<AccountUser> list) {
        Map<String, List<AccountUser>> employeesByCity =
                list.stream().collect(Collectors.groupingBy(AccountUser::getLogin));
        for (List<AccountUser> value : employeesByCity.values()) {
            if (value.size() > 1) {
                MyThreadLocal.set("400", MesgConst.DUP_LOGIN);
                return false;
            }
        }
        List<AccountUser> usersWithSameLogin = this.baseMapper.getUsersWithSameLogin(list);
        for (AccountUser accountUser : usersWithSameLogin) {
            AccountUser user = employeesByCity.get(accountUser.getLogin()).get(0);
            if (!(user.getId().equals(accountUser.getId()))) {
                MyThreadLocal.set("400", MesgConst.DUP_LOGIN);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saveAcc(AccountUser user) {
        if (validateAcc(user)) {
            return this.save(user);
        }
        return false;
    }

    @Override
    public boolean update(AccountUser user) {
        AccountUser userWithSameLogin = this.baseMapper.getUserWithSameLogin(user);
        if (null != userWithSameLogin) {
            if (!(user.getId().equals(userWithSameLogin.getId()))) {
                MyThreadLocal.set("400", MesgConst.LOGIN_EXIST);
                return false;
            }
        }
        if (user.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            MyThreadLocal.set("400", MesgConst.INVALID_SALARY);
            return false;
        }
        try {
            MyDateUtil.validateDate(user);
        } catch (Exception e) {
            MyThreadLocal.set("400", MesgConst.INVALID_DATE);
            return false;
        }
        return this.updateById(user);
    }

    @Override
    public boolean validateAcc(AccountUser user) {
        if (null != this.baseMapper.getUserWithSameID(user)) {
            MyThreadLocal.set("400", MesgConst.ID_EXIST);
            return false;
        }
        if (null != this.baseMapper.getUserWithSameLogin(user)) {
            MyThreadLocal.set("400", MesgConst.LOGIN_EXIST);
            return false;
        }
        if (user.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            MyThreadLocal.set("400", MesgConst.INVALID_SALARY);
            return false;
        }
        try {
            MyDateUtil.validateDate(user);
        } catch (Exception e) {
            MyThreadLocal.set("400", MesgConst.INVALID_DATE);
            return false;
        }
        return true;
    }

    @Override
    public void pageQuery(Page<AccountUser> pageParam, Query qq) {
        QueryWrapper<AccountUser> queryWrapper = new QueryWrapper<>();
        if (qq == null) {
            queryWrapper.orderByAsc("id");
            queryWrapper.ge("salary", 0);
            queryWrapper.lt("salary", 4000);
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }
        String minSalary = qq.getMinSalary();
        String maxSalary = qq.getMaxSalary();
        String sorting = qq.getSorting();

        if (StrUtil.isNotEmpty(sorting)) {
            if (sorting.equalsIgnoreCase("startDate")){
                sorting="start_date";
            }
            queryWrapper.orderByAsc(sorting);
        }else {
            queryWrapper.orderByAsc("id");
        }
        if (StrUtil.isNotEmpty(minSalary)) {
            queryWrapper.ge("salary", minSalary);
        }else {
            queryWrapper.ge("salary", 0);
        }
        if (StrUtil.isNotEmpty(maxSalary)) {
            queryWrapper.lt("salary", maxSalary);
        }else {
            queryWrapper.lt("salary", 4000);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

}