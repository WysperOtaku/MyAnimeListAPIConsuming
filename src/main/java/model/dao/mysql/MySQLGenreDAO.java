package model.dao.mysql;

import model.classes.Genre;
import model.dao.interfaces.GenreDAO;

import java.sql.SQLException;

public class MySQLGenreDAO implements GenreDAO {
    @Override
    public void create(Genre o) throws SQLException {

    }

    @Override
    public Genre read(Integer key) throws SQLException {
        return null;
    }

    @Override
    public void update(Genre o) throws SQLException {

    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}