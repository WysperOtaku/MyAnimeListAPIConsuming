package service;

import api.ApiConfig;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;
import model.classes.TokenInfo;
import view.View;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class OAuthService {

    public static String crearURL() throws IOException {
        ApiConfig conf = ApiConfig.load("main/resources/config.json");
        String url = String.format("https://myanimelist.net/v1/oauth2/authorize?response_type=code&client_id=%s&redirect_uri=%s&code_challenge=%s&code_challenge_method=plain",
                conf.client_id, URLEncoder.encode(conf.redirect_uri, StandardCharsets.UTF_8), conf.code_challenge);
        return url;
    }

    public static String esperarCode() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        final String[] codeContainer = new String[1];

        server.createContext("/callback", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            String[] partesQuery = query.split("&");

            for (String parte : partesQuery) {
                if (parte.startsWith("code=")) {
                    codeContainer[0] = parte.substring("code=".length());
                    break;
                }
            }
            String response = "Código recibido. Ya puedes volver al programa.";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            server.stop(1);
        });
        server.start();
        View.mostrarMsg("Esperando redireccion a http://localhost:8080/callback...");
        while (codeContainer[0] == null) {
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
        return codeContainer[0];
    }

    public static TokenInfo intercambiarCodePorToken (String code) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        ApiConfig conf = ApiConfig.load("main/resources/config.json");
        String body = String.format(
                "grant_type=authorization_code&code=%s&redirect_uri=%s&client_id=%s&code_verifier=%s",
                URLEncoder.encode(code, StandardCharsets.UTF_8),
                URLEncoder.encode(conf.redirect_uri, StandardCharsets.UTF_8),
                conf.client_id,
                conf.code_challenge);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://myanimelist.net/v1/oauth2/token"))
                .header("Content-Type", "application/x-fom-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        TokenInfo token = gson.fromJson(response.body(), TokenInfo.class);
        View.mostrarMsg(token.toString());
        return token;
    }

    public static TokenInfo refrescarToken(TokenInfo token) throws IOException, InterruptedException {
        ApiConfig conf = ApiConfig.load("main/resources/config.json");
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://myanimelist.net/v1/oauth2/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=refresh_token" +
                        "&refresh_token=" + URLEncoder.encode(token.getRefresh_token(), StandardCharsets.UTF_8) +
                        "&client_id=" + URLEncoder.encode(conf.client_id, StandardCharsets.UTF_8)
                ))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        TokenInfo newToken = new Gson().fromJson(response.body(), TokenInfo.class);
        return newToken;
    }

    public static TokenInfo cargarToken() {
        try (FileReader reader = new FileReader("main/resources/token.json")) {
            JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
            String accessToken = json.get("access_token").getAsString();
            String refreshToken = json.get("refresh_token").getAsString();
            long expiresIn = json.get("expires_in").getAsLong();
            long obtained_at = json.get("obtained_at").getAsLong();
            return new TokenInfo(accessToken, refreshToken, expiresIn, obtained_at);
        }
        catch (IOException e) {
            View.mostrarMsg("No se pudo leer el token.json: " + e.getMessage());
            return null;
        }
    }

    public static void guardarToken(TokenInfo token) throws IOException {
        try (FileWriter writer = new FileWriter("main/resources/token.json")) {
            new Gson().toJson(token, writer);
        }
    }

    public static void actualizarTokenFile(TokenInfo token) {
        try {
            File a = new File("main/resources/config.json");
            a.delete();
            OAuthService.guardarToken(OAuthService.refrescarToken(token));
        } catch (IOException | InterruptedException e) {
            View.mostrarMsg("No se pudo guardar el token.json: " + e.getMessage());
        }
    }

    public static boolean comprovarToken (TokenInfo token) {
        boolean seguir = true;
        Scanner scan = new Scanner(System.in);
        if (token == null) {
            View.mostrarMsg("Token no existente!");
            View.mostrarMsg("Porfavor, siga las instrucciones para poder continuar con el funcionamiento del programa:");
            View.mostrarMsg("Comprueve que el archivo config.json en /resources/ esta configurado correctamente!");
            String input;
            View.mostrarMsg("Continuar?(Y/n)");
            input = scan.nextLine();
            if (input.equalsIgnoreCase("N")) {
                seguir = false;
                return seguir;
            }
            View.mostrarMsg("Perfecto, por favor, copie esta URL en su navegador y autorice su apliacion:");
            try {
                View.mostrarMsg(OAuthService.crearURL());
            }
            catch (IOException e) {
                View.mostrarMsg("Hubo un problema al cargar el fichero config,json, por favor, comprueve que este todo correcto ERROR: " + e.getMessage());
                seguir = false;
                return seguir;
            }
            View.mostrarMsg("Intercambio del codigo del callback por el token inciado");
            try{
                OAuthService.guardarToken(OAuthService.intercambiarCodePorToken(OAuthService.esperarCode()));
                return seguir;
            }
            catch (IOException | InterruptedException e) {
                View.mostrarMsg(e.getMessage());
                seguir = false;
                return seguir;
            }
        }
        return seguir;
    }
}