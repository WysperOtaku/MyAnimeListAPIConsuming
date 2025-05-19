package model.dao.mysql;

import model.classes.Anime;
import model.dao.interfaces.AnimeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLAnimeDAO implements AnimeDAO {
    private final Connection con;

    public MySQLAnimeDAO(Connection con){
        this.con = con;
    }
    @Override
    public void create(Anime o) throws SQLException {

    }

    @Override
    public Anime read(Integer key) throws SQLException {
        String procedure = "CALL pro_anime_select(?)";
        PreparedStatement pstmt = con.prepareCall(procedure);
        pstmt.setInt(1,key);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return new Anime(rs.getInt("anime_id"),rs.getString("studio"),rs.getString("name"),
                    rs.getInt("jikan_id"),rs.getFloat("score"),rs.getString("season"),
                    rs.getInt("year"),rs.getBoolean("airing"),rs.getInt("num_episodes"),rs.getString("gen"));
        }
        else{
            throw new SQLException("La ID introducida no existe en la base de datos.");
        }
    }

    @Override
    public void update(Anime o) throws SQLException {

    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}
