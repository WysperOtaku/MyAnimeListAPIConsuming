package controller;

import api.MyAnimeListClient;
import com.google.gson.Gson;
import exceptions.ExistingObject;
import exceptions.InvalidEntry;
import model.classes.Anime;
import model.classes.TokenInfo;
import model.connection.MySQLConnection;
import model.dao.mysql.MySQLAnimeDAO;
import model.response.AnimeSearchResponse;
import model.response.AnimeWrapper;
import service.OAuthService;
import view.View;

import java.io.File;
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

        while (seguir){
            View.mostrartitulo("ANIME INFO");
            View.mostrarMenu("Actualizar informacion desde la API.", "Actualizar informacion desde JSON.", "Consultar informacion de la base de datos.", "Salir del programa.");
            try {
                int opcion = Integer.parseInt(scan.nextLine());
                Connection connection = MySQLConnection.mysqlConnection();
                if (opcion < 1 || opcion > 4) throw new InvalidEntry("La opcion introducida no existe.");
                switch (opcion){
                    case 1:
                        View.mostrarMenu("Copia completa", "Copia parcial");
                        int copia = Integer.parseInt(scan.nextLine());
                        if (copia < 1 || copia > 3) throw new InvalidEntry("La opcion introducida no existe.");
                        MyAnimeListClient client = new MyAnimeListClient();
                        insertsupdatesAPI(connection,copia, client.getTopAnimesFromEndopoint());
                        break;
                    case 2:
                        View.mostrarMenu("Copia completa", "Copia parcial");
                        int copia2 = Integer.parseInt(scan.nextLine());
                        if (copia2 < 1 || copia2 > 3) throw new InvalidEntry("La opcion introducida no existe.");
                        MyAnimeListClient client2 = new MyAnimeListClient();
                        client2.getTopAnimesToJson();
                        FileReader reader = new FileReader("src/main/resources/animes.json");
                        Gson gson = new Gson();
                        AnimeSearchResponse animeResponse = gson.fromJson(reader, AnimeSearchResponse.class);
                        List<Anime> animes = new ArrayList<>();
                        for (AnimeWrapper anime : animeResponse.getData()) {
                            animes.add(anime.node);

                        }
                        insertsupdatesAPI(connection, copia2, animes);
                        break;
                    case 3:
                        consultesMenu(connection);
                        break;
                    case 4:
                        View.mostrarMsg("Finalizando el programa...");
                        seguir = false;
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

    private static void insertsupdatesAPI(Connection con, int copia, List<Anime> animes) throws IOException, InterruptedException, SQLException{
        MySQLAnimeDAO animeDAO = new MySQLAnimeDAO(con);
        while (true){
            switch (copia){
                case 1:
                    for (Anime a: animes){
                        try {
                            animeDAO.create(a,true);
                        }
                        catch (ExistingObject e){
                            View.mostrarMsg(e.getMessage());
                        }
                    }
                    return;
                case 2:
                    for (Anime a: animes){
                        try {
                            animeDAO.create(a,false);
                        }
                        catch (ExistingObject e){
                            View.mostrarMsg(e.getMessage());
                        }
                    }
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