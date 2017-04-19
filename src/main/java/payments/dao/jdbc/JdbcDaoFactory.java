package payments.dao.jdbc;

import payments.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoFactory extends DaoFactory {
    private DataSource dataSource = DataSourceProvider.getInstance();
    @Override
    public ConnectionWrapper getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
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
