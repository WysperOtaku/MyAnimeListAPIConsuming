package model.classes;

public class Genre {
    public String name;

    public Genre(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-50s",
                "Name: ", name + "\n");
    }
}