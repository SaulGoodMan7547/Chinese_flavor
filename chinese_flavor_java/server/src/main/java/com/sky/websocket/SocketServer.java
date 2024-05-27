package com.sky.websocket;

import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint("/ws/{sid}")
public class SocketServer {

    private static Map<String,Session> map = new HashMap<>();

    /**
     * webSocket连接成功的回调函数
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid){
        map.put(sid,session);
    }

    /**
     * 接收到信息的回调函数
     * @param message
     * @param sid
     */
    @OnMessage
    public void onMessage(String message,@PathParam("sid") String sid){
        System.out.println("客户端：" + sid + "发来信息：" + message);
    }

    /**
     * 断开连接的回调函数
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid){

        System.out.println("客户端：" + sid + "断开连接");

        map.remove(sid);
    }

    /**
     * 给客户端发送信息
     * @param message
     */
    public void sendToAllClient(String message){
        Collection<Session> values = map.values();

        for (Session value : values) {
            try {
                value.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
