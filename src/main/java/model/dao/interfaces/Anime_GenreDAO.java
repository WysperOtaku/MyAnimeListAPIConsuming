package model.dao.interfaces;


import java.sql.SQLException;
import java.util.List;

public interface Anime_GenreDAO {
    void insertAnime_Genre(int anime, List<Integer> genres) throws SQLException;
}