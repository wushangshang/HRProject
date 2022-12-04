package com.wss.HrPro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wss.HrPro.entity.po.AccountUser;

import java.util.List;

public interface AccountUserService extends IService<AccountUser> {

    boolean validateAcc(List<AccountUser> list);
    boolean uploadUser(List<AccountUser> list);
}
