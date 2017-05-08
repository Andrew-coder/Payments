package payments.service.impl;

import org.apache.log4j.Logger;
import payments.dao.ConnectionWrapper;
import payments.dao.DaoFactory;
import payments.dao.UserDao;
import payments.dao.exception.DaoException;
import payments.model.dto.RegisterData;
import payments.model.entity.user.RoleType;
import payments.model.entity.user.User;
import payments.service.UserService;
import payments.service.exception.ServiceException;
import payments.utils.constants.Attributes;
import payments.utils.constants.ErrorMessages;

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
    public Optional<User> login(String cellphone, String password) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            UserDao userDao = daoFactory.getUserDao(wrapper);
            User user = userDao.findUserByCellphone(cellphone)
                    .filter( person-> password.equals(person.getPassword()))
                    .orElseThrow(()->new ServiceException(ErrorMessages.WRONG_LOGIN_DATA));
            return Optional.of(user);
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
    public void create(User user) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            UserDao userDao = daoFactory.getUserDao(wrapper);
            checkIsUserRegistered(user.getCellphone(), userDao);
            userDao.create(user);
        }
    }

    @Override
    public void updateUserCards(long id) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            UserDao userDao = daoFactory.getUserDao(wrapper);
            userDao.updateUserCards(id);
        }
    }

    private void checkIsUserRegistered(String cellphone, UserDao userDao){
        if(userDao.findUserByCellphone(cellphone).isPresent()){
            logger.error(ErrorMessages.USER_ALREADY_EXISTS);
            throw new ServiceException(ErrorMessages.USER_ALREADY_EXISTS);
        }
    }
}