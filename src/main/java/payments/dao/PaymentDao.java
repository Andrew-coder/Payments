package payments.dao;

import payments.model.entity.payment.Payment;

import java.util.List;

public interface PaymentDao extends CommonDao<Payment> {
    List<Payment> findAll(int startFrom, int quantity);
    int getTotalCount();
}