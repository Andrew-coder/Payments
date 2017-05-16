package payments.dao.jdbc;

import org.apache.log4j.Logger;
import payments.dao.*;
import payments.dao.exception.DaoException;
import payments.utils.constants.LoggerMessages;
import payments.utils.constants.MessageKeys;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC implementation of dao factory
 */
public class JdbcDaoFactory extends DaoFactory {
    private static final Logger logger = Logger.getLogger(JdbcDaoFactory.class);
    private DataSource dataSource = DataSourceProvider.getInstance();

    /**
     * this method get sql connection from pool and wrap it
     * @return connection wrapper
     */
    @Override
    public ConnectionWrapper getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        }
        catch (SQLException exception) {
            logger.error(LoggerMessages.ERROR_CONNECT_TO_DATABASE);
            throw new DaoException(exception, MessageKeys.ERROR_WITD_DB);
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