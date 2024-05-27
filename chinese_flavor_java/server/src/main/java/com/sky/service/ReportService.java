package com.sky.service;

import com.sky.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

public interface ReportService {

    /**
     * 营业额统计
     * @param begin
     * @param end
     * @return
     */
    TurnoverReportVO turnoverReport(LocalDate begin, LocalDate end);

    /**
     * 用户统计
     */
    UserReportVO userReport(LocalDate begin, LocalDate end);

    /**
     * 订单统计
     * @param begin
     * @param end
     * @return
     */
    OrderReportVO orderReport(LocalDate begin, LocalDate end);

    /**
     * TOP10 统计
     */
    SalesTop10ReportVO top(LocalDate begin, LocalDate end);

    /**
     * 导出excel表格
     */
    BusinessDataVO export(HttpServletResponse response);
}
