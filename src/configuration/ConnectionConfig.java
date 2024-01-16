package configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionConfig {
    private String url;
    private String username;
    private String password;
    public ConnectionConfig() {
        this.url = "jdbc:postgresql://localhost/"+System.getenv("DB_URL");
        this.username = System.getenv("DB_USERNAME");
        this.password = System.getenv("DB_PASSWORD");
    }

    public Connection createConnection(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(
                    this.url, this.username, this.password
            );

            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
