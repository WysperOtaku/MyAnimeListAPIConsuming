package model.dao.mysql;

import model.classes.Studio;
import model.dao.interfaces.StudioDAO;

import java.sql.SQLException;

public class MySQLStudioDAO implements StudioDAO {
    @Override
    public void create(Studio o) throws SQLException {

    }

    @Override
    public Studio read(Integer key) throws SQLException {
        return null;
    }

    @Override
    public void update(Studio o) throws SQLException {

    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}