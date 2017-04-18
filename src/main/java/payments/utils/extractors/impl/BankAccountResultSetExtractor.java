package payments.utils.extractors.impl;

import payments.model.entity.BankAccount;
import payments.utils.extractors.ResultSetExtactor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccountResultSetExtractor implements ResultSetExtactor<BankAccount>{
    @Override
    public BankAccount extract(ResultSet set) throws SQLException {
        return null;
    }
}