package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static Connection con = null;
    private static final ConnectDB instance = new ConnectDB();
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBanVeTau3";
    private static final String USER = "sa";
    private static final String PASSWORD = "sapassword";

    private ConnectDB() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ConnectDB getInstance() {
        return instance;
    }

    public void connect() throws SQLException {
        if (con == null || con.isClosed()) {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            instance.connect();
        }
        return con;
    }
}
