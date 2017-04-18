package payments.utils.extractors.impl;

import payments.model.entity.payment.Payment;
import payments.utils.extractors.ResultSetExtactor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentResultSetExtractor implements ResultSetExtactor<Payment> {
    @Override
    public Payment extract(ResultSet set) throws SQLException {
        return null;
    }
}
