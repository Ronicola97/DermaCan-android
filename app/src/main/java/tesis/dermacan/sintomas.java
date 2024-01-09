package tesis.dermacan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class sintomas extends AppCompatActivity {

    RequestQueue requestQueue;
    SharedPreferences sesion;
    String cedula_usu;
    List<String> mascotas = new ArrayList<String>();
    Spinner lista_mascotas;

    String id_mascota, nombre_mascota;
    List<String> nombresMascotas = new ArrayList<>();
    List<String> ids_Mascotas = new ArrayList<>();
    TextView testSpin;
    String MascotaSeleccionada;
    String idMascota;

    LinearLayout ly_alopecia;
    String bd_enrojecido, bd_olor, bd_pica_leve, bd_pica_moderada, bd_pica_intensa,
            bd_alo_cabeza, bd_alo_orejas, bd_alo_cuello, bd_alo_lomo, bd_alo_extremidades, bd_alo_abdomen,
            bd_cost_peque, bd_cost_media, bd_cost_grande,
            bd_gruesa_leve, bd_gruesa_pronunciada, bd_gruesa_grave,
            bd_pustula_peque, bd_pustula_grande,
            bd_eritema, bd_sacudir, bd_cerumen;


    private View rg_picazon, rg_costras, rg_grueso, rg_pustulas, rg_alopecia,
            ch_picazon, rd_picazon,rd_picazon2,rd_picazon3,
            ch_costras, rd_costras,rd_costras2,rd_costras3,
            ch_grueso, rd_grueso,rd_grueso2,rd_grueso3,
            ch_pustula, rd_pustula,rd_pustula2,

            ch_alopecia, rd_alo_cabeza, rd_alo_oreja, rd_alo_cuello, rd_alo_lomo, rd_alo_extremidades, rd_alo_abdomen,
            ch_cerumen, ch_enrojecido, ch_sacudir, ch_eritema, ch_olor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas);

        lista_mascotas = (Spinner) findViewById(R.id.mis_mascotas);

        ch_cerumen = (CheckBox)findViewById(R.id.cerumen);
        ch_enrojecido = (CheckBox)findViewById(R.id.enrojecido);
        ch_olor = (CheckBox)findViewById(R.id.olor);

        ch_alopecia = (CheckBox)findViewById(R.id.alopecia);
        rd_alo_cabeza = (CheckBox)findViewById(R.id.alo_cabeza);
        rd_alo_oreja = (CheckBox)findViewById(R.id.alo_oreja);
        rd_alo_cuello = (CheckBox)findViewById(R.id.alo_cuello);
        rd_alo_lomo = (CheckBox)findViewById(R.id.alo_lomo);
        rd_alo_extremidades = (CheckBox)findViewById(R.id.alo_extremidades);
        rd_alo_abdomen = (CheckBox)findViewById(R.id.alo_abdomen);

        ch_picazon = (CheckBox)findViewById(R.id.ch_picazon);
        rd_picazon = (RadioButton)findViewById(R.id.rd_int1_cab);
        rd_picazon2 = (RadioButton)findViewById(R.id.rd_int2_cab);
        rd_picazon3 = (RadioButton)findViewById(R.id.rd_int3_cab);

        ch_costras = (CheckBox)findViewById(R.id.ch_costras);
        rd_costras = (RadioButton)findViewById(R.id.rd_tam1_cab);
        rd_costras2 = (RadioButton)findViewById(R.id.rd_tam2_cab);
        rd_costras3 = (RadioButton)findViewById(R.id.rd_tam3_cab);

        ch_grueso = (CheckBox)findViewById(R.id.ch_grueso);
        rd_grueso = (RadioButton)findViewById(R.id.rd_grad1_cab);
        rd_grueso2 = (RadioButton)findViewById(R.id.rd_grad2_cab);
        rd_grueso3 = (RadioButton)findViewById(R.id.rd_grad3_cab);

        ch_pustula = (CheckBox)findViewById(R.id.ch_pustulas);
        rd_pustula = (RadioButton)findViewById(R.id.rd_dim1_cab);
        rd_pustula2 = (RadioButton)findViewById(R.id.rd_dim2_cab);

        ch_eritema = (CheckBox)findViewById(R.id.eritema);
        ch_sacudir = (CheckBox)findViewById(R.id.sacudir);



        rg_picazon = (RadioGroup)findViewById(R.id.grupo_picazon);
        rg_costras = (RadioGroup)findViewById(R.id.grupo_costras);
        rg_grueso = (RadioGroup)findViewById(R.id.grupo_grueso);
        rg_pustulas = (RadioGroup)findViewById(R.id.grupo_pustulas);
        ly_alopecia = (LinearLayout)findViewById(R.id.grupo_alopecia);

        cargarspinmascotas();

        testSpin = (TextView)findViewById(R.id.testing_spin);
    }

    public void validar_picazón(View view) {
        if (((CompoundButton) ch_picazon).isChecked()){
            ((RadioGroup) rg_picazon).setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );
            rd_picazon.setEnabled(true);
            rd_picazon2.setEnabled(true);
            rd_picazon3.setEnabled(true);
            ((RadioButton) rd_picazon).setChecked(true);

        } else {
            ((RadioGroup) rg_picazon).setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0
                    )
            );
            ((RadioButton) rd_picazon).setChecked(false);
            ((RadioButton) rd_picazon2).setChecked(false);
            ((RadioButton) rd_picazon3).setChecked(false);
            rd_picazon.setEnabled(false);
            rd_picazon2.setEnabled(false);
            rd_picazon3.setEnabled(false);
        }
    }
    public void validar_alopecia(View view) {
        if (((CompoundButton) ch_alopecia).isChecked()){
            ((LinearLayout) ly_alopecia).setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );
            rd_alo_abdomen.setEnabled(true);
            rd_alo_cabeza.setEnabled(true);
            rd_alo_cuello.setEnabled(true);
            rd_alo_oreja.setEnabled(true);
            rd_alo_extremidades.setEnabled(true);
            rd_alo_lomo.setEnabled(true);

        } else {
            ((LinearLayout) ly_alopecia).setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0
                    )
            );
            ((CompoundButton) rd_alo_cabeza).setChecked(false);
            ((CompoundButton) rd_alo_oreja).setChecked(false);
            ((CompoundButton) rd_alo_cuello).setChecked(false);
            ((CompoundButton) rd_alo_lomo).setChecked(false);
            ((CompoundButton) rd_alo_extremidades).setChecked(false);
            ((CompoundButton) rd_alo_abdomen).setChecked(false);
            rd_alo_cabeza.setEnabled(false);
            rd_alo_oreja.setEnabled(false);
            rd_alo_cuello.setEnabled(false);
            rd_alo_lomo.setEnabled(false);
            rd_alo_extremidades.setEnabled(false);
            rd_alo_abdomen.setEnabled(false);
        }
    }
    public void validar_costras(View view) {
        if (((CompoundButton) ch_costras).isChecked()){
            ((RadioGroup) rg_costras).setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );
            rd_costras.setEnabled(true);
            rd_costras2.setEnabled(true);
            rd_costras3.setEnabled(true);
            ((RadioButton) rd_costras).setChecked(true);
        } else {
            ((RadioGroup) rg_costras).setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0
                    )
            );
            ((CompoundButton) rd_costras).setChecked(false);
            ((CompoundButton) rd_costras2).setChecked(false);
            ((CompoundButton) rd_costras3).setChecked(false);
            rd_costras.setEnabled(false);
            rd_costras2.setEnabled(false);
            rd_costras3.setEnabled(false);
        }
    }
    public void validar_grueso(View view) {
        if (((CompoundButton) ch_grueso).isChecked()){
            ((RadioGroup) rg_grueso).setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );
            rd_grueso.setEnabled(true);
            rd_grueso2.setEnabled(true);
            rd_grueso3.setEnabled(true);
            ((RadioButton) rd_grueso).setChecked(true);
        } else {
            ((RadioGroup) rg_grueso).setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0
                    )
            );
            ((CompoundButton) rd_grueso).setChecked(false);
            ((CompoundButton) rd_grueso2).setChecked(false);
            ((CompoundButton) rd_grueso3).setChecked(false);
            rd_grueso.setEnabled(false);
            rd_grueso2.setEnabled(false);
            rd_grueso3.setEnabled(false);
        }
    }
    public void validar_pustula(View view) {
        if (((CompoundButton) ch_pustula).isChecked()){
            ((RadioGroup) rg_pustulas).setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );
            rd_pustula.setEnabled(true);
            rd_pustula2.setEnabled(true);
            ((RadioButton) rd_pustula).setChecked(true);
        } else {
            ((RadioGroup) rg_pustulas).setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0
                    )
            );
            ((CompoundButton) rd_pustula).setChecked(false);
            ((CompoundButton) rd_pustula2).setChecked(false);
            rd_pustula.setEnabled(false);
            rd_pustula2.setEnabled(false);
        }
    }


    private void cargarspinmascotas() {
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
                        id_mascota = mascota.getString("id_pet");
                        nombre_mascota = mascota.getString("nombre_pet");
                        nombresMascotas.add(nombre_mascota);
                        ids_Mascotas.add(id_mascota);
                    }
                    ArrayAdapter<String> adaptadorspin = new ArrayAdapter<String>(sintomas.this, android.R.layout.simple_spinner_item, nombresMascotas);
                    adaptadorspin.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                    lista_mascotas.setAdapter(adaptadorspin);


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error al obtener mascotas", Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }





    public void Analizar(View v){
        String pagina = Globales.pagWeb + "/guardar_sintomas.php";
        System.out.println(pagina);
        requestQueue = Volley.newRequestQueue(this);

        if (((CompoundButton) ch_enrojecido).isChecked()){
            bd_enrojecido = "1";
        }
        else {
            bd_enrojecido = "0";}

        if (((CompoundButton) ch_olor).isChecked()){
            bd_olor = "1";}
        else {
            bd_olor = "0";}

        if (((CompoundButton) ch_picazon).isChecked()){
            if(((RadioButton) rd_picazon).isChecked()){
                bd_pica_leve = "1";
                bd_pica_moderada = "0";
                bd_pica_intensa = "0";
            } else if (((RadioButton) rd_picazon2).isChecked()) {
                bd_pica_leve = "0";
                bd_pica_moderada = "1";
                bd_pica_intensa = "0";
            } else {
                bd_pica_leve = "0";
                bd_pica_moderada = "0";
                bd_pica_intensa = "1";
            }
        }
        else {
            bd_pica_leve = "0";
            bd_pica_moderada = "0";
            bd_pica_intensa = "0";
        }

        if (((CompoundButton) ch_alopecia).isChecked()){
            if(((CompoundButton) rd_alo_cabeza).isChecked()){
                bd_alo_cabeza = "1";
            }
            else {
                bd_alo_cabeza = "0";
            }
            if(((CompoundButton) rd_alo_oreja).isChecked()){
                bd_alo_orejas = "1";
            }
            else {
                bd_alo_orejas = "0";
            }
            if(((CompoundButton) rd_alo_cuello).isChecked()){
                bd_alo_cuello = "1";
            }
            else {
                bd_alo_cuello = "0";
            }
            if(((CompoundButton) rd_alo_lomo).isChecked()){
                bd_alo_lomo = "1";
            }
            else {
                bd_alo_lomo = "0";
            }
            if(((CompoundButton) rd_alo_extremidades).isChecked()){
                bd_alo_extremidades = "1";
            }
            else {
                bd_alo_extremidades = "0";
            }
            if(((CompoundButton) rd_alo_abdomen).isChecked()){
                bd_alo_abdomen = "1";
            }
            else {
                bd_alo_abdomen = "0";
            }
        }
        else {
            bd_alo_cabeza = "0";
            bd_alo_orejas = "0";
            bd_alo_cuello = "0";
            bd_alo_lomo = "0";
            bd_alo_extremidades = "0";
            bd_alo_abdomen = "0";
        }

        if (((CompoundButton) ch_costras).isChecked()){
            if(((RadioButton) rd_costras).isChecked()){
                bd_cost_peque = "1";
                bd_cost_media = "0";
                bd_cost_grande = "0";
            } else if (((RadioButton) rd_costras2).isChecked()) {
                bd_cost_peque = "0";
                bd_cost_media = "1";
                bd_cost_grande = "0";
            } else {
                bd_cost_peque = "0";
                bd_cost_media = "0";
                bd_cost_grande = "1";
            }
        }
        else {
            bd_cost_peque = "0";
            bd_cost_media = "0";
            bd_cost_grande = "0";}

        if (((CompoundButton) ch_grueso).isChecked()){
            if(((RadioButton) rd_grueso).isChecked()){
                bd_gruesa_leve = "1";
                bd_gruesa_pronunciada = "0";
                bd_gruesa_grave = "0";
            } else if (((RadioButton) rd_grueso2).isChecked()) {
                bd_gruesa_leve = "0";
                bd_gruesa_pronunciada = "1";
                bd_gruesa_grave = "0";
            } else {
                bd_gruesa_leve = "0";
                bd_gruesa_pronunciada = "0";
                bd_gruesa_grave = "1";
            }
        }
        else {
            bd_gruesa_leve = "0";
            bd_gruesa_pronunciada = "0";
            bd_gruesa_grave = "0";}

        if (((CompoundButton) ch_pustula).isChecked()){
            if(((RadioButton) rd_pustula).isChecked()){
                bd_pustula_peque = "1";
                bd_pustula_grande = "0";
            } else {
                bd_pustula_peque = "0";
                bd_pustula_grande = "1";
            }
        }
        else {
            bd_pustula_peque = "0";
            bd_pustula_grande = "0";
        }

        if (((CompoundButton) ch_eritema).isChecked()){
            bd_eritema = "1";
        } else {
            bd_eritema = "0";
        }

        if (((CompoundButton) ch_sacudir).isChecked()){
            bd_sacudir = "1";
        }
        else {
            bd_sacudir = "0";
        }

        if (((CompoundButton) ch_cerumen).isChecked()){
            bd_cerumen = "1";
        }
        else {
            bd_cerumen = "0";
        }


        try{
            StringRequest respuesta = new StringRequest(Request.Method.POST, pagina, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println(response);
                    if(response.equals("error")){
                        //Toast.makeText(sintomas.this, "Empezando análisis", Toast.LENGTH_LONG).show();
                        Toast.makeText(sintomas.this, "Se produjo un error al empezar el análisis", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(sintomas.this, "Procesando...", Toast.LENGTH_LONG).show();
                        //testSpin.setText(response);
                        try{
                            // Convertir la respuesta JSON en un objeto JSONObject
                            JSONObject diagnosticojson = new JSONObject(response);

                            // Obtener los datos del objeto JSON
                            String prediccion = diagnosticojson.getString("prediccion");
                            String probabilidad = diagnosticojson.getString("probabilidad");
                            String id_fcder = diagnosticojson.getString("id_fcder");
                            String id_pet = diagnosticojson.getString("id_pet");

                            // Crear el Intent y añadir los extras
                            Intent intent = new Intent(getApplicationContext(), historial.class);
                            intent.putExtra("prediccion", prediccion);
                            intent.putExtra("probabilidad", probabilidad);
                            intent.putExtra("id_fcder", id_fcder);
                            intent.putExtra("id_pet", id_pet);

                            // Iniciar la actividad después de 1 segundo utilizando Handler
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(intent);
                                }
                            }, 1000);
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error al obtener el analisis", Toast.LENGTH_LONG).show();
                        }



                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(sintomas.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    SessionManager sessionManager = new SessionManager(getApplicationContext());

                    // Obtiene la cédula del SessionManager
                    String cedula_usu = sessionManager.getCedulaUsu();

                    Map<String, String> parametros = new Hashtable<String, String>();

                    // Obtén la ID de la mascota seleccionada en el spinner
                    MascotaSeleccionada = String.valueOf(lista_mascotas.getSelectedItemId());
                    // Obtén el dato que deseas capturar de la lista `ids_Mascotas`
                    idMascota = ids_Mascotas.get(Integer.parseInt(MascotaSeleccionada));

                    parametros.put("cedula_dueno",cedula_usu);
                    parametros.put("id_mascota",idMascota);

                    parametros.put("enrojecimiento",bd_enrojecido);

                    parametros.put("mal_olor",bd_enrojecido);

                    parametros.put("pica_leve",bd_pica_leve);
                    parametros.put("pica_mode",bd_pica_moderada);
                    parametros.put("pica_inte",bd_pica_intensa);

                    parametros.put("alo_cabeza",bd_alo_cabeza);
                    parametros.put("alo_orejas",bd_alo_orejas);
                    parametros.put("alo_cuello",bd_alo_cuello);
                    parametros.put("alo_lomo",bd_alo_lomo);
                    parametros.put("alo_extremidades",bd_alo_extremidades);
                    parametros.put("alo_abdomen",bd_alo_abdomen);

                    parametros.put("costra_peque",bd_cost_peque);
                    parametros.put("costra_media",bd_cost_media);
                    parametros.put("costra_grand",bd_cost_grande);

                    parametros.put("piel_gru_leve",bd_gruesa_leve);
                    parametros.put("piel_gru_pron",bd_gruesa_pronunciada);
                    parametros.put("piel_gru_grav",bd_gruesa_grave);

                    parametros.put("pustula_peque",bd_pustula_peque);
                    parametros.put("pustula_grand",bd_pustula_grande);

                    parametros.put("eritema",bd_eritema);
                    parametros.put("sacudida_cabeza",bd_sacudir);
                    parametros.put("cerumen_oido",bd_cerumen);

                    return parametros;
                }
            };
            requestQueue.add(respuesta);

        }catch (Exception e){
            Toast.makeText(sintomas.this, "Se produjo un error al registrar sus datos.", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void menu(View v){
        Intent i = new Intent(this, menu.class);
        startActivity(i);
    }
}