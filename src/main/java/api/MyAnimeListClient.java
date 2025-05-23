package api;

import com.google.gson.Gson;
import model.classes.Anime;
import model.classes.TokenInfo;
import model.response.AnimeSearchResponse;
import model.response.AnimeWrapper;
import service.OAuthService;

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

    public List<Anime> getTopAnimes () throws IOException, InterruptedException {
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
            return getTopAnimes();
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
}
