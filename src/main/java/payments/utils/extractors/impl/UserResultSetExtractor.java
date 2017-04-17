package payments.utils.extractors.impl;

import payments.model.entity.User;
import payments.utils.extractors.ResultSetExtactor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetExtractor implements ResultSetExtactor<User>{
    @Override
    public User extract(ResultSet set) throws SQLException {
        return null;
    }
}
