package payments.dao.jdbc;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;
import org.apache.commons.io.FileUtils;

public class TestDatabaseInitializer {
    private static final String DUMP_SQL = "dump.sql";
    private static final String JDBC_DRIVER = "jdbc.driver";
    private static final String JDBC_URL = "jdbc.url";
    private static final String JDBC_USER = "user";
    private static final String JDBC_PASSWORD = "password";

    private String dumpPopulate;

    TestDatabaseInitializer() {
        this(DUMP_SQL);
    }

    private TestDatabaseInitializer(String dumpPopulate) {
        this.dumpPopulate = dumpPopulate;
    }

    void initTestJdbcDB() throws Exception {
        ResourceBundle jdbcProperties = ResourceBundle.getBundle("db");

        File script = new File(
                this.getClass()
                        .getClassLoader()
                        .getResource(dumpPopulate)
                        .getFile());

        String multiQuery = FileUtils.readFileToString(script, "utf-8");
        Class.forName(jdbcProperties.getString(JDBC_DRIVER));
        try (Connection con = DriverManager.getConnection(
                jdbcProperties.getString(JDBC_URL),
                jdbcProperties.getString(JDBC_USER),
                jdbcProperties.getString(JDBC_PASSWORD));
             Statement st = con.createStatement()) {

            st.execute(multiQuery);
        }
    }
}