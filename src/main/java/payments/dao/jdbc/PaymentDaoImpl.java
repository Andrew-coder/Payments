package payments.dao.jdbc;

import org.apache.log4j.Logger;
import payments.dao.PaymentDao;
import payments.dao.exception.DaoException;
import payments.model.entity.BankAccount;
import payments.model.entity.payment.Payment;
import payments.utils.constants.LoggerMessages;
import payments.utils.extractors.impl.PaymentResultSetExtractor;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class PaymentDaoImpl implements PaymentDao {
    private static final Logger logger = Logger.getLogger(PaymentDaoImpl.class);

    private static final String GET_ALL_PAYMENTS = "select p.payment_id, p.sum, p.payment_time, p.mfo, p.usreou, p.payment_purpose," +
            "            sender.account_id as account_id, sender.account_number as account_number, sender.balance, " +
            "            recipient.account_id as recipient_id, recipient.account_number as recipient_number, " +
            "            pt.type_id, pt.payment_rate, pt.fixed_rate, pt.payment_name from " +
            "            Payments p  " +
            "            left join BankAccounts sender on sender.account_id = p.sender " +
            "            left join BankAccounts recipient on recipient.account_id=p.recipient " +
            "            join PaymentsTypes pt on pt.type_id=p.payment_type " +
            "            order by p.payment_id desc ";
    private static final String GET_TOTAL_COUNT = "select count(payment_id) as count from Payments;";
    private static final String FILTER_BY_ID = " where payment_id = ?;";
    private static final String CREATE_PAYMENT = "insert into `Payment`.`Payments` (`sender`, `recipient`, `sum`, `payment_time`, `payment_type`, `mfo`, `usreou`, `payment_purpose`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String LIMIT = " limit ?,?";
    private Connection connection;
    private PaymentResultSetExtractor extractor;

    public PaymentDaoImpl(Connection connection) {
        this.connection = connection;
        extractor = new PaymentResultSetExtractor();
    }

    @Override
    public Optional<Payment> findById(long id) {
        Optional<Payment> result = Optional.empty();
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_PAYMENTS + FILTER_BY_ID)){
            statement.setLong(1,id);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                Payment payment = extractor.extract(set);
                result = Optional.of(payment);
            }
            return result;
        }
        catch(SQLException ex){
            logger.error(LoggerMessages.ERROR_FIND_PAYMENT_BY_ID + id);
            throw new DaoException(ex);
        }
    }

    @Override
    public List<Payment> findAll() {
        try(Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(GET_ALL_PAYMENTS)){
            List<Payment> payments = new ArrayList<>();
            while(set.next()){
                payments.add(extractor.extract(set));
            }
            return payments;
        }
        catch(SQLException ex){
            logger.error(LoggerMessages.ERROR_FIND_ALL_PAYMENTS);
            throw new DaoException(ex);
        }
    }

    @Override
    public List<Payment> findAll(int startFrom, int quantity) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_PAYMENTS + LIMIT)){
            statement.setInt(1, startFrom);
            statement.setInt(2, quantity);
            ResultSet set = statement.executeQuery();
            List<Payment> payments = new ArrayList<>();
            while(set.next()){
                payments.add(extractor.extract(set));
            }
            return payments;
        }
        catch(SQLException ex){
            logger.error(LoggerMessages.ERROR_FIND_ALL_PAYMENTS_BY_OFFSET + startFrom + quantity);
            throw new DaoException(ex);
        }
    }

    @Override
    public int getTotalCount() {
        try(Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(GET_TOTAL_COUNT)){
            set.next();
            return set.getInt("count");
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.ERROR_RETRIEVE_PAYMENTS_COUNT);
            throw new DaoException(ex);
        }
    }

    @Override
    public void create(Payment payment) {
        Objects.requireNonNull(payment, "Error! Wrong payment object...");
        try(PreparedStatement statement = connection.prepareStatement(CREATE_PAYMENT)){
            setAccountIdOrNull(payment.getSender(),1, statement);
            setAccountIdOrNull(payment.getRecipient(),2,statement);
            statement.setBigDecimal(3, payment.getSum());
            statement.setTimestamp(4, convertToTimestamp(payment.getDate()));
            statement.setLong(5, payment.getTariff().getId());
            statement.setString(6, payment.getMfo());
            statement.setString(7, payment.getUsreou());
            statement.setString(8, payment.getPaymentPurpose());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.ERROR_CREATE_NEW_PAYMENT);
            throw new DaoException(ex);
        }
    }

    @Override
    public void update(Payment payment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    private Timestamp convertToTimestamp(Date date){
        return new Timestamp(date.getTime());
    }

    private void setAccountIdOrNull(BankAccount account, int parameterIndex, PreparedStatement statement) throws SQLException{
        if(account!=null){
            statement.setLong(parameterIndex,account.getId());
        }
        else {
            statement.setNull(parameterIndex, Types.BIGINT);
        }
    }
}