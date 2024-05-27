package com.sky.test;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.common.utils.HttpUtil;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpUtils;

@SpringBootTest
public class HttpClientTest {

    @Test
    public void httpClientGet() throws Exception{
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建请求体
        HttpGet httpGet = new HttpGet("http://localhost:8080/user/shop/status");

        //发起请求
        CloseableHttpResponse response = httpClient.execute(httpGet);

        //获取请求数据
        //获取返回码
        int code = response.getStatusLine().getStatusCode();
        System.out.println("状态码：" + code);

        HttpEntity entity = response.getEntity();
        String s = EntityUtils.toString(entity);

        System.out.println("返回体：" + s);

        //资源关闭
        response.close();
        httpClient.close();
    }

    @Test
    public void httpClientPost() throws Exception{
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建请求体对象(带参数,参数需要以StringEntity类型传入，所以需要先构建StringEntity)
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");

        //因为请求地址需要传入json格式字符串，因此StringEntity需要传入json字符串
        //使用JSONObject可以更方便的构建JSON格式字符串
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username","admin");
        jsonObject.put("password","123456");

        StringEntity entity = new StringEntity(jsonObject.toString());

        //设置编码以及以数据json格式
        entity.setContentEncoding("utf-8");
        entity.setContentType("application/json");

        httpPost.setEntity(entity);

        //发送参数
        CloseableHttpResponse response = httpClient.execute(httpPost);

        //获取返回对象
        HttpEntity responseEntity = response.getEntity();
        int code = response.getStatusLine().getStatusCode();

        String s = EntityUtils.toString(responseEntity);

        System.out.println("状态码：" + code);
        System.out.println("返回对象：" + s);

        //关闭资源
        response.close();
        httpClient.close();
    }
}
