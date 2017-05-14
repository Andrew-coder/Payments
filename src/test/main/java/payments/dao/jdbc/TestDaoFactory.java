package payments.dao.jdbc;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import payments.dao.*;
import payments.dao.exception.DaoException;
import payments.utils.constants.LoggerMessages;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDaoFactory extends DaoFactory {
    private static final Logger logger = Logger.getLogger(JdbcDaoFactory.class);
    private DataSource dataSource = DataSourceProvider.getInstance();
    private static final String DUMP_POPULATE_SQL = "dump.sql";
    @Override
    public ConnectionWrapper getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            initTestDatabase(connection);

        }
        catch (SQLException|IOException exception) {
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

    private void initTestDatabase(Connection connection) throws SQLException, IOException{
        File script = new File(
                this.getClass()
                        .getClassLoader()
                        .getResource(DUMP_POPULATE_SQL)
                        .getFile());

        String multiQuery = FileUtils.readFileToString(script, "utf-8");
        try(Statement st = connection.createStatement()) {
            st.execute(multiQuery);
        }
    }
}