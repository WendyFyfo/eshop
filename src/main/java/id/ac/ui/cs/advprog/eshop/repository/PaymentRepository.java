package id.ac.ui.cs.advprog.eshop.repository;

import enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {
    private Map<String, Payment> paymentData = new HashMap<>();
    private Map<String, Order> paymentOrder = new HashMap<>();

    public Payment save(Payment payment, Order order) {
        if (payment == null || order == null) {
            throw new IllegalArgumentException("Payment or order cannot be null");
        }
        if (paymentData.containsKey(payment.getId())) {
            throw new IllegalArgumentException("Payment with this ID already exists");
        }

        paymentData.put(payment.getId(), payment);
        paymentOrder.put(payment.getId(), order);

        if ("SUCCESS".equals(payment.getStatus())) {
            order.setStatus(OrderStatus.SUCCESS.getValue());
        } else {
            order.setStatus(OrderStatus.FAILED.getValue());
        }

        return payment;
    }

    public void update(Payment payment, String status) {
        if (payment == null || status == null) {
            throw new IllegalArgumentException("Payment or status cannot be null");
        }
        if (!paymentData.containsKey(payment.getId())) {
            throw new IllegalArgumentException("Payment not found");
        }

        payment.setStatus(status);
        Order order = paymentOrder.get(payment.getId());

        if ("SUCCESS".equals(status)) {
            order.setStatus(OrderStatus.SUCCESS.getValue());
        } else {
            order.setStatus(OrderStatus.FAILED.getValue());
        }
    }

    public Payment findById(String paymentId) {
        if (paymentId == null) {
            throw new NullPointerException("Payment ID cannot be null");
        }
        return paymentData.get(paymentId);
    }

    public List<Payment> findAll() {
        return new ArrayList<>(paymentData.values());
    }

    public Order getOrder(String paymentId) {
        return paymentOrder.get(paymentId);
    }
}

