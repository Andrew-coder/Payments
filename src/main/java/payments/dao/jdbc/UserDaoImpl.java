package payments.dao.jdbc;

import payments.dao.UserDao;
import payments.model.entity.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

        @Override
    public Optional<User> findUserByEmail(String email) {
        return null;
    }

    @Override
    public Optional<User> findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void create(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(int id) {

    }
}
