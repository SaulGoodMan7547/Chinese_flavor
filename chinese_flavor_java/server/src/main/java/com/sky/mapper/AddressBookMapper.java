package com.sky.mapper;

import com.sky.entity.AddressBook;
import com.sky.service.AddressService;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    @Insert("INSERT INTO address_book(user_id,consignee,sex,phone,province_code,province_name,city_code,city_name,district_code,district_name,detail,label,is_default) " +
            "VALUES(#{userId},#{consignee},#{sex},#{phone},#{provinceCode},#{provinceName},#{cityCode},#{cityName},#{districtCode},#{districtName},#{detail},#{label},#{isDefault})")
    void add(AddressBook addressBook);

    @Select("SELECT * FROM address_book WHERE user_id = #{userId}")
    List<AddressBook> getByUserId(Long userId);

    @Select("SELECT * FROM address_book WHERE is_default = '1' AND user_id = #{userId}")
    AddressBook getDefaultAddress(Long userId);

    @Select("SELECT * FROM address_book WHERE id = #{id}")
    AddressBook getById(Long id);

    void update(AddressBook addressBook);

    @Delete("DELETE FROM address_book WHERE id = #{id}")
    void deleteById(Long id);

    void setDefault(Long id);
}
