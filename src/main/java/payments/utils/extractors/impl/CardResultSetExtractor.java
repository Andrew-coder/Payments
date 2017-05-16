package payments.utils.extractors.impl;

import payments.model.entity.Card;
import payments.utils.extractors.ResultSetExtactor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class to extract Card entity
 */
public class CardResultSetExtractor implements ResultSetExtactor<Card>{
    @Override
    public Card extract(ResultSet set) throws SQLException {
        UserResultSetExtractor userExtractor = new UserResultSetExtractor();
        BankAccountResultSetExtractor accountExtractor = new BankAccountResultSetExtractor();
        Card.Builder builder = new Card.Builder()
                .setId(set.getLong("card_id"))
                .setCardNumber(set.getString("card_number"))
                .setCvv(set.getString("cvv"))
                .setExpireDate(set.getDate("expire_date"))
                .setPin(set.getString("pin"))
                .setAccount(accountExtractor.extract(set))
                .setUser(userExtractor.extract(set));
        return builder.build();
    }
}