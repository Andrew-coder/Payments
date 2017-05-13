package payments.dao.jdbc;

import org.apache.log4j.Logger;
import payments.dao.*;
import payments.dao.exception.DaoException;
import payments.utils.constants.LoggerMessages;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TestDaoFactory extends DaoFactory {
    private static final Logger logger = Logger.getLogger(TestDaoFactory.class);
    private DataSource dataSource = DataSourceProvider.getInstance();
    @Override
    public ConnectionWrapper getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        }
        catch (SQLException exception) {
            logger.error(LoggerMessages.ERROR_CONNECT_TO_DATABASE);
            throw new DaoException(exception);
        }
        return new ConnectionWrapperImpl(connection);
    }

    @Override
    public UserDao getUserDao(ConnectionWrapper wrapper) {
        return new UserDaoImpl(extractSqlConnection(wrapper));
    }

    @Override
    public PaymentTariffDao getPaymentTariffDao(ConnectionWrapper wrapper) {
        return new PaymentTariffDaoImpl(extractSqlConnection(wrapper));
    }

    @Override
    public PaymentDao getPaymentDao(ConnectionWrapper wrapper) {
        return new PaymentDaoImpl(extractSqlConnection(wrapper));
    }

    @Override
    public CardDao getCardDao(ConnectionWrapper wrapper) {
        return new CardDaoImpl(extractSqlConnection(wrapper));
    }

    @Override
    public BankAccountDao getBankAccountDao(ConnectionWrapper wrapper) {
        return new BankAccountDaoImpl(extractSqlConnection(wrapper));
    }

    public Connection extractSqlConnection(ConnectionWrapper wrapper) {
        ConnectionWrapperImpl connectionWrapper = (ConnectionWrapperImpl) wrapper;
        return connectionWrapper.getConnection();
    }
}
