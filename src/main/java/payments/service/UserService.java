package payments.service;

import payments.model.entity.user.RoleType;
import payments.model.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(int id);
    Optional<User> login (String email , String password);
    List<User> findAll();
    void create(User user);
    void updateUserCards(long id);
}
