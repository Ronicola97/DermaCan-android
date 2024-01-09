package tesis.dermacan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class user extends AppCompatActivity {

    RequestQueue requestQueue;
    TextInputEditText dni_usu, nom_usu, apel_usu, cell_usu, email_usu, pass_usu, confpass_usu;
    TextInputLayout textInputCedula, textInputNombre, textInputApellido, textInputCelular,textInputCorreo, textInputPassword, textInputConfirmPassword;

    TextView testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        dni_usu = findViewById(R.id.cedula);
        nom_usu = findViewById(R.id.nombre);
        apel_usu = findViewById(R.id.apellido);
        cell_usu = findViewById(R.id.cell);
        email_usu = findViewById(R.id.email);
        pass_usu = findViewById(R.id.pass);
        confpass_usu = findViewById(R.id.confpass);

        testText = findViewById(R.id.test_textview);


        textInputCedula = findViewById(R.id.textInputCedula);
        textInputNombre = findViewById(R.id.textInputNombre);
        textInputApellido = findViewById(R.id.textInputApellido);
        textInputCelular = findViewById(R.id.textInputCelular);
        textInputCorreo = findViewById(R.id.textInputCorreo);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirm_password);


    }


    private boolean ValidarCampos(){
        boolean retorno = true;
        String cedula, nombre, apellido, celular, correo, password, confirm_password;

        cedula = dni_usu.getText().toString();
        nombre = nom_usu.getText().toString();
        apellido =  apel_usu.getText().toString();
        celular = cell_usu.getText().toString();
        correo = email_usu.getText().toString();
        password = pass_usu.getText().toString();
        confirm_password = confpass_usu.getText().toString();

        if (celular.isEmpty()){
            textInputCedula.setError("Ingrese su número de cédula");
            retorno = false;
        } else if(cedula.length() != 10 || cedula.charAt(0) != '0'){
            textInputCedula.setError("Ingrese un número de cédula válido");
            retorno = false;
        }else{
            textInputCedula.setErrorEnabled(false);
        }

        if (nombre.isEmpty()){
            textInputNombre.setError("Ingrese sus nombres");
            retorno = false;
        } else{
            textInputNombre.setErrorEnabled(false);
        }

        if (apellido.isEmpty()){
            textInputApellido.setError("Ingrese sus apellidos");
            retorno = false;
        } else{
            textInputApellido.setErrorEnabled(false);
        }

        if (celular.isEmpty()){
            textInputCelular.setError("Ingrese su número de celular");
            retorno = false;
        } else if(celular.length() != 10 || celular.charAt(0) != '0'){
            textInputCelular.setError("Ingrese un número de celular válido");
            retorno = false;
        }else{
            textInputCelular.setErrorEnabled(false);
        }

        if (correo.isEmpty()){
            textInputCorreo.setError("Ingrese su correo electrónico");
            retorno = false;
        } else if (!ValidarCorreo(correo)){
            textInputCorreo.setError("Ingrese un correo electrónico valido");
            retorno = false;
        }else {
            textInputCorreo.setErrorEnabled(false);
        }

        if (password.isEmpty()){
            textInputPassword.setError("Ingrese su contraseña");
            retorno = false;
        } else if (password.length() < 8 || password.length() > 16){
            textInputPassword.setError("Minimo 8 y máximo 16 caracteres");
            retorno = false;
        }else if (!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%-_^*()<>?/]).{8,}$")){
            textInputPassword.setError("La contraseña debe tener al menos 1 número, 1 letra mayúscula y 1 carácter especial");
            retorno = false;
        }else {
            textInputPassword.setErrorEnabled(false);
        }

        if (confirm_password.isEmpty()){
            textInputConfirmPassword.setError("Repita su contraseña");
            retorno = false;
        } else if (!confirm_password.equals(password)) {
            textInputConfirmPassword.setError("Las contraseñas no son iguales");
            retorno = false;
        }else{
            textInputConfirmPassword.setErrorEnabled(false);
        }
        return retorno;

    }

    public void RegistroUsuario(View v){

        try{
            if(ValidarCampos()){
                String pagina = Globales.pagWeb + "/guardar_usuario.php";
                System.out.println(pagina);
                requestQueue = Volley.newRequestQueue(this);
                StringRequest respuesta = new StringRequest(Request.Method.POST, pagina, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        if(response.equals("grabado")){
                            Toast.makeText(user.this, "Registro completado con éxito", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(user.this, "Se produjo un error al registrar sus datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(user.this, "Error de conexión con el servidor, intente más tarde", Toast.LENGTH_SHORT).show();
                        //nom_usu.setText(error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new Hashtable<String, String>();

                        parametros.put("cedula", dni_usu.getText().toString());
                        parametros.put("nombre", nom_usu.getText().toString());
                        parametros.put("apellidos", apel_usu.getText().toString());
                        parametros.put("email", email_usu.getText().toString());
                        parametros.put("contrasenia", pass_usu.getText().toString());
                        parametros.put("celular", cell_usu.getText().toString());

                        return parametros;
                    }
                };
                requestQueue.add(respuesta);

            }else {
                Toast.makeText(user.this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            Toast.makeText(user.this, "Se produjo un error al registrar sus datos.", Toast.LENGTH_SHORT).show();
        }

        dni_usu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputCedula.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        nom_usu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputNombre.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        apel_usu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputApellido.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cell_usu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputCelular.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        email_usu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputCorreo.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pass_usu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        confpass_usu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputConfirmPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    public static boolean ValidarCorreo(String email) {
        String expresiones = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expresiones, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void UsuarioGlobal(View v){
        try{
            if(ValidarCampos()){
                String correo = email_usu.getText().toString();
                String password = pass_usu.getText().toString();
                String cedula = dni_usu.getText().toString();
                String nombre = nom_usu.getText().toString();
                String apellido = apel_usu.getText().toString();
                String celular = cell_usu.getText().toString();

                Globales.DatosGlobales(cedula, nombre, apellido, celular, correo, password);

                Intent i = new Intent(this, login.class);
                startActivity(i);

            }else {
                Toast.makeText(user.this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(user.this, "Se produjo un error al registrar sus datos.", Toast.LENGTH_SHORT).show();
        }
        dni_usu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputCedula.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        nom_usu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputNombre.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        apel_usu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputApellido.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cell_usu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputCelular.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        email_usu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputCorreo.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pass_usu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        confpass_usu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputConfirmPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void login(View v){
        Intent i = new Intent(this, login.class);
        startActivity(i);
    }
}