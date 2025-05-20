package model.dao;

import exceptions.ExistingObject;

import java.sql.SQLException;

public interface DAO<T, K> {
    void create(T o) throws SQLException, ExistingObject;
    T read(K key) throws SQLException;
    void update(T o) throws SQLException;
    void delete(K key) throws SQLException;
}