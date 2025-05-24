package controller;

import api.MyAnimeListClient;
import com.google.gson.Gson;
import exceptions.InvalidEntry;
import model.classes.*;
import model.connection.MySQLConnection;
import model.dao.mysql.MySQLAnimeDAO;
import model.response.AnimeSearchResponse;
import model.response.AnimeWrapper;
import service.OAuthService;
import view.View;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args){
        TokenInfo token = OAuthService.cargarToken();
        boolean seguir = true;

        if (!OAuthService.comprovarToken(token)) {
            seguir = false;
        } else {
            token = OAuthService.cargarToken();
        }

        if (seguir && token.isExpired()) {
            OAuthService.actualizarTokenFile(token);
        }

        MyAnimeListClient client = new MyAnimeListClient();

        while (seguir){
            View.mostrartitulo("ANIME INFO");
            View.mostrarMenu("Actualizar informacion desde la API.", "Actualizar informacion desde JSON.", "Modificar informacion desde la API", "Modificar informacion desde JSON", "Consultar informacion de la base de datos.", "Salir del programa.");
            try {
                int opcion = Integer.parseInt(scan.nextLine());
                Connection connection = MySQLConnection.mysqlConnection();
                if (opcion < 1 || opcion > 6) throw new InvalidEntry("La opcion introducida no existe.");
                switch (opcion){
                    case 1:
                        insertsupdatesAPI(connection,tipoCopia(), client.getTopAnimesFromEndopoint());
                        break;
                    case 2:
                        client.getTopAnimesToJson();
                        FileReader reader = new FileReader("src/main/resources/animes.json");
                        Gson gson = new Gson();
                        AnimeSearchResponse animeResponse = gson.fromJson(reader, AnimeSearchResponse.class);
                        List<Anime> animes = new ArrayList<>();
                        for (AnimeWrapper anime : animeResponse.getData()) {
                            animes.add(anime.node);
                        }
                        insertsupdatesAPI(connection, tipoCopia(), animes);
                        reader.close();
                        break;
                    case 3:
                        View.mostrarMsg("Introduce la ID del anime que quieres actualizar.");
                        int id = Integer.parseInt(scan.nextLine());
                        Anime a = client.getAnimeFromEndpoint(id);
                        View.mostrarMsg(a.toStringConsume());

                        MySQLAnimeDAO animeDAO = new MySQLAnimeDAO(connection);
                        int opt = obtenerMalId();
                        if (opt == 1) animeDAO.create(a,true);
                        else if (opt == 3) View.mostrarMsg("Volviendo al menu principal...");
                        break;
                    case 4:
                        View.mostrarMsg("Introduce la ID del anime que quieres actualizar.");
                        int id2 = Integer.parseInt(scan.nextLine());
                        client.getAnimeToJson(id2);
                        FileReader reader2 = new FileReader("src/main/resources/anime.json");
                        Gson gson2 = new Gson();
                        Anime animeResponse2 = gson2.fromJson(reader2, Anime.class);
                        View.mostrarMsg(animeResponse2.toStringConsume());

                        MySQLAnimeDAO animeDAO2 = new MySQLAnimeDAO(connection);
                        int opt2 = obtenerMalId();
                        if (opt2 == 1) animeDAO2.create(animeResponse2,true);
                        else if (opt2 == 3) View.mostrarMsg("Volviendo al menu principal...");
                        reader2.close();
                        break;
                    case 5:
                        consultesMenu(connection);
                        break;
                    case 6:
                        View.mostrarMsg("Finalizando el programa...");
                        seguir = false;
                        client.closeClient();
                        connection.close();
                        System.exit(0);
                        break;
                }
            }
            catch (SQLException e){
                View.mostrarMsg("No ha sido posible establecer la connexion con la base de datos.");
                View.mostrarMsg("Finalizando el programa...");
                seguir = false;
            }
            catch (InvalidEntry | IOException e){
                View.mostrarMsg(e.getMessage());
            }
            catch (InterruptedException e){
                View.mostrarMsg("Ha habido un error en la peticion HTTP.");
            }
            catch (NumberFormatException e){
                View.mostrarMsg("Opcion no valida. Introduce un numero entre las opciones.");
            }
        }
    }

    private static int obtenerMalId() throws InvalidEntry {
        View.mostrarMsg("Quieres actualizar la informacion de este anime a nuestra BBDD?");
        View.mostrarMenu("Si","No","Volver al menu principal");
        int opt = Integer.parseInt(scan.nextLine());
        if (opt < 1 || opt > 3) throw new InvalidEntry("La opcion introducida no existe.");
        return opt;
    }

    private static int tipoCopia() throws InvalidEntry{
        View.mostrarMsg("Introduce el tipo de copia que quieres hacer.");
        View.mostrarMenu("Copia completa", "Copia parcial","Volver al menu principal");
        int copia = Integer.parseInt(scan.nextLine());
        if (copia < 1 || copia > 3) throw new InvalidEntry("La opcion introducida no existe.");
        return copia;
    }

    private static void insertsupdatesAPI(Connection con, int copia, List<Anime> animes) throws IOException, InterruptedException, SQLException{
        View.mostrarMsg("Actualizando la informacion de la Base de datos...");
        MySQLAnimeDAO animeDAO = new MySQLAnimeDAO(con);

        while (true){
            switch (copia){
                case 1:
                    for (Anime a: animes) animeDAO.create(a,true);
                    return;
                case 2:
                    for (Anime a: animes) animeDAO.create(a,false);
                    return;
                case 3:
                    View.mostrarMsg("Volviendo al menu principal...");
                    return;
            }
        }
    }

    private static void consultesMenu(Connection con){
        boolean seguir = true;

        try {
            while (seguir){
                View.mostrartitulo("Consultas");
                View.mostrarMenu("Mostrar todos los animes.", "Mostrar un anime por su id.","Volver al menu.");
                int opt = Integer.parseInt(scan.nextLine());
                if (opt < 1 || opt > 3) throw new InvalidEntry("La opcion introducida no existe.");

                switch (opt){
                    case 1:
                        MySQLAnimeDAO DAOanime = new MySQLAnimeDAO(con);
                        View.mostrarAnimes(DAOanime.readAll());
                        break;
                    case 2:
                        View.mostrarMsg("Introduce la ID del anime que quires consultar.");
                        int id = Integer.parseInt(scan.nextLine());
                        MySQLAnimeDAO animeDAO = new MySQLAnimeDAO(con);
                        View.mostrarMsg(animeDAO.read(id).toString());
                        break;
                    case 3:
                        View.mostrarMsg("Volviendo al menu principal...");
                        seguir = false;
                        break;
                }
            }
        } catch (InvalidEntry | SQLException e) {
            View.mostrarMsg(e.getMessage());
        }
    }
}