package payments.dao;

import payments.model.entity.payment.PaymentTariff;
import payments.model.entity.payment.PaymentType;

import java.util.Optional;

public interface PaymentTariffDao extends CommonDao<PaymentTariff> {
    Optional<PaymentTariff> findByPaymentType(PaymentType type);
}