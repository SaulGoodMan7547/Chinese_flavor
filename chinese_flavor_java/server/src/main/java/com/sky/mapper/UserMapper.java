package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE openid = #{openid}")
    User getByOpenid(String openid);

    void insert(User user1);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User getById(Long Id);

    @Select("SELECT COUNT(*) FROM `user` WHERE create_time BETWEEN #{begin} AND #{end} GROUP BY DATE(create_time) ORDER BY create_time ASC")
    List<Integer> getUserNumber(LocalDate begin, LocalDate end);

    @Select("SELECT DATE(create_time) FROM `user` WHERE create_time BETWEEN #{begin} AND #{end} GROUP BY DATE(create_time) ORDER BY create_time ASC")
    List<LocalDate> getUserCreateTime(LocalDate begin, LocalDate end);

    @Select("SELECT COUNT(*) FROM `user` WHERE create_time BETWEEN #{begin} AND #{plusDays}")
    Integer getTotalByDay(LocalDate begin, LocalDate plusDays);

    @Select("SELECT COUNT(*) FROM user WHERE create_time BETWEEN #{today} AND #{plusDays}")
    Integer getTodayNewUser(LocalDate today, LocalDate plusDays);
}
