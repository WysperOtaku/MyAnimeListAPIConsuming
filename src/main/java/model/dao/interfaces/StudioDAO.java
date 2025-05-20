package model.dao.interfaces;

import model.classes.Studio;

import java.sql.SQLException;

public interface StudioDAO {
    int insertStudio(Studio o) throws SQLException;
}