package payments.dao.jdbc;

import org.apache.log4j.Logger;
import payments.dao.CardDao;
import payments.dao.exception.DaoException;
import payments.model.entity.Card;
import payments.utils.constants.LoggerMessages;
import payments.utils.constants.MessageKeys;
import payments.utils.extractors.impl.CardResultSetExtractor;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of card dao, which works with MySql using jdbc
 */
public class CardDaoImpl implements CardDao {
    private static final Logger logger = Logger.getLogger(CardDaoImpl.class);
    private static final String GET_ALL_CARDS = "select c.card_id, c.card_number, c.pin, c.cvv, c.expire_date, c.cellphone," +
            "            u.user_id, name, surname, password, birthDate, role, " +
            "            ba.account_id, account_number, balance from (" +
            "            Users u join (" +
            "            User_has_cards uhc join (" +
            "            Cards c join BankAccounts ba on c.account_id=ba.account_id" +
            "            ) on uhc.card_id=c.card_id" +
            "            ) on uhc.user_id = u.user_id) ";
    private static final String FILTER_BY_ID = " where c.card_id = ?;";
    public static final String FILTER_BY_USER = " where u.user_id = ?;";
    public static final String FILTER_BY_CARD_NUMBER = " where card_number=?";
    private static final String UPDATE_CARD = "UPDATE Cards SET pin=?, cvv=? WHERE card_id=?;";
    private static final String BLOCK_CARD = "INSERT INTO BlockCards (card_id) VALUES (?);";
    private static final String UNBLOCK_CARD = "delete from BlockCards where card_id=?";
    public static final String GET_ALL_BLOCKED_CARDS = GET_ALL_CARDS + "join BlockCards bc on c.card_id=bc.card_id;";

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
            logger.error(LoggerMessages.ERROR_FIND_CARD_BY_ID + id);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
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
            logger.error(LoggerMessages.ERROR_FIND_CARD_BY_NUMBER + number);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
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
            logger.error(LoggerMessages.ERROR_FIND_CARDS_BY_USER + id);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
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
            logger.error(LoggerMessages.ERROR_FIND_ALL_CARDS);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
        }
    }

    @Override
    public void create(Card card) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Card card) {
        Objects.requireNonNull(card, "Error! Wrong card object...");
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_CARD)){
            statement.setString(1, card.getPin());
            statement.setString(2, card.getCvv());
            statement.setLong(3, card.getId());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.ERROR_UPDATE_CARD + card.toString());
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
        }
    }

    @Override
    public void blockCard(long id) {
        try(PreparedStatement statement = connection.prepareStatement(BLOCK_CARD)){
            statement.setLong(1, id);
            statement.executeUpdate();
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.ERROR_BLOCK_CARD + id);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
        }
    }

    @Override
    public void unblockCard(long id) {
        try(PreparedStatement statement = connection.prepareStatement(UNBLOCK_CARD)){
            statement.setLong(1, id);
            statement.executeUpdate();
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.ERROR_UNBLOCK_CARD + id);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
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
            logger.error(LoggerMessages.ERROR_FIND_ALL_BLOCK_CARDS);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
        }
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}