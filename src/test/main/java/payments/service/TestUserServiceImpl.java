package payments.service;

import data.UsersData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import payments.dao.ConnectionWrapper;
import payments.dao.DaoFactory;
import payments.dao.UserDao;
import payments.model.entity.user.User;
import payments.service.exception.ServiceException;
import payments.service.impl.UserServiceImpl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TestUserServiceImpl {
    @Mock
    private UserDao userDao;
    @Mock
    private ConnectionWrapper wrapper;
    @Mock
    private DaoFactory daoFactory;
    private UserService userService;
    private static final String cellphone = "0958016294";

    @Before
    public void init(){
        User existedUser = UsersData.A.user;

        User testUser = UsersData.B.user;
        when(daoFactory.getConnection()).thenReturn(wrapper);
        when((daoFactory.getUserDao(wrapper))).thenReturn(userDao);
        userService = new UserServiceImpl();
        ((UserServiceImpl)userService).setDaoFactory(daoFactory);
        when(userDao.findById(existedUser.getId())).thenReturn(Optional.of(existedUser));
        when(userDao.findUserByCellphone(existedUser.getCellphone())).thenReturn(Optional.of(existedUser));
        doNothing().when(userDao).create(any(User.class));
        when(userDao.findAll()).thenReturn(Arrays.asList(existedUser, testUser));
    }

    @Test
    public void testFindById(){
        long userId = 1;
        Optional<User> user = userService.findById(userId);
        Assert.assertNotNull(user.get());
        Assert.assertEquals(userId, user.get().getId());
        verify(userDao, times(1)).findById(1);
    }

    @Test(expected = ServiceException.class)
    public void testLoginWithWrongCredentials(){
        String wrongPassword="some wrong password";
        userService.login(cellphone, wrongPassword);
        verify(userDao, times(1)).findUserByCellphone(cellphone);
    }

    @Test
    public void testLoginWithCorrectCredentials(){
        String password="andriy";
        userService.login(cellphone, password);
        verify(userDao, times(1)).findUserByCellphone(cellphone);
    }

    @Test(expected = ServiceException.class)
    public void testCreateUserWithExistingCellphone(){
        User user = new User.Builder().setCellphone(cellphone).build();
        userService.create(user);
    }

    @Test
    public void testCreateSerWithCorrectCredentials(){
        String cellphone = "0951031020";
        User user = new User.Builder()
                .setName("nick")
                .setSurname("kendrick")
                .setPassword("nick")
                .setCellphone(cellphone)
                .build();
        when(userDao.findUserByCellphone(cellphone)).thenReturn(Optional.empty());
        userService.create(user);
        verify(userDao, times(1)).findUserByCellphone(cellphone);
        verify(userDao, times(1)).create(user);
    }

    @Test
    public void testFindAll(){
        List<User> users = userService.findAll();
        Assert.assertEquals(2, users.size());
        verify(userDao, times(1)).findAll();
    }

    @Test
    public void testUpdateUserCards(){
        long userId = 1;
        userService.updateUserCards(userId);
        doNothing().when(userDao).updateUserCards(userId);
        verify(userDao, times(1)).updateUserCards(userId);
    }
}