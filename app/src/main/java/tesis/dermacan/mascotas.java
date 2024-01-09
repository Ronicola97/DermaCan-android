package tesis.dermacan;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class mascotas extends AppCompatActivity {

    ImageView verfoto;
    Bitmap imagenfoto;

    List<String> raza = new ArrayList<String>();
    Spinner lista_raza;

    RequestQueue requestQueue;

    TextInputLayout TextInputNombreMascota, TextInputDireccion;
    TextInputEditText nom_mascota, direc_mascota;

    EditText edad;


    Bitmap bitmapToSend = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascotas);




        TextInputNombreMascota = findViewById(R.id.TextInputNombreMascota);
        TextInputDireccion = findViewById(R.id.TextInputDireccion);

        nom_mascota =  findViewById(R.id.nombre);
        direc_mascota = findViewById(R.id.Direccion);

        edad = findViewById(R.id.edad);
        edad.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        verfoto = (ImageView) findViewById(R.id.FotoPerro);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,},1000);
        }

        lista_raza = (Spinner) findViewById(R.id.raza);
        raza.add("Razas");
        raza.add("Beagle");
        raza.add("Boxer");
        raza.add("Bull Terrier");
        raza.add("Bulldog Ingles");
        raza.add("Caniche");
        raza.add("Chihuahua");
        raza.add("Cocker");
        raza.add("Dachshund");
        raza.add("Dálmata");
        raza.add("Doberman Pinscher");
        raza.add("French Bulldog");
        raza.add("French Poodle");
        raza.add("Golden Retriever");
        raza.add("Husky Siberiano");
        raza.add("Labrador Retriever");
        raza.add("Mestizo");
        raza.add("Pastor Alemán");
        raza.add("Pequines");
        raza.add("Pitbull");
        raza.add("Poodle");
        raza.add("Pug");
        raza.add("Rottweiler");
        raza.add("Schnauzer");
        raza.add("Shinh Tzu");

        ArrayAdapter<String> adaptadorspin = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, raza );
        adaptadorspin.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        lista_raza.setAdapter(adaptadorspin);

    }


    private boolean ValidarCampos(){
        boolean retorno = true;
        String nombre, direccion;

        nombre = nom_mascota.getText().toString();
        direccion =  direc_mascota.getText().toString();



        if (nombre.isEmpty()){
            TextInputNombreMascota.setError("Ingrese el nombre de su mascota");
            retorno = false;
        } else{
            TextInputNombreMascota.setErrorEnabled(false);
        }

        if (direccion.isEmpty()){
            TextInputDireccion.setError("Ingrese el sector donde habita");
            retorno = false;
        } else{
            TextInputDireccion.setErrorEnabled(false);
        }
        return retorno;

    }
    public void Registrar_Mascota(View v){
        String pagina = Globales.pagWeb + "/guardar_mascota.php";
        System.out.println(pagina);
        requestQueue = Volley.newRequestQueue(this);

        try{
            if(ValidarCampos() && bitmapToSend != null){
                StringRequest respuesta = new StringRequest(Request.Method.POST, pagina, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        if(response.equals("grabado")){
                            Toast.makeText(mascotas.this, "Su mascota ha sido registrada", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), lista_mascotas.class);
                            startActivity(i);
                        }else{
                            if(response.equals("existe")){
                                Toast.makeText(mascotas.this, "Nombre de mascota debe ser unico", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(mascotas.this, "Se produjo un error al guardar sus datos", Toast.LENGTH_SHORT).show();
                            }
                            //
                            //Toast.makeText(mascotas.this, response, Toast.LENGTH_SHORT).show();
                            //direc_mascota.setText(response);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mascotas.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        SessionManager sessionManager = new SessionManager(getApplicationContext());

                        // Obtiene la cédula del SessionManager
                        String cedula_usu = sessionManager.getCedulaUsu();

                        // Convertir bitmap a Base64
                        String imagenBase64 = bitmapToBase64(bitmapToSend);

                        Map<String, String> parametros = new Hashtable<String, String>();
                        parametros.put("cedula_dueno",cedula_usu);
                        parametros.put("name_mascota",nom_mascota.getText().toString() );
                        parametros.put("dir_mascota",direc_mascota.getText().toString() );
                        parametros.put("nacimiento",edad.getText().toString() );
                        parametros.put("tipo_raza",lista_raza.getSelectedItem().toString() );
                        parametros.put("imagen_mascota", imagenBase64);

                        return parametros;
                    }
                };
                requestQueue.add(respuesta);

            }else{
                Toast.makeText(mascotas.this, "Por favor complete todos los campos y tome la imagen.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(mascotas.this, "Se produjo un error al registrar sus datos.", Toast.LENGTH_SHORT).show();
        }

        nom_mascota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TextInputNombreMascota.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        direc_mascota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TextInputDireccion.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void verCalendario(View v){
        mostrarCalendario();
    }

    private String arreglar_fecha(int f){
        String real;
        if (f<=9) real = "0" + f;
        else real = "" + f;
        return real;
    }

    private void mostrarCalendario(){
        DatePickerFragment calendus = DatePickerFragment.crear(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                final String fecha = anio + "/" + arreglar_fecha(mes + 1) + "/" + arreglar_fecha(dia);
                edad.setText(fecha);
            }
        });
        calendus.show(this.getSupportFragmentManager(), "Calendario");
    }

    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal = Calendar.getInstance();
            int anio = cal.get(Calendar.YEAR);
            int mes = cal.get(Calendar.MONTH);
            int dia = cal.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), listener, anio, mes, dia);
        }

        public static DatePickerFragment crear(DatePickerDialog.OnDateSetListener listener){
            DatePickerFragment frag = new DatePickerFragment();
            frag.setListener(listener);
            return frag;
        }
    }

    public void tomarFoto(View v){
        Intent foto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(foto.resolveActivity(getPackageManager())!=null){
            startActivityForResult(foto, 1);
        }
    }



    public void seleccionarImagen(View v){
        Intent archivos = new Intent();
        archivos.setType("image/*");
        archivos.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(archivos, "Seleccione imagen"), 2);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            verfoto.setImageBitmap(imgBitmap);
            bitmapToSend = imgBitmap;
        }
        if (requestCode == 2 && resultCode == RESULT_OK){
            Uri path = data.getData();
            try{
                imagenfoto = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                verfoto.setImageBitmap(imagenfoto);
                bitmapToSend = imagenfoto;

            }catch (IOException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }



    public void mis_mascotas(View v){
        Intent i = new Intent(this, lista_mascotas.class);
        startActivity(i);
    }
}