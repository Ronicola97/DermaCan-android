package tesis.dermacan;

public class Globales {
    public static String pagWeb = "https://dermacan.up.railway.app/android";

    public static String cedula, nombre, apellido, celular, correo, password;

    public static String user = "usuario@gmail.com";
    public static String pass = "Usuario123@";
    public static String firstname = "Usuario";
    public static String lastname = "Prueba";
    public static void DatosGlobales(String cedula, String nombre, String apellido, String celular, String correo, String password) {
        Globales.cedula = cedula;
        Globales.nombre = nombre;
        Globales.apellido = apellido;
        Globales.celular = celular;
        Globales.correo = correo;
        Globales.password = password;
    }

}
