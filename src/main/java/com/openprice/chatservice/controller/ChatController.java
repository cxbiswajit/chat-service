package com.openprice.chatservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.openprice.chatservice.controller.model.BotResponseWrapper;
import com.openprice.chatservice.controller.model.ReqMessage;
import com.openprice.chatservice.service.BotService;

@Controller
public class ChatController {
    
    @Autowired
    BotService service;


    @MessageMapping("/msg")
    @SendToUser("/queue/msg") // Send the response to the user-specific message queue
    public BotResponseWrapper sendMessage(@Payload ReqMessage msg,@Header("userSessionId") String userSessionId) {
        List<String> botResponse =  new ArrayList<>();
        if(msg.getIsInitiate()){
            botResponse = service.getInitialResponse(msg.getProductId(), userSessionId); 
        }else{
            botResponse =  service.getBotResponse(userSessionId, msg.getContent()); 
        }
        return new BotResponseWrapper(botResponse);
    }
}
