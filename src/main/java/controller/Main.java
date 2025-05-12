package controller;

import exceptions.InvalidEntry;
import view.View;

import java.util.InputMismatchException;
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
                if (opcion < 1 || opcion > 4) throw new InvalidEntry("La opcion introducida no existe.");
                switch (opcion){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        View.mostrarMsg("Finalizando el programa...");
                        seguir = false;
                        break;
                }
            }
            catch (InvalidEntry e){
                View.mostrarMsg(e.getMessage());
            }
        }
    }
}