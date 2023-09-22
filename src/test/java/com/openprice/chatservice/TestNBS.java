package com.openprice.chatservice;

import com.openprice.chatservice.algo.NBS;

public class TestNBS {
    public static void main(String[] args) {
        double D_b = 8000;
        double D_s = 4000;
        for(int i=0;i<10;i++){
            double nbs = NBS.calculateNBS(D_s, D_b, 0.75);
            System.out.println(nbs);
            D_b = nbs;
            D_s = 2000;
        }
    }
}
