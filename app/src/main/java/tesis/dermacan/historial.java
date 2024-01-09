package tesis.dermacan;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class historial extends AppCompatActivity {

    ArrayList<view_historial> HistorialArrayList;

    RecyclerView HrecyclerView;


    SharedPreferences sesion;
    String cedula_usu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        // Obtener extras del Intent
        Intent intent = getIntent();
        if (intent != null) {
            String prediccion = intent.getStringExtra("prediccion");
            String probabilidad = intent.getStringExtra("probabilidad");
            String id_fcder = intent.getStringExtra("id_fcder");
            String id_pet = intent.getStringExtra("id_pet");

            // Aquí puedes utilizar los valores obtenidos según tus necesidades
            if (prediccion != null && probabilidad != null && id_fcder != null && id_pet != null) {
                // Por ejemplo, puedes mostrar un Toast con los valores obtenidos
                Toast.makeText(this, "Predicción: " + prediccion, Toast.LENGTH_LONG).show();


            }
        }

        HrecyclerView = (RecyclerView)findViewById(R.id.H_recyclerView);
        HrecyclerView.setHasFixedSize(true);
        HrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        HistorialArrayList = new ArrayList<>();
        loadhistorial();

    }



    private void loadhistorial() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        // Obtiene la cédula del SessionManager
        String cedula_usu = sessionManager.getCedulaUsu();

        String URL = Globales.pagWeb + "/obtener_historial.php?cedula="+cedula_usu;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject historial = array.getJSONObject(i);

                        //nombres en la tabla
                        HistorialArrayList.add(new view_historial(
                                historial.getString("nombre_pet"),
                                historial.getString("fecha_fcder"),
                                historial.getString("diagnostico"),
                                historial.getString("ruta_imagen"),

                                historial.getString("id_pet"),
                                historial.getString("id_fcder"),
                                historial.getString("id_diag")
                        ));
                    }
                    //Toast.makeText(getApplicationContext(), array.toString(), Toast.LENGTH_LONG).show();
                    HistorialAdapter historialAdapter = new HistorialAdapter(historial.this, HistorialArrayList);
                    HrecyclerView.setAdapter(historialAdapter);
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

    public void menu(View v){
        Intent i = new Intent(this, menu.class);
        startActivity(i);
    }
}