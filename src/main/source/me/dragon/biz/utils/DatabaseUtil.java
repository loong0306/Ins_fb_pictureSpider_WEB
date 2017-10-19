package me.dragon.biz.utils;

/**
 * Created by dragon on 4/18/2017.
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    public Connection getConnection() {
        Connection conn = null;
        String className = "";
        String url = "";
        String user = "";
        String password = "";
        try {
            Properties properties = new Properties();
            properties.load(DatabaseUtil.class.getClassLoader().getResourceAsStream("application.properties"));
            className = properties.getProperty("spring.datasource.driverClassName");
            url = properties.getProperty("spring.datasource.url");
            user = properties.getProperty("spring.datasource.username");
            password = properties.getProperty("spring.datasource.password");
            Class.forName(className);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void getFree(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

