package com.wss.HrPro.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wss.HrPro.entity.po.AccountUser;
import com.wss.HrPro.entity.vo.Query;

import java.util.List;

public interface AccountUserService extends IService<AccountUser> {

    boolean validateAccId(List<AccountUser> list);
    boolean uploadUser(List<AccountUser> list);
    boolean validateAccLogin(List<AccountUser> list);
    boolean saveAcc(AccountUser user);
    boolean update(AccountUser user);
    boolean validateAcc(AccountUser user);

     void pageQuery(Page<AccountUser> pageParam, Query qq);

}
