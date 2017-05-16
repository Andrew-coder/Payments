package payments.utils.extractors.impl;

import payments.model.entity.BankAccount;
import payments.utils.extractors.ResultSetExtactor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class to extract BankAccount entity
 */
public class BankAccountResultSetExtractor implements ResultSetExtactor<BankAccount>{
    @Override
    public BankAccount extract(ResultSet set) throws SQLException {
        BankAccount.Builder builder = new BankAccount.Builder()
                .setId(set.getLong("account_id"))
                .setAccountNumber(set.getString("account_number"))
                .setBalance(set.getBigDecimal("balance"));
        return builder.build();
    }
}