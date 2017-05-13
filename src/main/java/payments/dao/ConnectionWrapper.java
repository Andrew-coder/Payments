package payments.dao;

public interface ConnectionWrapper extends AutoCloseable{
    void beginTransaction();
    void beginSerializableTransaction();
    void commitTransaction();
    void rollbackTransaction();

    @Override
    void close();
}
