package model.dao.mysql;

import model.classes.Episode;
import model.dao.interfaces.EpisodeDAO;

import java.sql.SQLException;

public class MySQLEpisodeDAO implements EpisodeDAO {

    @Override
    public void create(Episode o) throws SQLException {

    }

    @Override
    public Episode read(Integer key) throws SQLException {
        return null;
    }

    @Override
    public void update(Episode o) throws SQLException {

    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}