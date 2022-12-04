package com.wss.HrPro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wss.HrPro.entity.po.AccountUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountUserMapper extends BaseMapper<AccountUser> {


    List<AccountUser> getUsersWithSameLogin(@Param("accountUsers") List<AccountUser> accountUsers);
    AccountUser getUserWithSameLogin(@Param("acc")AccountUser acc);
    AccountUser getUserWithSameID(@Param("acc")AccountUser acc);
    List<AccountUser> getUsersWithSameId(@Param("accountUsers") List<AccountUser> accountUsers);



}