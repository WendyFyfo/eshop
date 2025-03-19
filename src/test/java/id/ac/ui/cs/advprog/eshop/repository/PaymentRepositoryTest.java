package id.ac.ui.cs.advprog.eshop.repository;

import enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> payments = new ArrayList<>();
    Product product = new Product();
    List<Product> products = List.of(product);
    Order order;

    public static final List<String> PAYMENT_METHODS = List.of("VOUCHER_CODE", "BANK_TRANSFER");
    public static final List<String> PAYMENT_STATUSES = List.of("PENDING", "SUCCESS", "REJECTED");

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");

        Map<String, String> paymentVoucher = new HashMap<>();
        paymentVoucher.put("voucherCode", "ESHOP1234ABC5678");
        Payment voucher = new Payment("random-id-1", PAYMENT_METHODS.get(0), paymentVoucher);
        payments.add(voucher);

        Map<String, String> paymentBankTransfer = new HashMap<>();
        paymentBankTransfer.put("bankName", "aBankName");
        paymentBankTransfer.put("referenceCode", "aRefCode");
        Payment bankTransfer = new Payment("random-id-2", PAYMENT_METHODS.get(1), paymentBankTransfer);
        payments.add(bankTransfer);
    }

    @Test
    void testSaveCreate(){
        Payment payment = payments.getFirst();
        Payment result = paymentRepository.save(payment, order);
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getPaymentData(), result.getPaymentData());
    }

    @Test
    void testUpdateInvalidStatus(){
        Payment payment = payments.getFirst();
        paymentRepository.save(payment, order);
        assertThrows(IllegalArgumentException.class, () -> paymentRepository.update(payment, "INVALID"));
    }

    @Test
    void testFindByIdWithNull(){
        assertThrows(NullPointerException.class, () -> paymentRepository.findById(null));
    }

    @Test
    void testSaveDuplicatePaymentId(){
        Payment payment = payments.getFirst();
        paymentRepository.save(payment, order);
        assertThrows(IllegalArgumentException.class, () -> paymentRepository.save(payment, order));
    }

    @Test
    void testUpdateNonExistentPayment(){
        Payment payment = new Payment("non-existent-id", PAYMENT_METHODS.get(0), Map.of("voucherCode", "ESHOP1234ABC5678"));
        assertThrows(IllegalArgumentException.class, () -> paymentRepository.update(payment, PAYMENT_STATUSES.get(1)));
    }

    @Test
    void testFindAllIfEmpty(){
        List<Payment> payments = paymentRepository.findAll();
        assertEquals(0, payments.size());
    }

    @Test
    void testGetOrderIfNotFound(){
        Order foundOrder = paymentRepository.getOrder("anId");
        assertNull(foundOrder);
    }

    @Test
    void testOrderStatusAfterAllPaymentsFail(){
        Payment payment1 = new Payment("random-id-1", PAYMENT_METHODS.get(0), Map.of("voucherCode", "INVALID"));
        Payment payment2 = new Payment("random-id-2", PAYMENT_METHODS.get(1), Map.of("bankName", "Invalid", "referenceCode", "Invalid"));

        paymentRepository.save(payment1, order);
        paymentRepository.save(payment2, order);

        paymentRepository.update(payment1, PAYMENT_STATUSES.get(2));
        paymentRepository.update(payment2, PAYMENT_STATUSES.get(2));

        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }
}