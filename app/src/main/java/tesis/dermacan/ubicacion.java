package tesis.dermacan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class ubicacion extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        ImageView imageView = findViewById(R.id.imageViewMapa); // Asegúrate de que el ID coincida con tu layout
        String url = "https://maps.googleapis.com/maps/api/staticmap?center=40.714728,-73.998672&zoom=12&size=400x400&key=AIzaSyDqTNpNsdIo9sAA1oT6W94KjZ05N8EbAzg";

        Glide.with(this)  // Cargar imagen usando Glide
                .load(url)
                .into(imageView);

    }

    public void menu(View v){
        Intent i = new Intent(this, menu.class);
        startActivity(i);
    }

}