package model;

public class Episode {
    public int id;
    public String anime;
    public String name;
    public int num_episode;

    public Episode(int id, String anime, String name, int num_episode){
        this.id = id;
        this.anime = anime;
        this.name = name;
        this.num_episode = num_episode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum_episode() {
        return num_episode;
    }

    public void setNum_episode(int num_episode) {
        this.num_episode = num_episode;
    }

    @Override
    public String toString() {
        return String.format(
                "%-20s %-10s %-20s %-150s %-20s %-50s %-20s %-10s",
                "ID: ", id + "\n",
                "Anime: ", anime + "\n",
                "Name: ", name + "\n",
                "Score: ", num_episode + "\n");
    }
}