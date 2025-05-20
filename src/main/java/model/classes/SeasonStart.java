package model.classes;

public class SeasonStart {
    public int year;
    public String season;

    public SeasonStart() {}

    public SeasonStart(int year, String season) {
        this.year = year;
        this.season = season;
    }

    public int getYear() {
        return year;
    }

    public String getSeason() {
        return season;
    }
}
