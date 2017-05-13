package payments.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import payments.dao.jdbc.ConnectionWrapperImpl;

import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class TestConnectionWrapper {
    @Mock
    private Connection connection;

    private ConnectionWrapper wrapper;

    @Before
    public void init() throws SQLException{
        wrapper = new ConnectionWrapperImpl(connection);
        doNothing().when(connection).commit();
        doNothing().when(connection).rollback();
        doNothing().when(connection).close();
        doNothing().when(connection).setAutoCommit(anyBoolean());
        doNothing().when(connection).setTransactionIsolation(anyInt());
    }

    @Test
    public void testBeginTransaction() throws SQLException{
        wrapper.beginTransaction();
        verify(connection).setTransactionIsolation(anyInt());
        verify(connection).setAutoCommit(false);
    }

    @Test
    public void testCommitTransaction() throws SQLException{
        wrapper.commitTransaction();
        verify(connection).setAutoCommit(true);
        verify(connection).commit();
    }

    @Test
    public void testRollBackTransaction() throws SQLException{
        wrapper.rollbackTransaction();
        verify(connection).rollback();
        verify(connection).setAutoCommit(true);
    }

    @Test
    public void testWholeTransaction() throws SQLException {
        wrapper.beginTransaction();
        wrapper.close();
        verify(connection).rollback();
        verify(connection).close();
    }
}