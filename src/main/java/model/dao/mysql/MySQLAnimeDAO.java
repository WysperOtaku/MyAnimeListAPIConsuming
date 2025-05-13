package model.dao.mysql;

import model.classes.Anime;
import model.dao.interfaces.AnimeDAO;

import java.sql.SQLException;

public class MySQLAnimeDAO implements AnimeDAO {
    @Override
    public void create(Anime o) throws SQLException {

    }

    @Override
    public Anime read(Integer key) throws SQLException {
        return null;
    }

    @Override
    public void update(Anime o) throws SQLException {

    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}
