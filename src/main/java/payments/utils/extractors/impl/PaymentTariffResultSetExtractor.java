package payments.utils.extractors.impl;

import payments.model.entity.payment.PaymentTariff;
import payments.model.entity.payment.PaymentType;
import payments.utils.extractors.ResultSetExtactor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class to extract Payment tariff entity
 */
public class PaymentTariffResultSetExtractor implements ResultSetExtactor<PaymentTariff> {
    @Override
    public PaymentTariff extract(ResultSet set) throws SQLException {
        PaymentTariff.Builder builder = new PaymentTariff.Builder()
                .setId(set.getLong("type_id"))
                .setPaymentRate(set.getDouble("payment_rate"))
                .setFixedRate(set.getBigDecimal("fixed_rate"))
                .setType(PaymentType.getType(set.getString("payment_name")));
        return builder.build();
    }
}