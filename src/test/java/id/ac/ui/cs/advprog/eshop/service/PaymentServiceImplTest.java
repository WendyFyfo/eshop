package id.ac.ui.cs.advprog.eshop.service;

import enums.OrderStatus;
import enums.PaymentMethod;
import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private List<Order> orders;
    private List<Payment> payments;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);

        product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        orders = List.of(
                new Order("order-id-1", products, 100000L, "yanto"),
                new Order("order-id-2", products, 100001L, "yanti")
        );

        Map<String, String> mapVoucher = Map.of("voucherCode", "ESHOP1234ABC5678");
        Map<String, String> mapBankTransfer = Map.of("bankName", "bread-bank", "referenceCode", "refCode");

        payments = List.of(
                new Payment("payment-id-1", PaymentMethod.VOUCHER_CODE.name(), mapVoucher),
                new Payment("payment-id-2", PaymentMethod.BANK_TRANSFER.name(), mapBankTransfer)
        );
    }

    // Payment Creation Tests
    @Test
    void testAddPaymentWithVoucher() {
        when(paymentRepository.findById("payment-id-1")).thenReturn(null);
        when(paymentRepository.save(any(Payment.class), any(Order.class))).thenReturn(payments.getFirst());

        Payment result = paymentService.addPayment("payment-id-1", orders.getFirst(), PaymentMethod.VOUCHER_CODE.name(), payments.getFirst().getPaymentData());

        assertNotNull(result);
        assertEquals("payment-id-1", result.getId());
        verify(paymentRepository).save(any(Payment.class), any(Order.class));
    }

    @Test
    void testAddPaymentWithBankTransfer() {
        when(paymentRepository.findById("payment-id-2")).thenReturn(null);
        when(paymentRepository.save(any(Payment.class), any(Order.class))).thenReturn(payments.get(1));

        Payment result = paymentService.addPayment("payment-id-2", orders.get(1), PaymentMethod.BANK_TRANSFER.name(), payments.get(1).getPaymentData());

        assertNotNull(result);
        assertEquals("payment-id-2", result.getId());
        verify(paymentRepository).save(any(Payment.class), any(Order.class));
    }

    @Test
    void testAddPaymentWithInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () ->
                paymentService.addPayment("payment-id-3", orders.getFirst(), "INVALID_METHOD", new HashMap<>())
        );
    }

    // Payment Status Tests
    @Test
    void testSetPaymentStatusToSuccess() {
        Payment payment = payments.getFirst();
        Order order = orders.getFirst();

        when(paymentRepository.getOrder(payment.getId())).thenReturn(order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.name());

        assertEquals(PaymentStatus.SUCCESS.name(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.name(), order.getStatus());
        verify(paymentRepository).update(any(Payment.class), eq(PaymentStatus.SUCCESS.name()));
    }

    @Test
    void testSetPaymentStatusWithInvalidValue() {
        assertThrows(IllegalArgumentException.class, () ->
                paymentService.setStatus(payments.getFirst(), "INVALID_STATUS")
        );
    }

    @Test
    void testSetPaymentStatusToFailed() {
        Payment payment = payments.getFirst();
        Order order = orders.getFirst();

        when(paymentRepository.getOrder(payment.getId())).thenReturn(order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.name());

        assertEquals(PaymentStatus.REJECTED.name(), result.getStatus());
        assertEquals(OrderStatus.FAILED.name(), order.getStatus());
        verify(paymentRepository).update(any(Payment.class), eq(PaymentStatus.REJECTED.name()));
    }

    // Retrieve Payment Tests
    @Test
    void testGetPaymentByIdValid() {
        when(paymentRepository.findById("payment-id-1")).thenReturn(payments.getFirst());

        Payment result = paymentService.getPayment("payment-id-1");

        assertNotNull(result);
        assertEquals("payment-id-1", result.getId());
        verify(paymentRepository).findById("payment-id-1");
    }

    @Test
    void testGetPaymentByIdInvalid() {
        when(paymentRepository.findById("invalid-id")).thenReturn(null);

        Payment result = paymentService.getPayment("invalid-id");

        assertNull(result);
        verify(paymentRepository).findById("invalid-id");
    }

    // Payment List Tests
    @Test
    void testGetAllPaymentsWhenNotEmpty() {
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
        verify(paymentRepository).findAll();
    }

    @Test
    void testGetAllPaymentsWhenEmpty() {
        when(paymentRepository.findAll()).thenReturn(new ArrayList<>());

        List<Payment> result = paymentService.getAllPayments();

        assertTrue(result.isEmpty());
        verify(paymentRepository).findAll();
    }
}
