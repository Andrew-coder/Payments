package payments.dao.jdbc;

import data.UsersData;
import org.junit.*;
import payments.dao.ConnectionWrapper;
import payments.dao.DaoFactory;
import payments.dao.UserDao;
import payments.model.entity.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDaoIntegrationTest {
    private DaoFactory daoFactory = TestDaoFactory.getInstance();
    private UserDao userDao;
    private ConnectionWrapper wrapper;
    private List<User> testUsers;

    {
        testUsers = new ArrayList<>();
        for(UsersData data: UsersData.values()){
            testUsers.add(data.user);
        }
    }

    @Before
    public void init(){
        wrapper=daoFactory.getConnection();
        userDao = daoFactory.getUserDao(wrapper);
        wrapper.beginTransaction();
    }

    @After
    public void tearDown(){
        wrapper.rollbackTransaction();
        wrapper.close();
    }

    @BeforeClass
    public static void initTestDB() throws Exception{
        new TestDatabaseInitializer().initTestJdbcDB();
    }

    @Test
    public void testFindAll(){
        List<User> users = userDao.findAll();
        assertEquals(users, testUsers);
    }

    @Test
    public void testFindById(){
        User expectedUser = testUsers.get(0);
        long id = expectedUser.getId();
        User user = userDao.findById(id).get();
        assertEquals(user, expectedUser);
    }

    @Test
    public void testFindUserByCellphone(){
        User expectedUser = testUsers.get(1);
        String cellphone = expectedUser.getCellphone();
        User user = userDao.findUserByCellphone(cellphone).get();
        assertEquals(user, expectedUser);
    }

    @Test
    public void testCreateUser(){
        User newUser = testUsers.get(2);
        newUser.setCellphone("0950000000");
        userDao.create(newUser);
        String cellphone = newUser.getCellphone();
        User expectedUser = userDao.findUserByCellphone(cellphone).get();
        newUser.setId(expectedUser.getId());
        assertEquals(newUser, expectedUser);
        assertEquals(testUsers.size()+1, userDao.findAll().size());
    }

    @Test
    public void testUpdateUser(){
        User updatedUser = testUsers.get(3);
        updatedUser.setName("name");
        updatedUser.setPassword("password");
        userDao.update(updatedUser);
        long id = updatedUser.getId();
        User expectedUser = userDao.findById(id).get();
        assertEquals(updatedUser, expectedUser);
    }

    @Test
    public void testDeleteUser(){
        long id = testUsers.get(0).getId();
        userDao.delete(id);
        assertEquals(testUsers.size()-1,userDao.findAll().size());
    }
}