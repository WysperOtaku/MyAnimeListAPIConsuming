package model.dao;



import java.sql.SQLException;
import java.util.List;

public interface DAO<T, K> {
    void create(T o, boolean copia) throws SQLException;
    T read(K key) throws SQLException;
    void update(T o, int studio_id, List<Integer> genres) throws SQLException;
    void delete(K key) throws SQLException;
}