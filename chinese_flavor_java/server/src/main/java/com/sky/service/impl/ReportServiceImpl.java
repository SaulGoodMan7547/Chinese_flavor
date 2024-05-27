package com.sky.service.impl;

import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderDetailMapper orderDetailMapper;

    /**
     * 营业额统计
     * @param begin
     * @param end
     * @return
     */

    /**
     * 思路描述：黑马程序员：将begin - end期间的日期存入list,遍历list,遍历一次查询一次数据库，统计当天营业额
     * sql压力过大，于是优化：查询出begin - end 这些天中数据库中包含的日期以及金额（分别存放在两个list),创建一个BigDecimal数组，大小与查询天数一致
     * 将begin - end期间的日期存入list，遍历list，将list中的每一项与数据库中包含的日期比较，若有相同，则在bigDecimal相应位置添加数据
     * @param begin
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO turnoverReport(LocalDate begin, LocalDate end) {
        //每天的总金额
        List<BigDecimal> turnover = orderMapper.getTurnover(begin, end.plusDays(1));

        //数据库中存在的日期
        List<LocalDate> date1 = orderMapper.getCheckOutTime(begin,end.plusDays(1));

        //每天的日期
        List<LocalDate> list = new ArrayList<>();

        list.add(begin);

        int count = 1;
        while(!begin.equals(end)){

            begin = begin.plusDays(1);
            list.add(begin);
            count++;
        }

        int indexForDate1 = 0,indexForList=0;
        BigDecimal[] bigDecimals = new BigDecimal[count];
        for (LocalDate l : list) {

            if(indexForDate1 >= date1.size())
                break;

            if(l.equals(date1.get(indexForDate1))){
                bigDecimals[indexForList] = turnover.get(indexForDate1);
                indexForDate1++;
            }

            indexForList++;
        }

        for(int n = 0;n < bigDecimals.length;n++){
            if(bigDecimals[n] == null)
                bigDecimals[n] = BigDecimal.valueOf(0);
        }

        String date = StringUtils.join(list, ",");
        String join = StringUtils.join(bigDecimals, ',');

        TurnoverReportVO turnoverReportVO
                = TurnoverReportVO
                .builder()
                .turnoverList(join)
                .dateList(date).build();

        return turnoverReportVO;
    }

    /**
     * 用户统计
     */
    @Override
    public UserReportVO userReport(LocalDate begin, LocalDate end) {

        //用于记录每天的日期
        ArrayList<LocalDate> everyDay = new ArrayList<>();
        LocalDate start = LocalDate.parse(String.valueOf(begin));
        everyDay.add(start);

        //用于记录共多少天
        int count = 1;
        while(!start.equals(end)){
            start = start.plusDays(1);
            everyDay.add(start);
            count++;
        }

        //分别记录 每天新增的人数，以及日期
        List<Integer> userNumber = userMapper.getUserNumber(begin, end.plusDays(1));
        List<LocalDate> userCreateTime = userMapper.getUserCreateTime(begin, end.plusDays(1));

        //用于记录每天新注册的人数
        int[] userAddByDay = new int[count];

        int j = 0;
        for(int i = 0;i < count;i++){

            if(j >= userNumber.size())
                break;

            if(everyDay.get(i).equals(userCreateTime.get(j))){
                userAddByDay[i] = userNumber.get(j);
                j++;
            }
        }

        //用与记录每天的总人数
        int[] sum = new int[count];
        Integer total = userMapper.getTotalByDay(begin, begin.plusDays(1));

        sum[0] = total;
        for(int i = 1;i < sum.length;i++){
            sum[i] = sum[i - 1] + userAddByDay[i];
        }

        ArrayList<Object> userAddList = new ArrayList<>();
        ArrayList<Object> sumList = new ArrayList<>();

        //转为list才能被转换为String常量
        for (int i : userAddByDay) {
            userAddList.add(i);
        }

        for (int i : sum) {
            sumList.add(i);
        }

        String dateList = StringUtils.join(everyDay, ",");
        String newUserList = StringUtils.join(userAddList, ",");
        String totalUser = StringUtils.join(sumList, ",");


        UserReportVO userReportVO =
                UserReportVO.builder().dateList(dateList)
                .newUserList(newUserList)
                .totalUserList(totalUser)
                .build();

        return userReportVO;
    }

    /**
     * 订单统计
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public OrderReportVO orderReport(LocalDate begin, LocalDate end) {

        //用于记录每天的日期
        ArrayList<LocalDate> day = new ArrayList<>();
        LocalDate start = LocalDate.parse(String.valueOf(begin));
        day.add(start);

        //用于记录共多少天
        int count = 1;
        while(!start.equals(end)){
            start = start.plusDays(1);
            day.add(start);
            count++;
        }

        List<Integer> done = orderMapper.getDone(begin, end.plusDays(1));
        List<Integer> alls = orderMapper.getAlls(begin, end.plusDays(1));
        List<LocalDate> allDays = orderMapper.getAllDays(begin, end.plusDays(1));

        int[] doneList = new int[count];
        int[] allsList = new int[count];

        int al = 0;
        for (int i = 0; i < count; i++) {

            if(al >= allDays.size())
                break;

            if(day.get(i).equals(allDays.get(al))){
                doneList[i] = done.get(al);
                allsList[i] = alls.get(al);

                al++;
            }
        }

        ArrayList<Integer> done1 = new ArrayList<>();
        ArrayList<Integer> all1 = new ArrayList<>();

        //total:总数，money:有效订单数
        int total = 0,money = 0;
        for (int i : doneList) {
            done1.add(i);
            money += i;
        }

        for (int i : allsList) {
            all1.add(i);
            total += i;
        }

        Double donePercent = 0.0;

        if(total != 0){
            donePercent =  (double)money / total;
        }


        String done2 = StringUtils.join(done1, ",");
        String all2 = StringUtils.join(all1, ",");
        String day2 = StringUtils.join(day, ",");


        OrderReportVO orderReportVO = OrderReportVO.builder()
                .orderCompletionRate(donePercent)
                .orderCountList(all2)
                .totalOrderCount(total)
                .validOrderCount(money)
                .validOrderCountList(done2)
                .dateList(day2).build();

        return orderReportVO;
    }

    /**
     * TOP10 统计
     */
    @Override
    public SalesTop10ReportVO top(LocalDate begin, LocalDate end) {

        List<String> name = orderDetailMapper.getTopTenName(begin, end.plusDays(1));
        List<Integer> count = orderDetailMapper.getTopTenCount(begin, end.plusDays(1));

        List<String> topTenNames = name.size() > 10 ? name.subList(0, 10) : name;
        List<Integer> topTenCounts = count.size() > 10 ? count.subList(0, 10) : count;

        String names = StringUtils.join(topTenNames, ",");
        String counts = StringUtils.join(topTenCounts, ",");

        SalesTop10ReportVO salesTop10ReportVO = SalesTop10ReportVO.builder().nameList(names).numberList(counts).build();

        return salesTop10ReportVO;
    }

    /**
     * 导出excel表格
     */
    @Override
    public BusinessDataVO export(HttpServletResponse response) {
        //查询数据
        LocalDate start = LocalDate.now().minusDays(30);

        BusinessDataVO businessDataVO = getByDate(start, LocalDate.now());

        //写入excel
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");

        try {
            XSSFWorkbook excel = new XSSFWorkbook(resourceAsStream);
            XSSFSheet sheet1 = excel.getSheet("Sheet1");
            //填充时间
            XSSFRow row = sheet1.getRow(1);
            row.getCell(1).setCellValue("时间：" + start + " - " + LocalDate.now());

            //填充概览数据
            row = sheet1.getRow(3);
            row.getCell(2).setCellValue(businessDataVO.getTurnover());
            row.getCell(4).setCellValue(businessDataVO.getOrderCompletionRate());
            row.getCell(6).setCellValue(businessDataVO.getNewUsers());

            row = sheet1.getRow(4);
            row.getCell(2).setCellValue(businessDataVO.getValidOrderCount());
            row.getCell(4).setCellValue(businessDataVO.getUnitPrice());

            //循环写入明显数据
            for (int i = 0; i < 30; i++) {
                BusinessDataVO data = getByDate(start, start.plusDays(1));

                if(data == null){
                    data = BusinessDataVO.builder()
                            .newUsers(0)
                            //.turnover(0.0)
                            .validOrderCount(0)
                            .orderCompletionRate(0.0)
                            .unitPrice(0.0).build();
                    data.setTurnover(0.0);
                }

                row = sheet1.getRow(7 + i);
                row.getCell(1).setCellValue(start.toString());
                row.getCell(2).setCellValue(data.getTurnover() == null ? 0 : data.getTurnover());
                row.getCell(3).setCellValue(data.getValidOrderCount());
                row.getCell(4).setCellValue(data.getOrderCompletionRate());
                row.getCell(5).setCellValue(data.getUnitPrice());
                row.getCell(6).setCellValue(data.getNewUsers());

                start = start.plusDays(1);
            }


            ServletOutputStream out = response.getOutputStream();
            excel.write(out);

            out.close();
            excel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return businessDataVO;
    }

    /**
     * 通过日期返回BusinessDataVO
     */
    public BusinessDataVO getByDate(LocalDate start,LocalDate end){

        //获取期间营业额
        Double todayTurnover = orderMapper.getTodayTurnover(start,end);
        //获取期间有效订单数
        Integer todayOrders = orderMapper.getTodayOrders(start, end);
        //获取期间所有订单数
        Integer todayAllOrders = orderMapper.getTodayAllOrders(start, end);
        //获取期间新增用户数
        Integer todayNewUser = userMapper.getTodayNewUser(start, end);

        Double orderCompletionRate = 0.0;
        Double unitPrice = 0.0;
        if(todayAllOrders != 0){
            orderCompletionRate = (double) todayOrders / todayAllOrders;
        }
        if(todayOrders != 0){
            unitPrice = (double) todayTurnover / todayOrders;
        }

        BusinessDataVO businessDataVO = BusinessDataVO.builder()
                .newUsers(todayNewUser)
                .turnover(todayTurnover)
                .validOrderCount(todayOrders)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice).build();

        return businessDataVO;
    }
}
