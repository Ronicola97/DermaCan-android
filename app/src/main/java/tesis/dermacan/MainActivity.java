package tesis.dermacan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SessionManager sessionManager = new SessionManager(getApplicationContext());

                if (sessionManager.isLoggedIn()) {
                    // El usuario ya ha iniciado sesión, redirige a la actividad principal o a otra actividad.
                    Intent intent = new Intent(getApplicationContext(), menu.class);
                    startActivity(intent);
                } else {
                    // El usuario no ha iniciado sesión, muestra la actividad de inicio de sesión.
                    Intent ir_login = new Intent(getApplicationContext(), login.class);
                    startActivity(ir_login);
                }

            }
        },2000);

    }
}