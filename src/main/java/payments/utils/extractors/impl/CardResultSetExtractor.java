package payments.utils.extractors.impl;

import payments.model.entity.Card;
import payments.utils.extractors.ResultSetExtactor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardResultSetExtractor implements ResultSetExtactor<Card>{
    @Override
    public Card extract(ResultSet set) throws SQLException {
        return null;
    }
}