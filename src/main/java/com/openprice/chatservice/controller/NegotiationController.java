package com.openprice.chatservice.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.openprice.chatservice.controller.model.Product;
import com.openprice.chatservice.service.BotService;


@RestController
@RequestMapping("/v1/webhook")
public class NegotiationController {

    @Autowired
    BotService service;

    @PostMapping("/negotiate")
    public Product handlePriceDialogflowCXWebhook(@RequestBody Product request){
        System.out.println(" handlePriceDialogflowCXWebhook request ::" + request.toString());
        return service.getProductWithDeal(request);
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable String id){
        System.out.println("id ::" + id);     
        return service.getProductWithNBS(id);
    }
}
