package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.result.Result;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 添加员工
     * @param employeeDTO
     */
    void sava(EmployeeDTO employeeDTO);

    /**
     * 分页查询员工
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 更改员工状态
     */
    void startOrStop(Integer status,Long id);

    /**
     * 编辑员工信息
     */
    void update(EmployeeDTO employeeDTO);

    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    Employee selectEmployeeById(Long id);

    Result editPassword(PasswordEditDTO passwordEditDTO);

}
