package payments.utils.extractors.impl;

import payments.model.entity.BankAccount;
import payments.model.entity.payment.Payment;
import payments.utils.extractors.ResultSetExtactor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentResultSetExtractor implements ResultSetExtactor<Payment> {
    @Override
    public Payment extract(ResultSet set) throws SQLException {
        BankAccountResultSetExtractor accountExtractor = new BankAccountResultSetExtractor();
        PaymentTariffResultSetExtractor tariffExtractor = new PaymentTariffResultSetExtractor();
        Payment.Builder builder = new Payment.Builder()
                .setId(set.getLong("payment_id"))
                .setDate(set.getTimestamp("payment_time"))
                .setMfo(set.getString("mfo"))
                .setPaymentPurpose(set.getString("payment_purpose"))
                .setSender(accountExtractor.extract(set))
                .setSum(set.getBigDecimal("sum"))
                .setTariff(tariffExtractor.extract(set))
                .setUsreou(set.getString("usreou"));
        BankAccount bankAccount = new BankAccount.Builder()
                .setId(set.getLong("recipient_id"))
                .setAccountNumber(set.getString("recipient_number"))
                .build();
        builder.setRecipient(bankAccount);
        return builder.build();
    }
}