package payments.dao.jdbc;

import org.apache.log4j.Logger;
import payments.dao.CardDao;
import payments.dao.exception.DaoException;
import payments.model.entity.Card;
import payments.utils.extractors.impl.CardResultSetExtractor;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CardDaoImpl implements CardDao {
    private static final Logger logger = Logger.getLogger(CardDaoImpl.class);
    private static final String GET_ALL_CARDS = "select card_id, card_number, pin, cvv, expire_date, " +
            "user_id, name, surname, email, password, birthDate, role, " +
            "account_id, account_number, balance from (" +
            "Users join (" +
            "Cards join BankAccounts using(account_id)" +
            ") using(user_id)) ";
    private static final String FILTER_BY_ID = " where card_id = ?;";
    public static final String FILTER_BY_USER = " where user_id = ?;";
    private static final String DELETE_CARD = "delete from Cards ";
    private static final String CREATE_CARD = "insert into `Payment`.`Cards` (`card_number`, " +
            "`pin`, `cvv`, `expire_date`, `account_id`, `user_id`) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_CARD = "UPDATE `Payment`.`Cards` SET `card_number`=?, " +
            "`pin`=?, `cvv`=?, `expire_date`=? WHERE `card_id`=?;";

    private Connection connection;
    private CardResultSetExtractor extractor;

    public CardDaoImpl(Connection connection) {
        this.connection = connection;
        extractor = new CardResultSetExtractor();
    }

    @Override
    public Optional<Card> findById(long id) {
        Optional<Card> result = Optional.empty();
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_CARDS+ FILTER_BY_ID)){
            statement.setLong(1,id);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                Card card = extractor.extract(set);
                result = Optional.of(card);
            }
            return result;
        }
        catch(SQLException ex){
            throw new DaoException("dao exception occured when retrieving card by id", ex);
        }
    }

    @Override
    public List<Card> findCardsByUser(long id) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_CARDS + FILTER_BY_USER)){
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            List<Card> cards = new ArrayList<>();
            while(set.next()){
                cards.add(extractor.extract(set));
            }
            return cards;
        }
        catch (SQLException ex){
            throw new DaoException("dao exception occured when retrieving card by user", ex);
        }
    }

    @Override
    public List<Card> findAll() {
        try(Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(GET_ALL_CARDS)){
            List<Card> cards = new ArrayList<>();
            while(set.next()){
                cards.add(extractor.extract(set));
            }
            return cards;
        }
        catch(SQLException ex){
            throw new DaoException("dao exception occured when retrieving all cards", ex);
        }
    }

    @Override
    public void create(Card card) {
        Objects.requireNonNull(card, "Error! Wrong card object...");
        try(PreparedStatement statement = connection.prepareStatement(CREATE_CARD)){
            statement.setString(1, card.getCardNumber());
            statement.setString(2, card.getPin());
            statement.setString(3, card.getCvv());
            statement.setDate(4, java.sql.Date.valueOf(card.getExpireDate().
                    toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            statement.setLong(5, card.getAccount().getId());
            statement.setLong(6, card.getUser().getId());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            throw new DaoException("Error occured when creating new card!", ex);
        }
    }

    @Override
    public void update(Card card) {
        Objects.requireNonNull(card, "Error! Wrong card object...");
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_CARD + FILTER_BY_ID)){
            statement.setString(1, card.getCardNumber());
            statement.setString(2, card.getPin());
            statement.setString(3, card.getCvv());
            statement.setDate(4, java.sql.Date.valueOf(card.getExpireDate().
                    toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            statement.executeUpdate();
        }
        catch (SQLException ex){
            throw new DaoException("Error occured when updating card!", ex);
        }
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
