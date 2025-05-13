package model.classes;

public class Genre {
    public int id;
    public String name;

    public Genre(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-10s %-20s %-50s",
                "ID: ", id + "\n",
                "Name: ", name + "\n");
    }
}