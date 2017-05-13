package payments.dao.jdbc;

import org.apache.log4j.Logger;
import payments.dao.exception.DaoException;
import payments.dao.UserDao;
import payments.model.entity.user.User;
import payments.utils.constants.LoggerMessages;
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
    private static final String DELETE_USER = "delete from Users ";
    private static final String CREATE_USER = "insert into Users (`name`, `surname`, `cellphone`, `password`, `birthDate`, `role`) VALUES (?,?,?,?,?,?);";
    private static final String UPDATE_USER = "update Users set name=?, surname=?, cellphone=?, password=?, birthDate=?, role=? ";
    private static final String UPDATE_USER_CARDS = "insert into User_has_cards (user_id, card_id) " +
            "select u.user_id, Cards.card_id from Cards " +
            "join Users u using(cellphone) " +
            "left join User_has_cards uhc using(card_id) where uhc.user_id is null and u.user_id=?";

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
                logger.error(LoggerMessages.ERROR_FIND_USER_BY_CELLPHONE + cellphone);
                throw new DaoException(ex);
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
            logger.error(LoggerMessages.ERROR_FIND_USER_BY_ID + id);
            throw new DaoException(ex);
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
            logger.error(LoggerMessages.ERROR_FIND_ALL_USERS);
            throw new DaoException(ex);
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
            statement.setDate(5, new Date(user.getBirthDate().getTime()));
            statement.setString(6, user.getRole().getRoleName());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.ERROR_CREATE_NEW_USER + user.toString());
            throw new DaoException(ex);
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
            statement.setDate(5, new Date(user.getBirthDate().getTime()));
            statement.setString(6, user.getRole().getRoleName());
            statement.setLong(7, user.getId());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.ERROR_UPDATE_USER + user.toString());
            throw new DaoException(ex);
        }
    }

    @Override
    public void updateUserCards(long id) {
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_USER_CARDS)){
            statement.setLong(1, id);
            statement.executeUpdate();
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.ERROR_UPDATE_USER_CARDS+id);
            throw new DaoException(ex);
        }
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement statement = connection.prepareStatement(DELETE_USER+FILTER_BY_ID)){
            statement.setLong(1,id);
            statement.executeUpdate();
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.ERROR_DELETE_USER + id);
            throw new DaoException(ex);
        }
    }
}