package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentMethod;
import enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

public class PaymentTest {
    private Map<String, String> voucherCodePayment = new HashMap<>();
    private Map<String, String> bankTransferPayment = new HashMap<>();

    @BeforeEach
    void setUp() {
        voucherCodePayment.put("voucherCode", "ESHOP1234ABC5678");
        bankTransferPayment.put("bankName", "BANK_BCC");
        bankTransferPayment.put("referenceCode", "referenceCode-123");
    }

    @Test
    void testPaymentInvalidSubFeature() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("paymentID-123", "INVALID_FEATURE", voucherCodePayment);
        });
    }

    @Test
    void testPaymentInvalidStatus() {
        Payment payment = new Payment("paymentID-123", PaymentMethod.VOUCHER_CODE.name(), voucherCodePayment);

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("INVALID_STATUS");
        });
    }

    @Test
    void testDifferentMethodAndPaymentData() {
        Payment differentPayment = new Payment("paymentID-123", PaymentMethod.VOUCHER_CODE.name(), bankTransferPayment);
        assertEquals(PaymentStatus.REJECTED.name(), differentPayment.getStatus());
    }

    @Test
    void testInvalidVoucherCodePrefix() {
        Map<String, String> invalidVoucherCodePayment = new HashMap<>();
        invalidVoucherCodePayment.put("voucherCode", "ABCDEFGH12345678");
        Payment payment = new Payment("paymentID-123", PaymentMethod.VOUCHER_CODE.name(), invalidVoucherCodePayment);
        assertEquals(PaymentStatus.REJECTED.name(), payment.getStatus());
    }

    @Test
    void testInvalidVoucherCodeDigit() {
        Map<String, String> invalidVoucherCodePayment = new HashMap<>();
        invalidVoucherCodePayment.put("voucherCode", "ESHOPABC123456789");
        Payment payment = new Payment("paymentID-123", PaymentMethod.VOUCHER_CODE.name(), invalidVoucherCodePayment);
        assertEquals(PaymentStatus.REJECTED.name(), payment.getStatus());
    }

    @Test
    void testInvalidVoucherCodeLength() {
        Map<String, String> invalidVoucherCodePayment = new HashMap<>();
        invalidVoucherCodePayment.put("voucherCode", "ESHOPABCD12345678");
        Payment payment = new Payment("paymentID-123", PaymentMethod.VOUCHER_CODE.name(), invalidVoucherCodePayment);
        assertEquals(PaymentStatus.REJECTED.name(), payment.getStatus());
    }

    @Test
    void testValidVoucherCode() {
        Payment payment = new Payment("paymentID-123", PaymentMethod.VOUCHER_CODE.name(), voucherCodePayment);
        assertEquals("paymentID-123", payment.getId());
        assertEquals(PaymentMethod.VOUCHER_CODE.name(), payment.getMethod());
        assertEquals(voucherCodePayment, payment.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.name(), payment.getStatus());
    }

    @Test
    void testEmptyBankName() {
        Map<String, String> invalidBankTransferPayment = new HashMap<>();
        invalidBankTransferPayment.put("bankName", "");
        invalidBankTransferPayment.put("referenceCode", "referenceCode-123");
        Payment payment = new Payment("paymentID-123", PaymentMethod.BANK_TRANSFER.name(), invalidBankTransferPayment);
        assertEquals(PaymentStatus.REJECTED.name(), payment.getStatus());
    }

    @Test
    void testEmptyBankReferenceCode() {
        Map<String, String> invalidBankTransferPayment = new HashMap<>();
        invalidBankTransferPayment.put("bankName", "BANK_OF_PACIL");
        invalidBankTransferPayment.put("referenceCode", "");
        Payment payment = new Payment("paymentID-123", PaymentMethod.BANK_TRANSFER.name(), invalidBankTransferPayment);
        assertEquals(PaymentStatus.REJECTED.name(), payment.getStatus());
    }

    @Test
    void testValidBankTransferPayment() {
        Payment payment = new Payment("paymentID-123", PaymentMethod.BANK_TRANSFER.name(), bankTransferPayment);
        assertEquals("paymentID-123", payment.getId());
        assertEquals(PaymentMethod.BANK_TRANSFER.name(), payment.getMethod());
        assertEquals(bankTransferPayment, payment.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.name(), payment.getStatus());
    }
}