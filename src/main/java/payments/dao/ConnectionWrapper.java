package payments.dao;

public interface ConnectionWrapper extends AutoCloseable{
    void beginTransaction();
    void commitTransaction();
    void rollbackTransaction();

    @Override
    void close();
}
