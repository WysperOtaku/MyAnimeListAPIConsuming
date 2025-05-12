package model;

public class Anime {
    public int id;
    public String studio;
    public String name;
    public int jikan_id;
    public float score;
    public String season;
    public int year;
    public boolean airing;
    public int num_episodes;
    public String[] genres;

    public Anime(int id, String studio, String name, int jikan_id, float score, String season, int year, boolean airing, int num_episodes, String[] genres){
        this.id = id;
        this.studio = studio;
        this.name = name;
        this.jikan_id = jikan_id;
        this.score = score;
        this.season = season;
        this.year = year;
        this.airing = airing;
        this.num_episodes = num_episodes;
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        return jikan_id;
    }

    public void setJikan_id(int jikan_id) {
        this.jikan_id = jikan_id;
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

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public static String animeGenres(String[] genres){
        String all_genres = "";
        for (String g: genres) all_genres += g + "   ";
        return all_genres;
    }

    @Override
    public String toString() {
        return String.format(
                "%-20s %-10s %-20s %-50s %-20s %-150s %-20s %-10s %-20s %-10s %-20s %-10s %-20s %-5s %-20s %-10s %-20s %-10s %-20s %50s",
                "ID: ", id + "\n",
                "Studio: ", studio + "\n",
                "Name: ", name + "\n",
                "Jikan ID: ", jikan_id + "\n",
                "Score: ", score + "\n",
                "Season:", season + "\n",
                "Year: ", year + "\n",
                "Airing: ", airing + "\n",
                "Number Episodes: ", num_episodes + "\n",
                "Genres: ", animeGenres(genres));
    }
}