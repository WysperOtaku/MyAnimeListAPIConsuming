package model.response;

import model.classes.Anime;

import java.util.List;

public class AnimeSearchResponse {
    private List<Anime> data;

    // Constructor sin parametros para que GSON me devuelva el objeto que necesito
    //Usando reflexion GSON va a ir creando la lista 1 a 1
    public AnimeSearchResponse() {}

    public List<Anime> getData() {
        return data;
    }
}
