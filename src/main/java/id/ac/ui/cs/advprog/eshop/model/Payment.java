package id.ac.ui.cs.advprog.eshop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        String paymentFeature = "";
        String paymentCode = "" ;
        for(Map.Entry<String,String> entry : paymentData.entrySet()) {
            paymentFeature = entry.getKey();
            paymentCode = entry.getValue();
        }

        if(method != "VOUCHER_CODE" && method != "BANK_TRANSFER" ) {
            throw new IllegalArgumentException();
        }
        if(method.equals("VOUCHER_CODE") && !paymentFeature.equals("voucherCode")) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = checkStatus(method, paymentFeature, paymentCode);
    }


    private String checkStatus(String method, String paymentFeature, String paymentCode) {
        if (paymentFeature == null || paymentFeature.isEmpty() ||
                paymentCode == null || paymentCode.isEmpty()) {
            return "REJECTED";
        }

        if (method.equals("VOUCHER_CODE") &&
                !paymentCode.matches("^ESHOP(?=(.*\\d){8})[A-Z0-9]{11}$")) {
            return "REJECTED";
        }

        return "SUCCESS";
    }

    public void setStatus(String status) {
        if(status.equals("SUCCESS") || status.equals("REJECTED")) {
            this.status = status;
        }

        throw new IllegalArgumentException();
    }
}
