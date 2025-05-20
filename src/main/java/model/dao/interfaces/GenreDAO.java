package model.dao.interfaces;

import model.classes.Genre;

import java.sql.SQLException;

public interface GenreDAO {
    int insertGenre(Genre o) throws SQLException;

}