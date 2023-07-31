package com.openprice.chatservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.dialogflow.cx.v3beta1.QueryResult;
import com.google.cloud.dialogflow.cx.v3beta1.ResponseMessage;
import com.openprice.chatservice.algo.NBS;
import com.openprice.chatservice.config.StaticProductInitializer;
import com.openprice.chatservice.config.model.Product;

@Service
public class BotService {
    private final DetectIntentService intetService;

    @Autowired
    public BotService(DetectIntentService intetService) {
        this.intetService = intetService;
    }

    public List<String> getBotResponse(String sessionId, String request) {

        QueryResult queryResult;
        List<String> content = new ArrayList<>();
        try {
            queryResult = intetService.detectIntent(sessionId, request);
            for (ResponseMessage msg : queryResult.getResponseMessagesList()) {
                System.out.println("Response Text: " + msg);
                content.add(msg.getText().getText(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    public List<String> getInitialResponse(String product_id, String sessionId) {
        List<String> content = new ArrayList<>();
        try {
            Product product = getProductById(product_id);

            // intetService.detectIntent(sessionId, "hello");

            int nbs = getNBS(product.getProduct_open_price(),product.getProduct_close_price());

            content.add(
                    "Hi, I'm Cirax, I can help get you a better deal - let's stop clock-watching and start negotiating!");
            content.add("This is listed for " + product.getProduct_open_price() + " but I could drop the price to "
                    + nbs + " for you, what do you say?");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    private int getNBS(Double D_b,Double D_s) {
        double BP_b = getRandomDoubleInRange(0.7, 0.75);
        return NBS.calculateNBS(D_s, D_b, BP_b);
    }

    public Product getProductById(String id) {
        Map<String, Product> staticMap = StaticProductInitializer.getProductMap();
        return staticMap.getOrDefault(id, null);
    }

    public double getRandomDoubleInRange(double min, double max) {
        return min + (Math.random() * (max - min));
    }
}
