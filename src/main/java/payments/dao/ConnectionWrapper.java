package payments.dao;

/**
 * this connection wrapper defines basic methods to interact with database
 */
public interface ConnectionWrapper extends AutoCloseable{
    void beginTransaction();
    void beginSerializableTransaction();
    void commitTransaction();
    void rollbackTransaction();

    @Override
    void close();
}
