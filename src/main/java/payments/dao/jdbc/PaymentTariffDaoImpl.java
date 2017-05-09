package payments.dao.jdbc;

import org.apache.log4j.Logger;
import payments.dao.PaymentTariffDao;
import payments.dao.exception.DaoException;
import payments.model.entity.payment.PaymentTariff;
import payments.model.entity.payment.PaymentType;
import payments.utils.constants.LoggerMessages;
import payments.utils.extractors.impl.PaymentTariffResultSetExtractor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PaymentTariffDaoImpl implements PaymentTariffDao{
    private static final Logger logger = Logger.getLogger(PaymentTariffDaoImpl.class);
    private static final String GET_ALL_TARIFFS = "select id, payment_name, payment_rate, fixed_rate from PaymentsTypes";
    private static final String FILTER_BY_ID = " where id = ?;";
    private static final String FILTER_BY_PAYMENT_TYPE = " where payment_name = ?;";
    private static final String UPDATE_TARIFF = "update `Payment`.`PaymentsTypes` set `payment_rate`=?, `fixed_rate`=? ";
    private Connection connection;
    private PaymentTariffResultSetExtractor extractor;

    public PaymentTariffDaoImpl(Connection connection) {
        this.connection = connection;
        extractor = new PaymentTariffResultSetExtractor();
    }

    @Override
    public Optional findById(long id) {
        Optional<PaymentTariff> result = Optional.empty();
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_TARIFFS+ FILTER_BY_ID)){
            statement.setLong(1,id);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                PaymentTariff tariff = extractor.extract(set);
                result = Optional.of(tariff);
            }
            return result;
        }
        catch(SQLException ex){
            logger.error(LoggerMessages.ERROR_FIND_TARIFF_BY_ID + id);
            throw new DaoException(ex);
        }
    }

    @Override
    public Optional<PaymentTariff> findByPaymentType(PaymentType type) {
        Optional<PaymentTariff> result = Optional.empty();
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_TARIFFS+FILTER_BY_PAYMENT_TYPE)){
            statement.setString(1, type.getTypeName());
            ResultSet set = statement.executeQuery();
            if(set.next()){
                PaymentTariff tariff = extractor.extract(set);
                result = Optional.of(tariff);
            }
            return result;
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.ERROR_FIND_TARIFF_BY_TYPE + type.name());
            throw new DaoException(ex);
        }
    }

    @Override
    public List findAll() {
        try(Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(GET_ALL_TARIFFS)){
            List<PaymentTariff> tariffs = new ArrayList<>();
            while(set.next()){
                tariffs.add(extractor.extract(set));
            }
            return tariffs;
        }
        catch(SQLException ex){
            logger.error(LoggerMessages.ERROR_FIND_ALL_TARIFFS);
            throw new DaoException(ex);
        }
    }

    @Override
    public void create(PaymentTariff tariff) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(PaymentTariff tariff) {
        Objects.requireNonNull(tariff, "Error! Wrong payment tariff object...");
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_TARIFF + FILTER_BY_ID)){
            statement.setDouble(1, tariff.getPaymentRate());
            statement.setBigDecimal(2, tariff.getFixedRate());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.ERROR_UPDATE_TARIFF + tariff.toString());
            throw new DaoException(ex);
        }
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}