package payments.dao;

/**
 * Created by andrew on 01.04.17.
 */
public interface ConnectionWrapper extends AutoCloseable{
    void beginTransaction();
    void commitTransaction();
    void rollbackTransaction();

    @Override
    void close();
}
