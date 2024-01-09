package tesis.dermacan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class menu extends AppCompatActivity {

    TextView text_name;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        text_name = findViewById(R.id.view_UserName);

        // Inicializa el SessionManager
        sessionManager = new SessionManager(getApplicationContext());

        text_name.setText(sessionManager.getNombre());



    }

    public void CerrarSesion(View v){
        sessionManager.cerrarSesion();
        Intent intent = new Intent(getApplicationContext(), login.class);  // Cambia "login.class" por la actividad correspondiente
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Limpia las actividades anteriores
        startActivity(intent);
        finish();
    }




    public void informacion(View v){
        Intent i = new Intent(this, informacion.class);
        startActivity(i);
    }



    public void mascotas(View v){
        Intent i = new Intent(this, lista_mascotas.class);
        startActivity(i);
    }

    public void sintomas(View v){
        Intent i = new Intent(this, sintomas.class);
        startActivity(i);
    }

    public void historial(View v){
        Intent i = new Intent(this, historial.class);
        startActivity(i);
    }

    public void veterinarias(View v){
        Intent i = new Intent(this, ubicacion.class);
        startActivity(i);
    }
}