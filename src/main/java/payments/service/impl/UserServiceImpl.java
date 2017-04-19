package payments.service.impl;

import org.apache.log4j.Logger;
import payments.dao.ConnectionWrapper;
import payments.dao.DaoFactory;
import payments.dao.UserDao;
import payments.model.entity.user.RoleType;
import payments.model.entity.user.User;
import payments.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService{
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private UserServiceImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static class InstanceHolder {
        private static final UserService instance = new UserServiceImpl(DaoFactory.getInstance());
    }

    public static UserService getInstance(){
        return InstanceHolder.instance;
    }

    @Override
    public Optional<User> findById(int id) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            UserDao userDao = daoFactory.getUserDao(wrapper);
            return userDao.findById(id);
        }
    }

    @Override
    public Optional<User> login(String email, String password) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            UserDao userDao = daoFactory.getUserDao(wrapper);
            return userDao.findUserByEmail(email)
                    .filter( person-> password.equals(person.getPassword()));
        }
    }

    @Override
    public List<User> findAll() {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            UserDao userDao = daoFactory.getUserDao(wrapper);
            return userDao.findAll();
        }
    }

    @Override
    public List<User> findUsersByRole(RoleType roleType) {
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
