package payments.utils.extractors.impl;

import payments.model.entity.user.RoleType;
import payments.model.entity.user.User;
import payments.utils.extractors.ResultSetExtactor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class to extract User entity
 */
public class UserResultSetExtractor implements ResultSetExtactor<User>{
    @Override
    public User extract(ResultSet set) throws SQLException {
        User.Builder builder= new User.Builder()
                .setId(set.getLong("user_id"))
                .setName(set.getString("name"))
                .setSurname(set.getString("surname"))
                .setCellphone(set.getString("cellphone"))
                .setPassword(set.getString("password"))
                .setBirthDate(set.getDate("birthDate"))
                .setRole(RoleType.getRole(set.getString("role")));
        return builder.build();
    }
}
