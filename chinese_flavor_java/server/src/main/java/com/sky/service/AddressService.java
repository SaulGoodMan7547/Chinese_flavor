package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressService {

    /**
     * 新增地址
     */
    void add(AddressBook addressBook);

    /**
     * 查询当前用户的所有地址
     * @return
     */
    List<AddressBook> list();

    /**
     * 查询当前默认地址
     */
    AddressBook getDefaultAddress();

    /**
     * 根据id查询地址
     */
    AddressBook getById(Long id);

    /**
     * 根据id修改地址
     */
    void update(AddressBook addressBook);

    /**
     * 根据id删除地址
     * @param id
     */
    void deleteById(Long id);

    /**
     * 设置默认地址
     */
    void setDefault(Long id);
}
