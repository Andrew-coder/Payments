package payments.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DaoFactory {
    public static final String DB_FILE = "/db.properties";
    private static final String DB_FACTORY_CLASS = "factory.class";

    private static class InstanceHolder{
        private static final DaoFactory instance = setUpDaoFactory();
    }

    public static DaoFactory getInstance(){
        return InstanceHolder.instance;
    }

    public static DaoFactory setUpDaoFactory(){
        DaoFactory factory;
        try {
            InputStream inputStream =
                    DaoFactory.class.getResourceAsStream(DB_FILE);
            Properties dbProps = new Properties();
            dbProps.load(inputStream);
            String factoryClass = dbProps.getProperty(DB_FACTORY_CLASS);
            factory = (DaoFactory) Class.forName(factoryClass).newInstance();
        } catch (IOException | IllegalAccessException|
                InstantiationException |ClassNotFoundException e ) {
            throw new RuntimeException(e);
        }
        return factory;
    }

    public abstract ConnectionWrapper getConnection();
    public abstract UserDao getUserDao(ConnectionWrapper wrapper);
    public abstract PaymentTariffDao getPaymentTariffDao(ConnectionWrapper wrapper);
    public abstract PaymentDao getPaymentDao(ConnectionWrapper wrapper);
    public abstract CardDao getCardDao(ConnectionWrapper wrapper);
    public abstract BankAccountDao getBankAccountDao(ConnectionWrapper wrapper);
}
