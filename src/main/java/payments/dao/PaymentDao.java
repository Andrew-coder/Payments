package payments.dao;

import payments.model.entity.payment.Payment;

import java.util.List;

/**
 * DAO for payment
 */
public interface PaymentDao extends CommonDao<Payment> {
    /**
     * find all payment history
     * @param startFrom
     * @param quantity
     * @return
     */
    List<Payment> findAll(int startFrom, int quantity);

    /**
     * get total count of payments
     * @return
     */
    int getTotalCount();
}