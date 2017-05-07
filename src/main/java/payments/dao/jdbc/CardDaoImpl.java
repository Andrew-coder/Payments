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
    private static final String GET_ALL_CARDS = "select c.card_id, c.card_number, c.pin, c.cvv, c.expire_date, c.cellphone," +
            "user_id, name, surname, password, birthDate, role, " +
            "account_id, account_number, balance from (" +
            "Users join (" +
            "User_has_cards join (" +
            "Cards c join BankAccounts using(account_id)" +
            ") using(card_id)" +
            ") using(user_id)) ";
    private static final String FILTER_BY_ID = " where card_id = ?;";
    public static final String FILTER_BY_USER = " where user_id = ?;";
    public static final String FILTER_BY_CARD_NUMBER = " where card_number=?";
    private static final String DELETE_CARD = "delete from Cards ";
    private static final String CREATE_CARD = "insert into `Payment`.`Cards` (`card_number`, " +
            "`pin`, `cvv`, `expire_date`, `cellphone`, `account_id`, `user_id`) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_CARD = "UPDATE `Payment`.`Cards` SET `card_number`=?, " +
            "`pin`=?, `cvv`=?, `expire_date`=? WHERE `card_id`=?;";
    private static final String BLOCK_CARD = "INSERT INTO `Payment`.`BlockCards` (`card_id`) VALUES (?);";
    private static final String UNBLOCK_CARD = "delete from BlockCards where card_id=?";
    public static final String GET_ALL_BLOCKED_CARDS = GET_ALL_CARDS + "join BlockCards using(card_id);";

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
            throw new DaoException("dao exception occurred when retrieving card by id", ex);
        }
    }

    @Override
    public Optional<Card> findCardByNumber(String number) {
        Optional<Card> result = Optional.empty();
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_CARDS+ FILTER_BY_CARD_NUMBER)){
            statement.setString(1, number);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                Card card = extractor.extract(set);
                result = Optional.of(card);
            }
            return result;
        }
        catch(SQLException ex){
            throw new DaoException("dao exception occurred when retrieving card by number", ex);
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
            throw new DaoException("dao exception occurred when retrieving card by user", ex);
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
            throw new DaoException("dao exception occurred when retrieving all cards", ex);
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
            throw new DaoException("Error occurred when creating new card!", ex);
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
            throw new DaoException("Error occurred when updating card!", ex);
        }
    }

    @Override
    public void blockCard(long id) {
        try(PreparedStatement statement = connection.prepareStatement(BLOCK_CARD)){
            statement.setLong(1, id);
            statement.executeUpdate();
        }
        catch (SQLException ex){
            throw new DaoException("Error occurred when blocking card!", ex);
        }
    }

    @Override
    public void unblockCard(long id) {
        try(PreparedStatement statement = connection.prepareStatement(UNBLOCK_CARD)){
            statement.setLong(1, id);
            statement.executeUpdate();
        }
        catch (SQLException ex){
            throw new DaoException("Error occurred when unblocking card!", ex);
        }
    }

    @Override
    public List<Card> findAllBlockedCards() {
        try(Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(GET_ALL_BLOCKED_CARDS)){
            List<Card> cards = new ArrayList<>();
            while(set.next()){
                cards.add(extractor.extract(set));
            }
            return cards;
        }
        catch(SQLException ex){
            throw new DaoException("dao exception occurred when retrieving all blocked cards", ex);
        }
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}