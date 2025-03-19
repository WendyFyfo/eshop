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

    private static final String VOUCHER_CODE = "VOUCHER_CODE";
    private static final String BANK_TRANSFER = "BANK_TRANSFER";
    private static final String SUCCESS = "SUCCESS";
    private static final String REJECTED = "REJECTED";
    private static final List<String> VALID_PAYMENT_METHODS = List.of(VOUCHER_CODE, BANK_TRANSFER);
    private static final List<String> VALID_STATUSES = List.of(SUCCESS, REJECTED);

    public Payment(String id, String method, Map<String, String> paymentData) {
        String paymentFeature = "";
        String paymentCode = "" ;
        for(Map.Entry<String,String> entry : paymentData.entrySet()) {
            paymentFeature = entry.getKey();
            paymentCode = entry.getValue();
        }

        if( !VALID_PAYMENT_METHODS.contains(method) ) {
            throw new IllegalArgumentException();
        }
        if(method.equals(VOUCHER_CODE) && !paymentFeature.equals("voucherCode")) {
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
            return REJECTED;
        }

        if (method.equals("VOUCHER_CODE") &&
                !paymentCode.matches("^ESHOP(?=(.*\\d){8})[A-Z0-9]{11}$")) {
            return REJECTED;
        }

        return SUCCESS;
    }

    public void setStatus(String status) {
        if(VALID_STATUSES.contains(status)) {
            this.status = status;
        }else{
            throw new IllegalArgumentException();
        }
    }
}
