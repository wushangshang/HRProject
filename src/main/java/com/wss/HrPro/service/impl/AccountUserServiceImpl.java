package com.wss.HrPro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wss.HrPro.entity.po.AccountUser;
import com.wss.HrPro.mapper.AccountUserMapper;
import com.wss.HrPro.service.AccountUserService;
import com.wss.HrPro.util.constans.MesgConst;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountUserServiceImpl extends ServiceImpl<AccountUserMapper, AccountUser> implements AccountUserService {
    @Override
    public boolean validateAcc(List<AccountUser> list) {
        Map<String, List<AccountUser>> employeesByCity =
                list.stream().collect(Collectors.groupingBy(AccountUser::getId));
        for (List<AccountUser> value : employeesByCity.values()) {
            if (value.size() > 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean uploadUser(List<AccountUser> list) {
        if (!this.validateAcc(list)){
            throw new RuntimeException(MesgConst.MORE_THAN_ONEID);
        }
        return false;
    }
}