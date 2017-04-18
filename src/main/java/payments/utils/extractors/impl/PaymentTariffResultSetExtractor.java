package payments.utils.extractors.impl;

import payments.model.entity.payment.PaymentTariff;
import payments.utils.extractors.ResultSetExtactor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentTariffResultSetExtractor implements ResultSetExtactor<PaymentTariff> {
    @Override
    public PaymentTariff extract(ResultSet set) throws SQLException {
        return null;
    }
}
