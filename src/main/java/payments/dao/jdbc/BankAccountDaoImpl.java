package payments.dao.jdbc;

import org.apache.log4j.Logger;
import payments.dao.BankAccountDao;
import payments.dao.exception.DaoException;
import payments.model.entity.BankAccount;
import payments.utils.constants.LoggerMessages;
import payments.utils.constants.MessageKeys;
import payments.utils.extractors.impl.BankAccountResultSetExtractor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BankAccountDaoImpl implements BankAccountDao{
    private static final Logger logger = Logger.getLogger(BankAccountDaoImpl.class);
    private static final String GET_ALL_ACCOUNTS = "select account_id, account_number, balance from BankAccounts ";
    private static final String FILTER_BY_ID = "where account_id=?;";
    public static final String CREATE_ACCOUNT = "insert into `Payment`.`BankAccounts` (`account_number`, `balance`) VALUES (?, ?);";
    public static final String UPDATE_ACCOUNT_BALANCE = "update `Payment`.`BankAccounts` set `balance`=? ";
    public static final String FILTER_BY_CARD = "join Cards using(account_id) where Cards.card_id=?";
    public static final String FILTER_BY_NUMBER = "where account_number=?; ";

    private Connection connection;
    private BankAccountResultSetExtractor extractor;

    public BankAccountDaoImpl(Connection connection) {
        this.connection = connection;
        extractor = new BankAccountResultSetExtractor();
    }

    @Override
    public Optional<BankAccount> findById(long id) {
        Optional<BankAccount> result = Optional.empty();
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_ACCOUNTS+ FILTER_BY_ID)){
            statement.setLong(1,id);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                BankAccount account = extractor.extract(set);
                result = Optional.of(account);
            }
            return result;
        }
        catch(SQLException ex){
            logger.error(LoggerMessages.ERROR_FIND_ACCOUNT_BY_ID + id);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
        }
    }

    @Override
    public Optional<BankAccount> findBankAccountByNumber(String number) {
        Optional<BankAccount> result = Optional.empty();
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_ACCOUNTS+ FILTER_BY_NUMBER)){
            statement.setString(1,number);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                BankAccount account = extractor.extract(set);
                result = Optional.of(account);
            }
            return result;
        }
        catch(SQLException ex){
            logger.error(LoggerMessages.ERROR_FIND_ACCOUNT_BY_NUMBER + number);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
        }
    }

    @Override
    public Optional<BankAccount> findBankAccountByCard(long id) {
        Optional<BankAccount> result = Optional.empty();
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_ACCOUNTS+ FILTER_BY_CARD)){
            statement.setLong(1,id);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                BankAccount account = extractor.extract(set);
                result = Optional.of(account);
            }
            return result;
        }
        catch(SQLException ex){
            logger.error(LoggerMessages.ERROR_FIND_ACCOUNT_BY_CARD + id);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
        }
    }

    @Override
    public List<BankAccount> findAll() {
        try(Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(GET_ALL_ACCOUNTS)){
            List<BankAccount> accounts = new ArrayList<>();
            while(set.next()){
                accounts.add(extractor.extract(set));
            }
            return accounts;
        }
        catch(SQLException ex){
            logger.error(LoggerMessages.ERROR_FIND_ALL_ACCOUNT);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
        }
    }

    @Override
    public void create(BankAccount bankAccount) {
        Objects.requireNonNull(bankAccount, "Error! Wrong bank account object...");
        try(PreparedStatement statement = connection.prepareStatement(CREATE_ACCOUNT)){
            statement.setString(1, bankAccount.getAccountNumber());
            statement.setBigDecimal(2, bankAccount.getBalance());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.ERROR_CREATE_ACCOUNT + bankAccount.toString());
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
        }
    }

    @Override
    public void update(BankAccount bankAccount) {
        Objects.requireNonNull(bankAccount, "Error! Wrong bank account object...");
        try(PreparedStatement statement = connection.
                prepareStatement(UPDATE_ACCOUNT_BALANCE + FILTER_BY_ID)){
            statement.setBigDecimal(1, bankAccount.getBalance());
            statement.setLong(2, bankAccount.getId());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.ERROR_UPDATE_ACCOUNT + bankAccount.toString());
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
        }
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}