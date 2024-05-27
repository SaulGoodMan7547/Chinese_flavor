package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressService {

    @Resource
    private AddressBookMapper addressBookMapper;

    /**
     * 新增地址
     */
    @Override
    public void add(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());

        addressBook.setIsDefault(0);
        addressBookMapper.add(addressBook);

    }

    /**
     * 查询当前用户的所有地址
     * @return
     */
    @Override
    public List<AddressBook> list() {

        Long userId = BaseContext.getCurrentId();

        List<AddressBook> list = addressBookMapper.getByUserId(userId);

        return list;
    }

    /**
     * 查询当前默认地址
     */
    @Override
    public AddressBook getDefaultAddress() {
        Long userId = BaseContext.getCurrentId();

        AddressBook defaultAddress = addressBookMapper.getDefaultAddress(userId);

        return defaultAddress;
    }

    /**
     * 根据id查询地址
     */
    @Override
    public AddressBook getById(Long id) {

        AddressBook addressBook = addressBookMapper.getById(id);

        return addressBook;
    }

    /**
     * 根据id修改地址
     */
    @Override
    public void update(AddressBook addressBook) {

        addressBookMapper.update(addressBook);
    }

    /**
     * 根据id删除地址
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }

    /**
     * 设置默认地址
     */
    @Transactional
    @Override
    public void setDefault(Long id) {

        AddressBook defaultAddress = addressBookMapper.getDefaultAddress(BaseContext.getCurrentId());

        if(defaultAddress != null){
            AddressBook addressBook = new AddressBook();
            addressBook.setIsDefault(0);
            addressBook.setId(defaultAddress.getId());

            addressBookMapper.update(addressBook);
        }



        addressBookMapper.setDefault(id);

    }
}
