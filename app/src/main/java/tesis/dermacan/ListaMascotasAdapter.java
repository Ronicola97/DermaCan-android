package tesis.dermacan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaMascotasAdapter extends RecyclerView.Adapter<ListaMascotasAdapter.MascotaViewHolder> {
    private Context mCtx;
    private ArrayList<view_lista_mascotas> listaMascotasArrayList;

    public ListaMascotasAdapter(Context mCtx, ArrayList<view_lista_mascotas> listaMascotasArrayList){
        this.mCtx = mCtx;
        this.listaMascotasArrayList = listaMascotasArrayList;

    }

    @Override
    public MascotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.view_lista_mascotas, null);
        return new MascotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MascotaViewHolder holder, int position){
        view_lista_mascotas mascota = listaMascotasArrayList.get(position);
        //cargar imagen
        Glide.with(mCtx)
                .load(mascota.getImage_pet())
                .into(holder.imageView);
        holder.nombre_pet.setText(mascota.getName_pet());
        holder.edad_pet.setText(String.valueOf(mascota.getAge_pet()));
        holder.raza_mascota.setText(mascota.getRaza_pet());
    }

    @Override
    public int getItemCount() { return listaMascotasArrayList.size();}

    public class MascotaViewHolder extends RecyclerView.ViewHolder{
        //textview para la interfaz
        TextView nombre_pet, edad_pet, raza_mascota;
        CircleImageView imageView;

        public MascotaViewHolder (View itemView){
            super (itemView);
            nombre_pet = itemView.findViewById(R.id.name_pet);
            edad_pet = itemView.findViewById(R.id.date_pet);
            raza_mascota = itemView.findViewById(R.id.raza_pet);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }



}

