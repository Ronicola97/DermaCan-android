package tesis.dermacan;

import static tesis.dermacan.user.ValidarCorreo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {

    TextInputEditText email, contrasenia;
    TextInputLayout TextInputCorreo, TextInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        contrasenia = findViewById(R.id.contrasenia);

        TextInputCorreo = findViewById(R.id.TextInputCorreo);
        TextInputPassword = findViewById(R.id.TextInputPassword);

    }

    private boolean ValidarCampos() {
        boolean retorno = true;
        String correo, password;
        correo = email.getText().toString();
        password = contrasenia.getText().toString();

        if (correo.isEmpty()){
            TextInputCorreo.setError("Ingrese su correo electrónico");
            retorno = false;
        } else if (!ValidarCorreo(correo)){
            TextInputCorreo.setError("Ingrese un correo electrónico valido");
            retorno = false;
        }else {
            TextInputCorreo.setErrorEnabled(false);
        }

        if (password.isEmpty()){
            TextInputPassword.setError("Ingrese su contraseña");
            retorno = false;
        } else if (password.length() < 8 || password.length() > 16){
            TextInputPassword.setError("Minimo 8 y máximo 16 caracteres");
            retorno = false;
        }else if (!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^*()<>?/]).{8,}$")){
            TextInputPassword.setError("La contraseña debe tener al menos 1 número, 1 letra mayúscula y 1 carácter especial");
            retorno = false;
        }else {
            TextInputPassword.setErrorEnabled(false);
        }

        return retorno;
    }

    public void Validar_Login(View v){
        String correo = email.getText().toString();
        String pass = contrasenia.getText().toString();

        try{
            if (ValidarCampos()){
                String URL = Globales.pagWeb + "/validar_usuario.php?email="+correo+"&contrasenia="+pass;
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JsonArrayRequest respuestajson = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        if(response.length() > 0){
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    jsonObject = response.getJSONObject(0);
                                    String cedula_cliente = jsonObject.getString("cedula_usu");
                                    String nombre_cliente = jsonObject.getString("nombre_usu");

                                    // Inicializa SessionManager y establece datos de sesión
                                    SessionManager sessionManager = new SessionManager(getApplicationContext());
                                    sessionManager.setLogin(true);
                                    sessionManager.setNombre(nombre_cliente);
                                    sessionManager.setCedulaUsu(cedula_cliente);

                                    Intent intent = new Intent(getApplicationContext(), menu.class);
                                    startActivity(intent);

                                }catch (JSONException e){
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "El correo o la contraseña son incorrectos", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Hubo un problema de servidor, inténtelo más tarde", Toast.LENGTH_LONG).show();
                    }
                });
                requestQueue.add(respuestajson);
            }else{
                Toast.makeText(login.this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(login.this, "Se produjo un error al registrar sus datos.", Toast.LENGTH_SHORT).show();
        }

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TextInputCorreo.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        contrasenia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TextInputPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void MenuGlobal (View v){
        try{
            if(ValidarCampos()){
                String correo = email.getText().toString();
                String password = contrasenia.getText().toString();

                if (correo.equals(Globales.correo) && password.equals(Globales.password)) {
                    String nombre = Globales.nombre + " " + Globales.apellido;
                    SharedPreferences sharedPreferences = getSharedPreferences("mis_preferencias", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("nombre", nombre);
                    editor.commit();
                    Toast.makeText(login.this, "Validación exitosa", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, menu.class);
                    i.putExtra("nombre", nombre);
                    startActivity(i);

                } else if (correo.equals(Globales.user) && password.equals(Globales.pass)){
                    String nombre = Globales.firstname + " " + Globales.lastname;
                    SharedPreferences sharedPreferences = getSharedPreferences("mis_preferencias", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("nombre", nombre);
                    editor.commit();
                    Toast.makeText(login.this, "Validación exitosa", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, menu.class);
                    i.putExtra("nombre", nombre);
                    startActivity(i);

                } else {
                    Toast.makeText(login.this, "El correo o la contraseña están incorrectos", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(login.this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(login.this, "Se produjo un error al registrar sus datos.", Toast.LENGTH_SHORT).show();
        }
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TextInputCorreo.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        contrasenia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TextInputPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void user(View v){
        Intent i = new Intent(this, user.class);
        startActivity(i);
    }

    public void menu(View v){
        Intent i = new Intent(this, menu.class);
        startActivity(i);
    }

    public void Instagram(View v){
        String url = "https://www.instagram.com/vet_garfield/";
        Uri direccion = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, direccion);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
}