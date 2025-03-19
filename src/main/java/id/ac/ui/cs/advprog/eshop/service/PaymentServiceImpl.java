package id.ac.ui.cs.advprog.eshop.service;

import enums.OrderStatus;
import enums.PaymentMethod;
import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        if (!PaymentMethod.contains(method)) {
            throw new IllegalArgumentException("Invalid payment method");
        }
        Payment payment = new Payment(order.getId(), method, paymentData);
        return paymentRepository.save(payment, order);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        Order order = paymentRepository.getOrder(payment.getId());
        if (order != null) {
            if (Objects.equals(status, PaymentStatus.SUCCESS.name())) {
                order.setStatus(OrderStatus.SUCCESS.getValue());
            } else if (Objects.equals(status, PaymentStatus.REJECTED.name())) {
                order.setStatus(OrderStatus.FAILED.getValue());
            }
        }
        paymentRepository.update(payment, status);
        payment.setStatus(status);
        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
