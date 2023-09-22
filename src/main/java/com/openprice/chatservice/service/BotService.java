package com.openprice.chatservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.dialogflow.cx.v3beta1.QueryResult;
import com.google.cloud.dialogflow.cx.v3beta1.ResponseMessage;
import com.openprice.chatservice.algo.NBS;
import com.openprice.chatservice.config.StaticProductInitializer;
import com.openprice.chatservice.controller.model.Product;


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

    @Async
    public List<String> startDialogflow(String product_id, String sessionId) {
        QueryResult queryResult;
        List<String> content = new ArrayList<>();
        try {
            queryResult = intetService.detectIntent(sessionId, "sell "+ product_id);
            for (ResponseMessage msg : queryResult.getResponseMessagesList()) {
                System.out.println("Response Text: " + msg);
                content.add(msg.getText().getText(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         return content;
    }

    public Product getProductWithDeal(Product request){
        Product response = getProductById(request.getProductId());
        double nbs = getNBS(request.getBuyerDisagreementPrice(),response.getSellerDisagreementPrice());
        boolean isDeal = isDeal(request.getBuyerAskingPrice(),nbs,response.getSellerDisagreementPrice());
        if(isDeal){
            response.setBuyerDisagreementPrice(request.getBuyerAskingPrice());
            response.setDeal("yes");
        }else{
            response.setBuyerDisagreementPrice(nbs);
            response.setDeal("no");
            if(request.getBuyerAskingPrice() < response.getSellerDisagreementPrice()){
                response.setAskingPriceThreshold(4);
            }
            response.setBuyerLastAskingPrice(request.getBuyerAskingPrice());
        }       
        return response;
    }

    private boolean isDeal(double A_b, double nbs, double D_s) {
        System.out.println("askPrice ::" + A_b + "nbs ::" + nbs + "listingPrice :" +D_s);
        return (A_b >= nbs) && (A_b >= D_s) ;
    }

    public Product getProductWithNBS(String id){
        Product response = getProductById(id);
        double nbs = getNBS(response.getProductListingPrice(),response.getSellerDisagreementPrice());
        System.out.println("nbs ::" + nbs);
        response.setBuyerDisagreementPrice(nbs);
        return response;
    }

    public double getNBS(Double D_b,Double D_s) {
        double P_b = getRandomDoubleInRange(0.7, 0.75);
        return NBS.calculateNBS(D_s, D_b, P_b);
    }

    public Product getProductById(String id) {
        Map<String, Product> staticMap = StaticProductInitializer.getProductMap();
        Product product = staticMap.getOrDefault(id, null);
        ObjectMapper mapper = new ObjectMapper();
        Product response = null;
        try {
            response = mapper.readValue(mapper.writeValueAsString(product), Product.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public double getRandomDoubleInRange(double min, double max) {
        return min + (Math.random() * (max - min));
    }
}
