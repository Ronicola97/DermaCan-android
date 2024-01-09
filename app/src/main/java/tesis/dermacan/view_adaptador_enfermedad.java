package tesis.dermacan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class view_adaptador_enfermedad extends RecyclerView.Adapter<view_adaptador_enfermedad.verTitulo> {

    ArrayList<view_enfermedades> view_enfermedadesArrayList;

    public view_adaptador_enfermedad(ArrayList<view_enfermedades> view_enfermedadesArrayList) {
        this.view_enfermedadesArrayList = view_enfermedadesArrayList;
    }

    @NonNull
    @Override
    public verTitulo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_enfermedades,parent,false);
        return new verTitulo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull verTitulo holder, int position) {
        view_enfermedades view_enfermedades = view_enfermedadesArrayList.get(position);

        holder.enfermedades.setImageResource(view_enfermedades.imageID);
        holder.enfermedad.setText(view_enfermedades.enfermedad);
        holder.descripción.setText(view_enfermedades.descripción);
        holder.contagio.setText(view_enfermedades.contagio);
        holder.contagio_a.setText(view_enfermedades.contagio_a);
        holder.contagio_b.setText(view_enfermedades.contagio_b);
        holder.sintomas.setText(view_enfermedades.sintomas);
        holder.sintoma_1.setText(view_enfermedades.sintoma_1);
        holder.sintoma_2.setText(view_enfermedades.sintoma_2);
        holder.sintoma_3.setText(view_enfermedades.sintoma_3);
        holder.sintoma_4.setText(view_enfermedades.sintoma_4);
        holder.sintoma_5.setText(view_enfermedades.sintoma_5);
        holder.sintoma_6.setText(view_enfermedades.sintoma_6);
        holder.sintoma_7.setText(view_enfermedades.sintoma_7);

    }

    @Override
    public int getItemCount() {
        return view_enfermedadesArrayList.size();
    }

    public class verTitulo extends RecyclerView.ViewHolder{

        ImageView enfermedades;
        TextView enfermedad, descripción, contagio, contagio_a, contagio_b, sintomas, sintoma_1, sintoma_2, sintoma_3, sintoma_4, sintoma_5, sintoma_6, sintoma_7;

        public verTitulo(@NonNull View itemView) {
            super(itemView);

            enfermedades = itemView.findViewById(R.id.enfermedades);
            enfermedad = itemView.findViewById(R.id.name_enfermedad);
            descripción = itemView.findViewById(R.id.descripción);
            contagio = itemView.findViewById(R.id.contagio);
            contagio_a = itemView.findViewById(R.id.descrip_contagio_a);
            contagio_b = itemView.findViewById(R.id.descrip_contagio_b);
            sintomas = itemView.findViewById(R.id.sintomas);
            sintoma_1 = itemView.findViewById(R.id.sintoma_1);
            sintoma_2 = itemView.findViewById(R.id.sintoma_2);
            sintoma_3 = itemView.findViewById(R.id.sintoma_3);
            sintoma_4 = itemView.findViewById(R.id.sintoma_4);
            sintoma_5 = itemView.findViewById(R.id.sintoma_5);
            sintoma_6 = itemView.findViewById(R.id.sintoma_6);
            sintoma_7 = itemView.findViewById(R.id.sintoma_7);
        }
    }

}
