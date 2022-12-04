package com.wss.HrPro.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.wss.HrPro.entity.po.AccountUser;
import com.wss.HrPro.util.constans.MesgConst;

import java.util.regex.Pattern;


public class MyDateUtil {
    public static String formatDate(String date) {
        try {
            if (date.length() == 9) {
                DateTime parse = DateUtil.parse(date, "dd-MMM-yy");
                return DateUtil.format(parse, DatePattern.NORM_DATE_PATTERN);
            } else {
                DateTime parse = DateUtil.parse(date, DatePattern.NORM_DATE_PATTERN);
                return DateUtil.format(parse, DatePattern.NORM_DATE_PATTERN);
            }
        } catch (Exception e) {
            throw new RuntimeException(MesgConst.INVALID_DATE);
        }

    }
    public static void validateDate(AccountUser user) {
        String date = user.getStartDate();
        try {
            Pattern p = Pattern.compile("[a-zA-Z]");
            if(p.matcher(date).find()) {
                DateTime parse = DateUtil.parse(date, "dd-MMM-yy");
                user.setStartDate(DateUtil.format(parse, DatePattern.NORM_DATE_PATTERN));
            } else {
                DateTime parse = DateUtil.parse(date, DatePattern.NORM_DATE_PATTERN);
                user.setStartDate(DateUtil.format(parse, DatePattern.NORM_DATE_PATTERN));
            }
        } catch (Exception e) {
            throw new RuntimeException(MesgConst.INVALID_DATE);
        }
    }
    public static void main(String[] args) {
        AccountUser user=new AccountUser();
        user.setStartDate("7-Nov-01");
        validateDate(user);
        int kk=0;
    }

}
