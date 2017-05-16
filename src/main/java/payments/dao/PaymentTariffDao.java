package payments.dao;

import payments.model.entity.payment.PaymentTariff;
import payments.model.entity.payment.PaymentType;

import java.util.Optional;

/**
 * DAO for payment tariff
 */
public interface PaymentTariffDao extends CommonDao<PaymentTariff> {
    /**
     * find tariff by payment type
     * @param type
     * @return
     */
    Optional<PaymentTariff> findByPaymentType(PaymentType type);
}