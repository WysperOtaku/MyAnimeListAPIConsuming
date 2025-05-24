package api;

import com.google.gson.Gson;
import model.classes.Anime;
import model.classes.TokenInfo;
import model.response.AnimeSearchResponse;
import model.response.AnimeWrapper;
import service.OAuthService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MyAnimeListClient {
    private HttpClient client;
    private TokenInfo token;

    public MyAnimeListClient() {
        this.client = HttpClient.newHttpClient();
        this.token = OAuthService.cargarToken();
    }

    public List<Anime> getTopAnimesFromEndopoint () throws IOException, InterruptedException {
        String endpoint = "https://api.myanimelist.net/v2/anime/ranking?ranking_type=all&limit=100&fields=status,start_season,num_episodes,genres,studios";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Authorization", "Bearer " + token.getAccess_token())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 401) {
            OAuthService.actualizarTokenFile(token);
            token = OAuthService.cargarToken();
            return getTopAnimesFromEndopoint();
        }

        if (response.statusCode() != 200) {
            throw new IOException("Error en la petición: " + response.statusCode());
        }

        Gson gson = new Gson();
        AnimeSearchResponse animeResponse = gson.fromJson(response.body(), AnimeSearchResponse.class);
        List<Anime> animes = new ArrayList<>();
        for (AnimeWrapper anime : animeResponse.getData()) {
            animes.add(anime.node);
        }

        return animes;
    }

    public void getTopAnimesToJson() throws IOException, InterruptedException {
        String endpoint = "https://api.myanimelist.net/v2/anime/ranking?ranking_type=all&limit=100&fields=status,start_season,num_episodes,genres,studios";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Authorization", "Bearer " + token.getAccess_token())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 401) {
            OAuthService.actualizarTokenFile(token);
            token = OAuthService.cargarToken();
            getTopAnimesToJson();
            return;
        }

        if (response.statusCode() != 200) {
            throw new IOException("Error en la petición: " + response.statusCode());
        }

        File json = new File("src/main/resources/animes.json");
        json.createNewFile();
        FileWriter writer = new FileWriter("src/main/resources/animes.json");
        writer.write(response.body());
        writer.close();
    }

    public Anime getAnimeFromEndpoint(int id) throws IOException, InterruptedException {
        String endpoint = "https://api.myanimelist.net/v2/anime/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Authorization", "Bearer " + token.getAccess_token())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 401) {
            OAuthService.actualizarTokenFile(token);
            token = OAuthService.cargarToken();
            return getAnimeFromEndpoint(id);
        }

        if (response.statusCode() != 200) {
            throw new IOException("Error en la peticion: " + response.statusCode());
        }
        Gson gson = new Gson();
        Anime anime = gson.fromJson(response.body(), Anime.class);
        return anime;
    }

    public void getAnimeToJson(int id) throws IOException, InterruptedException {
        String endpoint = "https://api.myanimelist.net/v2/anime/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Authorization", "Bearer " + token.access_token)
                .GET()
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 401) {
            OAuthService.actualizarTokenFile(token);
            token = OAuthService.cargarToken();
            getAnimeToJson(id);
            return;
        }

        if (response.statusCode() != 200) {
            throw new IOException("Error en la peticion: " + response.statusCode());
        }

        File json = new File("src/main/resources/anime.json");
        json.createNewFile();
        FileWriter writer = new FileWriter("src/main/resources/anime.json");
        writer.write(response.body().toString());
        writer.close();
    }
}
