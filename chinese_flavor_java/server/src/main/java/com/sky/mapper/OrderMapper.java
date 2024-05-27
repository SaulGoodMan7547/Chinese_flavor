package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.entity.Orders;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.OrderOverViewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    void insert(Orders order);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    /**
     * 订单支付
     * @param orderStatus
     * @param orderPaidStatus
     * @param check_out_time
     * @param id
     */
    @Update("UPDATE orders SET status = #{orderStatus},pay_status = #{orderPaidStatus} ,checkout_time = #{check_out_time} where id = #{id}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime check_out_time, Long id);

    @Select("SELECT * FROM orders WHERE id = #{orderId}")
    Orders getById(Long orderId);

    @Update("UPDATE orders SET status = 6,cancel_time = #{now},cancel_reason = #{ordersCancelDTO.cancelReason} WHERE id = #{ordersCancelDTO.id}")
    void cancel(OrdersCancelDTO ordersCancelDTO,LocalDateTime now);

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("SELECT * FROM orders")
    List<Orders> getAll();

    /**
     * 根据id更改状态(接单，派送中）
     */
    @Update("UPDATE orders SET status = #{status} WHERE id = #{id}")
    void updateStatusById(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 完成订单
     * @param now
     */
    @Update("UPDATE orders SET status = 5,delivery_time = #{now} WHERE id = #{id}")
    void complete(Long id,LocalDateTime now);

    @Update("UPDATE orders SET status = 6,cancel_time = #{now},rejection_reason = #{ordersRejectionDTO.rejectionReason}," +
            "cancel_reason = #{ordersRejectionDTO.rejectionReason} WHERE id = #{ordersRejectionDTO.id}")
    void rejection(OrdersRejectionDTO ordersRejectionDTO,LocalDateTime now);

    @Select("SELECT * FROM orders WHERE status = #{status} AND order_time < #{time}")
    List<Orders> getByStatusAndTime(Integer status,LocalDateTime time);

    @Select("SELECT SUM(amount) FROM `orders` WHERE status = 5 AND checkout_time BETWEEN #{begin} " +
            "AND #{end} GROUP BY DATE(checkout_time) ORDER BY checkout_time ASC ")
    List<BigDecimal> getTurnover(LocalDate begin,LocalDate end);
    @Select("SELECT DATE(checkout_time) FROM `orders` WHERE status = 5 AND checkout_time BETWEEN #{begin} " +
            "AND #{end} GROUP BY DATE(checkout_time) ORDER BY checkout_time ASC ")
    List<LocalDate> getCheckOutTime(LocalDate begin, LocalDate end);

    @Select("SELECT COUNT(*) FROM `orders` WHERE status = 5 AND " +
            "checkout_time BETWEEN #{begin} AND #{end} GROUP BY DATE(checkout_time) ORDER BY checkout_time ASC")
    List<Integer> getDone(LocalDate begin, LocalDate end);

    @Select("SELECT COUNT(*) FROM `orders` WHERE checkout_time BETWEEN #{begin} AND #{end} " +
            "GROUP BY DATE(checkout_time) ORDER BY checkout_time ASC")
    List<Integer> getAlls(LocalDate begin, LocalDate end);

    @Select("SELECT DATE(checkout_time) FROM `orders` WHERE checkout_time BETWEEN #{begin} AND #{end} " +
            "GROUP BY DATE(checkout_time) ORDER BY checkout_time ASC")
    List<LocalDate> getAllDays(LocalDate begin, LocalDate end);

    @Select("SELECT SUM(amount) FROM `orders` WHERE checkout_time BETWEEN #{today} AND #{plusDays} AND `status` = 5")
    Double getTodayTurnover(LocalDate today, LocalDate plusDays);

    @Select("SELECT COUNT(*) FROM `orders` WHERE checkout_time BETWEEN #{today} AND #{plusDays} AND `status` = 5")
    Integer getTodayOrders(LocalDate today, LocalDate plusDays);

    @Select("SELECT COUNT(*) FROM `orders` WHERE checkout_time BETWEEN #{today} AND #{plusDays}")
    Integer getTodayAllOrders(LocalDate today, LocalDate plusDays);

    Integer getOrderOverView(LocalDate today, LocalDate plusDays,Integer status);
}
