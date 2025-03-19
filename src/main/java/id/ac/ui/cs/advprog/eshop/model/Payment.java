package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentMethod;
import enums.PaymentStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private final String id;
    private final String method;
    private String status;
    private final Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        if (method == null || (!method.equals(PaymentMethod.VOUCHER_CODE.name()) && !method.equals(PaymentMethod.BANK_TRANSFER.name()))) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = checkStatus(method, paymentData);
    }

    private String checkStatus(String method, Map<String, String> paymentData) {
        if (method.equals(PaymentMethod.VOUCHER_CODE.name())) {
            return validateVoucher(paymentData);
        } else if (method.equals(PaymentMethod.BANK_TRANSFER.name())) {
            return validateBankTransfer(paymentData);
        }
        return PaymentStatus.REJECTED.name();
    }

    private String validateVoucher(Map<String, String> paymentData) {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null || !voucherCode.matches("^ESHOP(?=(\\D*\\d){8,})[A-Z0-9]{11}$")) {
            return PaymentStatus.REJECTED.name();
        }
        return PaymentStatus.SUCCESS.name();
    }

    private String validateBankTransfer(Map<String, String> paymentData) {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        if (bankName == null || bankName.isEmpty() || referenceCode == null || referenceCode.isEmpty()) {
            return PaymentStatus.REJECTED.name();
        }
        return PaymentStatus.SUCCESS.name();
    }

    public void setStatus(String status) {
        if (!status.equals(PaymentStatus.SUCCESS.name()) && !status.equals(PaymentStatus.REJECTED.name())) {
            throw new IllegalArgumentException("Invalid status");
        }
        this.status = status;
    }
}
