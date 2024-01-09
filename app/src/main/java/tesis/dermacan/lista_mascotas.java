package tesis.dermacan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class lista_mascotas extends AppCompatActivity {

    ArrayList<view_lista_mascotas> listaMascotasArrayList;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mascotas);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaMascotasArrayList = new ArrayList<>();
        loadmascotas();
    }

    private void loadmascotas() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        // Obtiene la cédula del SessionManager
        String cedula_usu = sessionManager.getCedulaUsu();

        String URL = Globales.pagWeb + "/obtener_mascotas.php?cedula="+cedula_usu;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject mascota = array.getJSONObject(i);

                        // Asegúrate de extraer también la URL de la imagen de la respuesta JSON

                        //nombres en la tabla
                        listaMascotasArrayList.add(new view_lista_mascotas(
                                mascota.getString("nombre_pet"),
                                mascota.getString("fnaci_pet"),
                                mascota.getString("raz_pet"),
                                mascota.getString("ruta_imagen")
                        ));
                    }
                    ListaMascotasAdapter listaMascotasAdapter = new ListaMascotasAdapter(lista_mascotas.this, listaMascotasArrayList);
                    recyclerView.setAdapter(listaMascotasAdapter);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }




    public void crear_mascota(View v){
        Intent i = new Intent(this, mascotas.class);
        startActivity(i);
    }

    public void menu(View v){
        Intent i = new Intent(this, menu.class);
        startActivity(i);
    }
}