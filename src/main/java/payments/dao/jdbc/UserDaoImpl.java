package payments.dao.jdbc;

import org.apache.log4j.Logger;
import payments.dao.exception.DaoException;
import payments.dao.UserDao;
import payments.model.entity.user.User;
import payments.utils.extractors.impl.UserResultSetExtractor;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private static final String GET_ALL_USERS = "select user_id, name, surname, cellphone, password, birthDate,  role from Users ";
    private static final String FILTER_BY_ID = " where user_id = ?;";
    private static final String FILTER_BY_CELLPHONE = " where cellphone=?;";
    private static final String FILTER_BY_ROLE = " where role=?;";
    private static final String DELETE_USER = "delete from Users ";
    private static final String CREATE_USER = "insert into Users (`name`, `surname`, `cellphone`, `password`, `birthDate`, `role`) VALUES (?,?,?,?,?,?);";
    private static final String UPDATE_USER = "update `Payment`.`Users` set `name`=?, `surname`=?, `cellphone`=?, `password`=?, `birthDate`=?, `role`=? ";

    private Connection connection;
    private UserResultSetExtractor extractor;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
        extractor = new UserResultSetExtractor();
    }

        @Override
    public Optional<User> findUserByCellphone(String cellphone) {
            Optional<User> result = Optional.empty();
            try(PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS+ FILTER_BY_CELLPHONE)){
                statement.setString(1,cellphone);
                ResultSet set = statement.executeQuery();
                if(set.next()){
                    User user = extractor.extract(set);
                    result = Optional.of(user);
                }
                return result;
            }
            catch(SQLException ex){
                throw new DaoException("dao exception occurred when retrieving user by email", ex);
            }
    }

    @Override
    public Optional<User> findById(long id) {
        Optional<User> result = Optional.empty();
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS+ FILTER_BY_ID)){
            statement.setLong(1,id);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                User user = extractor.extract(set);
                result = Optional.of(user);
            }
            return result;
        }
        catch(SQLException ex){
            throw new DaoException("dao exception occurred when retrieving user by id", ex);
        }
    }

    @Override
    public List<User> findAll() {
        try(Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(GET_ALL_USERS)){
            List<User> users = new ArrayList<>();
            while(set.next()){
                users.add(extractor.extract(set));
            }
            return users;
        }
        catch(SQLException ex){
            throw new DaoException("dao exception occurred when retrieving all users", ex);
        }
    }

    @Override
    public void create(User user) {
        Objects.requireNonNull(user, "Error! Wrong user object...");
        try(PreparedStatement statement = connection.prepareStatement(CREATE_USER)){
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getCellphone());
            statement.setString(4, user.getPassword());
            statement.setDate(5, java.sql.Date.valueOf(user.getBirthDate().
                    toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            statement.setString(6, user.getRole().getRoleName());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            throw new DaoException("Error occurred when creating new user!", ex);
        }
    }

    @Override
    public void update(User user) {
        Objects.requireNonNull(user, "Error! Wrong user object...");
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_USER + FILTER_BY_ID)){
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getCellphone());
            statement.setString(4, user.getPassword());
            statement.setDate(5, java.sql.Date.valueOf(user.getBirthDate().
                    toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            statement.setString(6, user.getRole().getRoleName());
            statement.setLong(7, user.getId());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            throw new DaoException("Error occurred when updating user!", ex);
        }
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement statement = connection.prepareStatement(DELETE_USER+FILTER_BY_ID)){
            statement.setLong(1,id);
            statement.executeUpdate();
        }
        catch (SQLException ex){
            throw new DaoException("Error occurred when deleting user!", ex);
        }
    }
}