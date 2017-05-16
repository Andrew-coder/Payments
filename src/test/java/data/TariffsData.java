package data;

import payments.model.entity.payment.PaymentTariff;
import payments.model.entity.payment.PaymentType;

import java.math.BigDecimal;

public enum TariffsData {
    A(1, PaymentType.REFILL,0, BigDecimal.valueOf(0.00)),
    B(2,PaymentType.TRANSFER_WITHIN_THIS_BANK,0.05,BigDecimal.valueOf(1.0)),
    C(3,PaymentType.TRANSFER_TO_ANOTHER_BANK_CARD,0.15,BigDecimal.valueOf(5.0)),
    D(4,PaymentType.TRANSFER_TO_ANOTHER_CARD_OF_ONE_USER,0,BigDecimal.valueOf(0.0));

    public PaymentTariff tariff;

    TariffsData(long id, PaymentType type, double paymentRate, BigDecimal fixedRate){
        tariff = new PaymentTariff.Builder()
                .setId(id)
                .setType(type)
                .setPaymentRate(paymentRate)
                .setFixedRate(fixedRate)
                .build();
    }
}