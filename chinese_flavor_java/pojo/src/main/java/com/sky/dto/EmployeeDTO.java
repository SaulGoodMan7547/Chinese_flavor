package com.sky.dto;

import lombok.Data;

import java.io.Serializable;
/**
 * 作用：用于封装前端向后端提交的 Employee
 * 使用场景：添加员工,编辑员工信息
 * */
@Data
public class EmployeeDTO implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

}
