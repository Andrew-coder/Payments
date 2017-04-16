package payments.dao;

import payments.model.entity.User;

import java.util.Optional;

public interface UserDao extends CommonDao<User>{
    Optional<User> findUserByEmail(String email);
}
