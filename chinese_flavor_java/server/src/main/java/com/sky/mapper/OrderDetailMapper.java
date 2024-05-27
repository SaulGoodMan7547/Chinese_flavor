package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderDetailMapper {

    void insertBatch(ArrayList<OrderDetail> orderDetails);

    @Select("SELECT * FROM order_detail WHERE order_id = #{orderId}")
    List<OrderDetail> getByOrderId(Long orderId);

    @Select("SELECT od.name FROM order_detail AS od LEFT JOIN orders AS o ON od.order_id = o.id " +
            "WHERE o.order_time BETWEEN #{begin} AND #{end} and o.status = 5 GROUP BY od.name ORDER BY sum(od.number) DESC")
    List<String> getTopTenName(LocalDate begin, LocalDate end);

    @Select("SELECT sum(od.number) FROM order_detail AS od LEFT JOIN orders AS o ON od.order_id = o.id " +
            "WHERE o.order_time BETWEEN #{begin} AND #{end} and o.status = 5 GROUP BY od.name ORDER BY sum(od.number) DESC")
    List<Integer> getTopTenCount(LocalDate begin, LocalDate end);
}
