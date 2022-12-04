package com.wss.HrPro.entity.vo;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@Builder
public class Query {
    private String minSalary;
    private String maxSalary;

    private String sorting;
    private String desc;
}
