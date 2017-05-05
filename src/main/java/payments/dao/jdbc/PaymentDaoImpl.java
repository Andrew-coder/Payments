package payments.dao.jdbc;

import org.apache.log4j.Logger;
import payments.dao.PaymentDao;
import payments.dao.exception.DaoException;
import payments.model.entity.payment.Payment;
import payments.utils.extractors.impl.PaymentResultSetExtractor;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class PaymentDaoImpl implements PaymentDao {
    private static final Logger logger = Logger.getLogger(PaymentDaoImpl.class);

    private static final String GET_ALL_PAYMENTS = "select p.payment_id, p.sum, p.payment_time, sender.account_id as account_id, sender.account_number as account_number, " +
            "recipient.account_id as sender_id, recipient.account_number as sender_number, " +
            "pt.id, pt.payment_rate, pt.fixed_rate from " +
            "Payments p  " +
            "join BankAccounts sender on sender.account_id = p.sender" +
            "join BankAccounts recipient on recipient.account_id=p.recipient" +
            "join PaymentsTypes pt on pt.id=p.payment_type ";
    private static final String FILTER_BY_ID = " where payment_id = ?;";
    public static final String CREATE_PAYMENT = "insert into `Payment`.`Payments` (`sender`, `recipient`, `sum`, `payment_time`, `payment_type`, `mfo`, `usreou`, `payment_purpose`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

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
            throw new DaoException("dao exception occured when retrieving payment by id", ex);
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
            throw new DaoException("dao exception occured when retrieving all payments", ex);
        }
    }

    @Override
    public void create(Payment payment) {
        Objects.requireNonNull(payment, "Error! Wrong payment object...");
        try(PreparedStatement statement = connection.prepareStatement(CREATE_PAYMENT)){
            statement.setLong(1, payment.getSender().getId());
            statement.setLong(2, payment.getRecipient().getId());
            statement.setBigDecimal(3, payment.getSum());
            statement.setTimestamp(4, convertToTimestamp(payment.getDate()));
            statement.setLong(5, payment.getTariff().getId());
            statement.setString(6, payment.getMfo());
            statement.setString(7, payment.getUsreou());
            statement.setString(8, payment.getPaymentPurpose());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            throw new DaoException("Error occured when creating new card!", ex);
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
}