package view;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class View {
    /**
     * Muestra un mensaje
     * @param msg Mensaje a mostrar
     */
    public static void mostrarMsg(String msg){
        System.out.println(msg);
    }

    /**
     * Muestra un menu de opciones con su respectiva opcion
     * @param opciones Titulos de cada opcion
     */
    public static void mostrarMenu(String ... opciones){
        System.out.println("Introduce una de las siguientes opciones:");
        for (int i = 1; i <= opciones.length; i++){
            System.out.println(i + ": " + opciones[i - 1]);
        }
    }

    /**
     * Muestra un titulo con un formato mas decorado
     * @param titulo Titulo a mostrar
     */
    public static void mostrartitulo(String titulo){
        String r = "";
        for (int i = 0; i < titulo.length(); i++){
            r += "-";
        }
        System.out.println(r + "\n" + titulo.toUpperCase() + "\n" + r);
    }

    /**
     * Agafa el ResultSet de readAll de animes per mostrarlo
     * @param rs ResultSet
     * @throws SQLException Excepcio llençada en cas de error amb el ResultSet
     */
    public static void mostrarAnimes(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();

        System.out.println(String.format("%-50s %-75s %-10s %-10s %-15s %-10s %-10s %-15s %-100s",
                md.getColumnLabel(1), md.getColumnLabel(2), md.getColumnLabel(3),
                md.getColumnLabel(4), md.getColumnLabel(5), md.getColumnLabel(6),
                md.getColumnLabel(7), md.getColumnLabel(8), md.getColumnLabel(9)));

        while (rs.next()){
            boolean air;
            if (Integer.parseInt(rs.getString(7)) == 0) air = false;
            else air = true;
            System.out.println(String.format("%-50s %-75s %-10s %-10s %-15s %-10s %-10s %-15s %-100s",
                    rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6),
                    air, rs.getString(8), rs.getString(9)));
        }
    }
}