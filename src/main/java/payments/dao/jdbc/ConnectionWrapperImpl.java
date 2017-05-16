package payments.dao.jdbc;

import org.apache.log4j.Logger;
import payments.dao.ConnectionWrapper;
import payments.dao.exception.DaoException;
import payments.utils.constants.LoggerMessages;
import payments.utils.constants.MessageKeys;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This is class which represents connection wrapper for transaction management
 */
public class ConnectionWrapperImpl implements ConnectionWrapper{
    private static final Logger logger = Logger.getLogger(ConnectionWrapperImpl.class);
    private static final int DEFAULT_TRANSACTION_ISOLATION_LEVEL = Connection.TRANSACTION_READ_COMMITTED;
    private static final int SERIALIZABLE_TRANSACTION_ISOLATION_LEVEL = Connection.TRANSACTION_SERIALIZABLE;
    private Connection connection;
    private boolean inTransaction = false;

    public ConnectionWrapperImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * this method start transaction on sql connection
     */
    @Override
    public void beginTransaction() {
        beginTransactionWithIsolationLevel(DEFAULT_TRANSACTION_ISOLATION_LEVEL);
    }

    @Override
    public void beginSerializableTransaction() {
        beginTransactionWithIsolationLevel(SERIALIZABLE_TRANSACTION_ISOLATION_LEVEL);
    }

    private void beginTransactionWithIsolationLevel(int isolationLevel){
        try{
            connection.setTransactionIsolation(isolationLevel);
            connection.setAutoCommit(false);
            inTransaction=true;
        }catch (Exception ex) {
            logger.error(LoggerMessages.CAN_NOT_BEGIN_TRANSACTION);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
        }
    }

    /**
     * this method commit transaction on sql connection
     */
    @Override
    public
    void commitTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
            inTransaction=false;
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.CAN_NOT_COMMIT_TRANSACTION);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
        }
    }

    /**
     * this method rollbacks transaction to previous state before transaction
     */
    @Override
    public void rollbackTransaction() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
            inTransaction=false;
        }
        catch (SQLException ex){
            logger.error(LoggerMessages.CAN_NOT_ROLLBACK_TRANSACTION);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
        }
    }

    /**
     * this method closes sql connection and automatically rollbacks transaction
     */
    @Override
    public void close(){
        if(inTransaction) {
            rollbackTransaction();
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            logger.error(LoggerMessages.CAN_NOT_CLOSE_CONNECTION);
            throw new DaoException(ex, MessageKeys.ERROR_WITD_DB);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}