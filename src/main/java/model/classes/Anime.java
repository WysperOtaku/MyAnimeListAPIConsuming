package model.classes;

import java.util.List;

public class Anime {
    public String studio;
    public String name;
    public int mal_id;
    public float score;
    public String season;
    public int year;
    public boolean airing;
    public int num_episodes;
    public List<Genre> genres;

    public Anime(List<Studio> studio, String name, int jikan_id, float score, String season, int year, boolean airing, int num_episodes, List<Genre> genres){
        this.studio = studio.get(0).getName();
        this.name = name;
        this.mal_id = jikan_id;
        this.score = score;
        this.season = season;
        this.year = year;
        this.airing = airing;
        this.num_episodes = num_episodes;
        this.genres = genres;
    }
    public Anime () {}

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

    public int getJikan_id() {
        return mal_id;
    }

    public void setJikan_id(int jikan_id) {
        this.mal_id = jikan_id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAiring() {
        return airing;
    }

    public void setAiring(boolean airing) {
        this.airing = airing;
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
                "%-20s %-50s %-20s %-150s %-20s %-10s %-20s %-10s %-20s %-10s %-20s %-5s %-20s %-10s %-20s %-10s %-20s %50s",
                "Studio: ", studio + "\n",
                "Name: ", name + "\n",
                "Jikan ID: ", mal_id + "\n",
                "Score: ", score + "\n",
                "Season:", season + "\n",
                "Year: ", year + "\n",
                "Airing: ", airing + "\n",
                "Number Episodes: ", num_episodes + "\n",
                "Genres: ", animeGenres(genres));
    }
}