package api;

import com.google.gson.Gson;
import model.response.AnimeSearchResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class MyAnimeListClient {
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void searchAnime (String anime) throws InterruptedException, IOException {
        String baseUrl = "https://api.jikan.moe/v4/anime?q=";
        String queryParam = URLEncoder.encode(anime, StandardCharsets.UTF_8);
        String fullUrl = baseUrl + queryParam;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        Gson gson = new Gson();
        AnimeSearchResponse result = gson.fromJson(json, AnimeSearchResponse.class);
    }


}
