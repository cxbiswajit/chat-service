package com.openprice.chatservice.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReqMessage {

    private String sender;
    private Boolean isInitiate;
    private String productId;
    private String content;
    
}

