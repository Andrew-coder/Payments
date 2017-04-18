package payments.dao;

import payments.model.entity.user.User;

import java.util.Optional;

public interface UserDao extends CommonDao<User>{
    Optional<User> findUserByEmail(String email);
}
