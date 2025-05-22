package model.dao.mysql;

import exceptions.ExistingObject;
import model.classes.Anime;
import model.classes.Genre;
import model.classes.Studio;
import model.dao.interfaces.AnimeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLAnimeDAO implements AnimeDAO {
    private final Connection con;

    public MySQLAnimeDAO(Connection con){
        this.con = con;
    }

    @Override
    public void create(Anime o) throws SQLException, ExistingObject {
        //Comprobar si el anime existe
        String select = "SELECT anime_id FROM animes WHERE name = ?";
        PreparedStatement stmt = con.prepareStatement(select);
        stmt.setString(1,o.getName().trim().toLowerCase());
        ResultSet r = stmt.executeQuery();
        if (r.next()) throw new ExistingObject("El anime " + o.getName() + " ya existe en la base de datos.");

        //Cojer el estudio i insertarlo a estudios si no existe
        MySQLStudioDAO studioDAO = new MySQLStudioDAO(con);
        int studio_id = studioDAO.insertStudio(new Studio(o.getStudios().getFirst().getName().trim().toLowerCase()));

        //Cojer los generos i insertarlos a generos si no existen i guardar sus id
        MySQLGenreDAO genreDAO = new MySQLGenreDAO(con);
        List<Integer> genreIds = new ArrayList<>();
        for (Genre g: o.getGenres()) genreIds.add(genreDAO.insertGenre(g));

        //Cojer las id i introducir los datos a la bbdd
        String insert = "CALL pro_anime_insert(?,?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(insert);
        pstmt.setInt(1,studio_id);
        pstmt.setString(2,o.getName());
        pstmt.setInt(3,o.getMal_id());
        pstmt.setString(4,o.getStart_season().getSeason());
        pstmt.setInt(5,o.getStart_season().getYear());
        pstmt.setString(6,o.getStatus());
        pstmt.setInt(7,o.getNum_episodes());
        pstmt.execute();

        //Cojer la id del anime que se acaba de introducir
        String query = "SELECT anime_id FROM animes WHERE name = ?";
        PreparedStatement pstmt2 = con.prepareStatement(query);
        pstmt2.setString(1,o.getName());
        ResultSet rs = pstmt2.executeQuery();
        if (rs.next()){
            int anime_id = rs.getInt("anime_id");

            //Con la id del anime i la lista de id de generos llamar a la funcion para insertarlos
            MySQLAnime_GenreDAO anime_genreDAO = new MySQLAnime_GenreDAO(con);
            anime_genreDAO.insertAnime_Genre(anime_id,genreIds);
        }
    }

    @Override
    public Anime read(Integer key) throws SQLException {
        String procedure = "CALL pro_anime_select_animeID(?)";
        PreparedStatement pstmt = con.prepareStatement(procedure);
        pstmt.setInt(1,key);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return new Anime(rs.getString("studio"),rs.getString("name"),
                    rs.getInt("mal_id"),rs.getString("season"),
                    rs.getInt("year"), rs.getString("status"),
                    rs.getInt("num_episodes"),rs.getString("gen"));
        }
        else{
            throw new SQLException("La ID introducida no existe en la base de datos.");
        }
    }

    @Override
    public ResultSet readAll()throws SQLException {
        String query = "SELECT s.name AS studio, a.name, a.mal_id, a.season, a.year, a.status, a.num_episodes, fun_Genres(a.anime_id) AS genres" +
                " FROM animes a" +
                " LEFT JOIN studios s ON a.studio_id = s.studio_id";
        PreparedStatement pstmt = con.prepareStatement(query);
        return pstmt.executeQuery();
    }

    @Override
    public void update(Anime o) throws SQLException {

    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}
