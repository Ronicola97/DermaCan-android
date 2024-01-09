package tesis.dermacan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class informacion extends AppCompatActivity {

    ViewPager2 viewPager2;
    ArrayList<view_enfermedades> view_enfermedadesArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        viewPager2 = findViewById(R.id.enfermedades);
        int[] images = {R.drawable.sarna_sarc1, R.drawable.alergia_pulga1, R.drawable.otitis_externa1};
        String[] titulo_enf = {"Sarna Sarcóptica","Alergia por Picadura de Pulga", "Otitis Externa"};
        String[] descripcion = {getString(R.string.descrip_sarna),getString(R.string.descrip_pulga),getString(R.string.descrip_otitis)};

        String[] titulo_cont = {"Método de Contagio","Método de Contagio", "Método de Contagio"};
        String[] contagio1 = {getString(R.string.contagio_s_op1), getString(R.string.contagio_p_op1), getString(R.string.contagio_o_op1)};
        String[] contagio2 ={getString(R.string.contagio_s_op2),getString(R.string.contagio_p_op2), getString(R.string.contagio_o_op2)};

        String[] titulo_sint = {"Signos de la enfermedad","Signos de la enfermedad", "Signos de la enfermedad"};
        String[] sintomas1 = {getString(R.string.síntomas_s_op1),getString(R.string.síntomas_p_op1),getString(R.string.síntomas_o_op1)};
        String[] sintomas2 = {getString(R.string.síntomas_s_op2),getString(R.string.síntomas_p_op2),getString(R.string.síntomas_o_op2)};
        String[] sintomas3 = {getString(R.string.síntomas_s_op3),getString(R.string.síntomas_p_op3),getString(R.string.síntomas_o_op3)};
        String[] sintomas4 = {getString(R.string.síntomas_s_op4),getString(R.string.síntomas_p_op4),getString(R.string.síntomas_o_op4)};
        String[] sintomas5 = {getString(R.string.síntomas_s_op5),getString(R.string.síntomas_p_op5),getString(R.string.síntomas_o_op5)};
        String[] sintomas6 = {getString(R.string.síntomas_s_op6),getString(R.string.síntomas_p_op6),getString(R.string.síntomas_o_op6)};
        String[] sintomas7 = {getString(R.string.síntomas_s_op7),getString(R.string.síntomas_p_op7),getString(R.string.síntomas_o_op7)};



        view_enfermedadesArrayList = new ArrayList<>();

        for (int i=0; i<images.length; i++){
            view_enfermedades view_enfermedades = new view_enfermedades(images[i],titulo_enf[i],descripcion[i],
                    titulo_cont[i], contagio1[i], contagio2[i], titulo_sint[i], sintomas1[i],sintomas2[i],
                    sintomas3[i], sintomas4[i], sintomas5[i], sintomas6[i], sintomas7[i]);
            view_enfermedadesArrayList.add(view_enfermedades);
        }

        view_adaptador_enfermedad view_adaptador_enfermedad = new view_adaptador_enfermedad(view_enfermedadesArrayList);
        viewPager2.setAdapter(view_adaptador_enfermedad);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);


    }

    public void menu(View v){
        Intent i = new Intent(this, menu.class);
        startActivity(i);
    }
}