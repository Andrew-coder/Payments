package payments.service;

import payments.model.dto.payment.AccountTransferData;
import payments.model.dto.payment.CardTransferData;
import payments.model.dto.payment.RefillData;
import payments.model.entity.payment.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Optional<Payment> findById(long id);
    List<Payment> findAll();
    List<Payment> findAll(int startFrom, int quantity);
    int getTotalCount();
    void saveRefillPayment(Payment payment, RefillData data);
    void saveCardTransfer(Payment payment, CardTransferData data);
    void saveAccountTransfer(Payment payment, AccountTransferData data);
}