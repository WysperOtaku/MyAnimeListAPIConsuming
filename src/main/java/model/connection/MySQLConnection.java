package model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static Connection mysqlConnection() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/anime_db", user = "permatrago", passw = "bloste_myanimelist";
        return DriverManager.getConnection(url,user,passw);
    }
}