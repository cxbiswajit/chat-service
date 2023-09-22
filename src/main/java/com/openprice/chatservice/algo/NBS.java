package com.openprice.chatservice.algo;

public class NBS {

    public static double calculateNBS(double sellerDisagreementPoint, double buyerDisagreementPoint,
            double buyerBargainingPower) {
        // Ensure that the sum of bargaining powers is equal to 1
        double sellerBargainingPower = 1 - buyerBargainingPower;

        // Calculate the Nash Bargaining Solution
        double nashBargainingSolution = sellerBargainingPower * sellerDisagreementPoint +
                buyerBargainingPower * buyerDisagreementPoint;

        return Math.ceil(nashBargainingSolution);
    }
}
