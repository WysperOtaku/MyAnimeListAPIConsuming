package controller;

import exceptions.InvalidEntry;
import model.connection.MySQLConnection;
import model.dao.mysql.MySQLAnimeDAO;
import view.View;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args){
        boolean seguir = true;

        while (seguir){
            View.mostrartitulo("ANIME INFO");
            View.mostrarMenu("Actualizar informacion desde la API.", "Actualizar informacion desde JSON.", "Consultar informacion de la base de datos.", "Salir del programa.");
            try {
                int opcion = Integer.parseInt(scan.nextLine());
                Connection connection = MySQLConnection.mysqlConnection();
                if (opcion < 1 || opcion > 4) throw new InvalidEntry("La opcion introducida no existe.");
                switch (opcion){
                    case 1:
                        break;
                    case 2:
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
            catch (InvalidEntry e){
                View.mostrarMsg(e.getMessage());
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