package payments.service;

import payments.model.entity.user.User;

import java.util.List;
import java.util.Optional;

/**
 * User service
 */
public interface UserService {
    Optional<User> findById(long id);
    Optional<User> login (String cellphone , String password);
    List<User> findAll();
    void create(User user);
    void updateUserCards(long id);
}
