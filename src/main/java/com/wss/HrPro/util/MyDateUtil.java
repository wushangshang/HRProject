package com.wss.HrPro.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.wss.HrPro.entity.po.AccountUser;
import com.wss.HrPro.util.constans.MesgConst;



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
            if (date.length() == 9) {
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
        System.out.println(formatDate(""));
        int kk=0;
    }

}
