package model.dao.mysql;

import model.classes.Genre;
import model.dao.interfaces.GenreDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLGenreDAO implements GenreDAO {
    private final Connection con;

    public MySQLGenreDAO(Connection con){
        this.con = con;
    }

    @Override
    public int insertGenre(Genre o) throws SQLException {
        //Cojer el nombre del genero y comprobar si existe en la bbdd
        String query = "SELECT * FROM genres WHERE name = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1,o.getName().trim().toLowerCase());
        ResultSet rs = pstmt.executeQuery();

        //Si existe devolver la ID del genero
        if (rs.next()) return rs.getInt("genre_id");

        //Si no existe insertarlo a la bbdd i devolver la id
        String insert = "CALL pro_genre_insert(?)";
        PreparedStatement pstmt2 = con.prepareStatement(insert);
        pstmt2.setString(1,o.getName().trim().toLowerCase());
        pstmt2.execute();

        //Despues de insertarlo obtener la id del genero
        ResultSet rs2 = pstmt.executeQuery();
        rs2.next();
        return rs2.getInt("genre_id");
    }
}