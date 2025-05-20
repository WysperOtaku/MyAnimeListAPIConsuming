package model.dao.mysql;

import model.classes.Studio;
import model.dao.interfaces.StudioDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLStudioDAO implements StudioDAO {
    private final Connection con;

    public MySQLStudioDAO(Connection con){
        this.con = con;
    }

    @Override
    public int insertStudio(Studio o) throws SQLException {
        //Cojer el nombre del estudio y comprobar si existe en la bbdd
        String query = "SELECT * FROM studios WHERE name = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1,o.getName());
        ResultSet rs = pstmt.executeQuery();

        //Si existe devolver la ID del estudio
        if (rs.next()) return rs.getInt("studio_id");

        //Si no existe insertarlo a la bbdd i devolver la id
        String insert = "CALL pro_studio_insert(?)";
        PreparedStatement pstmt2 = con.prepareStatement(insert);
        pstmt2.setString(1,o.getName());
        pstmt2.execute();

        //Despues de insertarlo obtener la id del estudio
        ResultSet rs2 = pstmt.executeQuery();
        rs2.next();
        return rs2.getInt("studio_id");
    }
}