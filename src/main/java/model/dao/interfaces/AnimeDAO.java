package model.dao.interfaces;

import model.classes.Anime;
import model.dao.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface AnimeDAO extends DAO<Anime,Integer> {
    ResultSet readAll() throws SQLException;
}