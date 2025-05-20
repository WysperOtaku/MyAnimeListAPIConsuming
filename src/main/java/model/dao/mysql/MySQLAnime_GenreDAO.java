package model.dao.mysql;

import model.classes.Genre;
import model.dao.interfaces.Anime_GenreDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MySQLAnime_GenreDAO implements Anime_GenreDAO {
    private final Connection con;

    public MySQLAnime_GenreDAO(Connection con){
        this.con = con;
    }

    @Override
    public void insertAnime_Genre(int anime, List<Integer> genres) throws SQLException {
        String insert = "CALL pro_animes_genres_insert(?,?)";
        PreparedStatement pstmt = con.prepareStatement(insert);
        pstmt.setInt(1,anime);
        for (Integer id: genres) {
            pstmt.setInt(2,id);
            pstmt.execute();
        }
    }
}