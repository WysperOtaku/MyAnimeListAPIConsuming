package model.classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Anime {
    public String studio;
    public List<Studio> studios;
    @SerializedName("title")
    public String name;
    @SerializedName("id")
    public int mal_id;
    public String status;
    public SeasonStart start_season;
    public int num_episodes;
    public List<Genre> genres;
    public String genre;

    public Anime () {}

    // TODO: falta cambiar el constructor para que le puedas enchufar el año y la season y te haga el objeto SeasonStart, los parametros estan tal cual, cambia solo el constructor
    public Anime(String studio, String name, int mal_id, String season, int year, int num_episodes, String genre){
        this.studio = studio;
        this.name = name;
        this.mal_id = mal_id;
        this.num_episodes = num_episodes;
        this.genre = genre;
    }

    // TODO: no se si te puede llegar a hacer falta getter y setters nuevos, revisalo!

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMal_id() {
        return mal_id;
    }

    public void setMal_id(int mal_id) {
        this.mal_id = mal_id;
    }

    public int getNum_episodes() {
        return num_episodes;
    }

    public void setNum_episodes(int num_episodes) {
        this.num_episodes = num_episodes;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public static String animeGenres(List<Genre> genres){
        String all_genres = "";
        for (Genre g: genres) all_genres += g + "   ";
        return all_genres;
    }

    @Override
    public String toString() {
        return String.format(
                "%-20s %-50s \n%-20s %-150s \n%-20s %-10s \n%-20s %-10s \n%-20s %-10s \n%-20s %-5s \n%-20s %-10s \n%-20s %-10s \n%-20s %-100s",
                "Studio: ", studio,
                "Name: ", name,
                "API ID: ", mal_id,
                "Number Episodes: ", num_episodes,
                "Genres: ", genre);
    }
}