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
        PaymentTariffResultSetExtractor tarrifExtractor = new PaymentTariffResultSetExtractor();
        Payment.Builder builder = new Payment.Builder()
                .setId(set.getLong("payment_id"))
                .setDate(set.getDate("payment_time"))
                .setMfo(set.getString("mfo"))
                .setPaymentPurpose(set.getString("payment_purpose"))
                .setSender(accountExtractor.extract(set))
                .setSum(set.getLong("sum"))
                .setTariff(tarrifExtractor.extract(set))
                .setUsreou(set.getString("usreou"));
        BankAccount bankAccount = new BankAccount.Builder()
                .setId(set.getLong("sender_id"))
                .setAccountNumber("sender_number")
                .build();
        builder.setRecipient(bankAccount);
        return builder.build();
    }
}
