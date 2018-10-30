package me.sehwa.supremeboard.dao;

import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class ConnectionTest {
    @Test
    public void connectTest() throws IOException {
        Properties properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream("supremeboard.properties"));
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            assertThat(conn).isNotNull();
        }catch (Exception e){
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
