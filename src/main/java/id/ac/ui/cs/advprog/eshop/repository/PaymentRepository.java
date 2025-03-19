package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PaymentRepository {
    public Payment save(Payment payment, Order order) {
        return null;
    }

    public void update(Payment payment, String status) {
    }

    public Payment findById(String id) {
        return null;
    }

    public List<Payment> findAll() {
        return null;
    }

    public Order getOrder(String id) {
        return null;
    }
}
