package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class PaymentTest {
    private Map<String, String> voucherCodePayment = new HashMap<>();
    private Map<String, String> bankTransferPayment = new HashMap<>();

    @BeforeEach
    void setUp() {
        voucherCodePayment.put("voucherCode","ESHOPABC12345678");
        bankTransferPayment.put("bank BCC", "referenceCode");
    }

    @Test
    void testPaymentInvalidSubFeature() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("paymentID-123","invalid-payment", voucherCodePayment);
        });
    }

    @Test
    void testPaymentInvalidStatus() {
        Payment payment = new Payment("paymentID-123","VOUCHER_CODE", voucherCodePayment);

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("INVALID_STATUS");
        });
    }

    @Test
    void testDifferentMethodAndPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("paymentID-123", "VOUCHER_CODE", bankTransferPayment);
        });
    }

    @Test
    void testInvalidVoucherCodePrefix() {
        Map<String, String> invalidVoucherCodePayment = new HashMap<>();
        invalidVoucherCodePayment.put("voucherCode", "ABCDEFGH12345678");
        Payment payment = new Payment("paymentID-123", "VOUCHER_CODE", invalidVoucherCodePayment);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidVoucherCodeDigit() {
        Map<String, String> invalidVoucherCodePayment = new HashMap<>();
        invalidVoucherCodePayment.put("voucherCode", "ESHOPABC123456789");
        Payment payment = new Payment("paymentID-123", "VOUCHER_CODE", invalidVoucherCodePayment);

        assertEquals("REJECTED", payment.getStatus());
    }
    @Test
    void testInvalidVoucherCodeLength() {
        Map<String, String> invalidVoucherCodePayment = new HashMap<>();
        invalidVoucherCodePayment.put("voucherCode", "ESHOPABCD12345678");
        Payment payment = new Payment("paymentID-123", "VOUCHER_CODE", invalidVoucherCodePayment);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testValidVoucherCode() {
        Payment payment = new Payment("paymentID-123", "VOUCHER_CODE", voucherCodePayment);

        assertEquals("paymentID-123", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(voucherCodePayment, payment.getPaymentData());
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testEmptyBankName() {
        Map<String, String> invalidBankTransferPayment = new HashMap<>();
        invalidBankTransferPayment.put("", "referenceCode-123");

        Payment payment = new Payment("paymentID-123", "BANK_TRANSFER", invalidBankTransferPayment);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testEmptyBankReferenceCode() {
        Map<String, String> invalidBankTransferPayment = new HashMap<>();
        invalidBankTransferPayment.put("BANK_OF_PACIL", "");

        Payment payment = new Payment("paymentID-123", "BANK_TRANSFER", invalidBankTransferPayment);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testValidBankTransferPayment() {
        Payment payment = new Payment ("paymentID-123", "BANK_TRANSFER", bankTransferPayment);

        assertEquals("paymentID-123", payment.getId());
        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals(bankTransferPayment, payment.getPaymentData());
        assertEquals("SUCCESS", payment.getStatus());
    }
}
