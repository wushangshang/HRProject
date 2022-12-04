package com.wss.HrPro.entity.vo;


import cn.hutool.core.util.StrUtil;
import com.wss.HrPro.util.MyDateUtil;
import com.wss.HrPro.util.constans.MesgConst;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountUserVo {

    private String id;


    private String login;

    private String name;

    private String startDate;

    private BigDecimal salary;

    static public AccountUserVo parse(CSVRecord csvRecord) {
        String salary = csvRecord.get("salary");
        String id = csvRecord.get("id");
        String login = csvRecord.get("login");
        String name = csvRecord.get("name");
        String startDate = MyDateUtil.formatDate(csvRecord.get("startDate"));
        if (StrUtil.isBlank(salary) || StrUtil.isBlank(id) || StrUtil.isBlank(login) || StrUtil.isBlank(name) || StrUtil.isBlank(startDate)) {
            throw new RuntimeException(MesgConst.COLUMN_ERROR);
        }
        BigDecimal salaryMoney = new BigDecimal(salary);

        return AccountUserVo.builder().id(id)
                .login(login)
                .name(name)
                .startDate(startDate)
                .salary(salaryMoney).build();
    }

}
