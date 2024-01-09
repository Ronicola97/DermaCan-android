package tesis.dermacan;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "LoginPref";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_CEDULA_USU = "cedula_usu";

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setNombre(String nombre) {
        editor.putString(KEY_NOMBRE, nombre);
        editor.apply();
    }

    public String getNombre() {
        return pref.getString(KEY_NOMBRE, "");
    }

    public void setCedulaUsu(String cedulaUsu) {
        editor.putString(KEY_CEDULA_USU, cedulaUsu);
        editor.apply();
    }

    public String getCedulaUsu() {
        return pref.getString(KEY_CEDULA_USU, "");
    }

    public void cerrarSesion() {
        editor.clear();  // Elimina todos los datos almacenados en SharedPreferences
        editor.apply();
    }

    // Puedes agregar más métodos según tus necesidades, como cerrar sesión, obtener datos de sesión, etc.
}
