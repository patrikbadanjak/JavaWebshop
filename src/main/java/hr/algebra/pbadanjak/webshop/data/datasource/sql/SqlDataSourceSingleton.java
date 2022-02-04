package hr.algebra.pbadanjak.webshop.data.datasource.sql;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class SqlDataSourceSingleton {

    private SqlDataSourceSingleton() {}

    private static DataSource instance;

    private static final String DB_PROPS = "sqldb.properties";
    private static final String URL_PROP = "url";
    private static final String USERNAME_PROP = "user";
    private static final String PASSWORD_PROP = "password";

    public static DataSource getInstance() {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    private static DataSource createInstance() {
        Properties dbProperties = new Properties();

        try {
            dbProperties.load(SqlDataSourceSingleton.class.getClassLoader().getResourceAsStream(DB_PROPS));
        } catch (IOException e) {
            e.printStackTrace();
        }

        SQLServerDataSource dataSource = new SQLServerDataSource();

        dataSource.setURL(dbProperties.getProperty(URL_PROP));
        dataSource.setUser(dbProperties.getProperty(USERNAME_PROP));
        dataSource.setPassword(dbProperties.getProperty(PASSWORD_PROP));

        return dataSource;
    }
}
