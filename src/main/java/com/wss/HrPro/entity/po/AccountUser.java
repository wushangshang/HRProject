package com.wss.HrPro.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Table
@Entity
@TableName(value = "account_user")
public class AccountUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @TableId
    private String id;

    @TableField(value = "login")
    private String login;
    @TableField(value = "name")
    private String name;
    @TableField(value = "start_date")
    private String startDate;
    @TableField(value = "salary")
    private BigDecimal salary;



}
