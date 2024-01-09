package tesis.dermacan;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    RequestQueue requestQueue;

    private Context mCtx;
    private ArrayList<view_historial> HistorialArrayList;

    private static final int REQUEST_CODE_WRITE_STORAGE = 100;

    view_historial historialala;

    public HistorialAdapter(Context mCtx, ArrayList<view_historial> HistorialArrayList){
        this.mCtx = mCtx;
        this.HistorialArrayList = HistorialArrayList;

    }

    @Override
    public HistorialAdapter.HistorialViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.view_historial, null);
        return new HistorialAdapter.HistorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistorialAdapter.HistorialViewHolder holder, int position){
        view_historial historial = HistorialArrayList.get(position);
        //cargar imagen
        Glide.with(mCtx)
                .load(historial.getHruta_imagen())
                .into(holder.H_ruta_imagen);
        holder.H_nombre_pet.setText(historial.getHName_pet());
        holder.H_fecha_fcder.setText(String.valueOf(historial.getHfecha_fcder()));
        holder.H_diagnostico.setText(historial.getHdiagnostico());
    }

    @Override
    public int getItemCount() { return HistorialArrayList.size();}

    public class HistorialViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //textview para la interfaz
        TextView H_nombre_pet, H_fecha_fcder, H_diagnostico;
        CircleImageView H_ruta_imagen;

        public HistorialViewHolder (View itemView){
            super(itemView);
            H_nombre_pet = itemView.findViewById(R.id.Hname_pet);
            H_fecha_fcder = itemView.findViewById(R.id.Hdato_fecha);
            H_diagnostico = itemView.findViewById(R.id.Hdiagnostico);
            H_ruta_imagen = itemView.findViewById(R.id.HimageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // Obtiene la posición del elemento clickeado
            view_historial historiala = HistorialArrayList.get(position);
            historialala = historiala;

            // Aquí puedes llamar a tu función específica pasando cualquier dato necesario del historial
            Generar_Pdf(historiala);
        }

        public void Generar_Pdf(view_historial historiala) {
            String pagina = Globales.pagWeb + "/generar_pdf.php";
            System.out.println(pagina);
            requestQueue = Volley.newRequestQueue(mCtx);

            try{

                StringRequest respuesta = new StringRequest(Request.Method.POST, pagina, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { //response debe recibir un url a un archivo pdf
                        System.out.println(response);
                        if(response.equals("error")){
                            Toast.makeText(mCtx, "Error al generar el pdf", Toast.LENGTH_LONG).show();
                        }else{
                            //Toast.makeText(mCtx, response, Toast.LENGTH_SHORT).show();
                            try{

                                downloadAndOpenPdf(response);

                            }catch (Exception e){
                                //Toast.makeText(mCtx, "Se produjo un error al descargar el pdf.", Toast.LENGTH_SHORT).show();
                                Toast.makeText(mCtx, response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, "Hubo un error", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> parametros = new Hashtable<String, String>();
                        parametros.put("id_pet", historiala.getHid_pet());
                        parametros.put("id_fcder", historiala.getHid_fcder());
                        parametros.put("id_diag", historiala.getHid_diag());

                        return parametros;
                    }
                };
                requestQueue.add(respuesta);

            }catch (Exception e){
                Toast.makeText(mCtx, "Se produjo un error al generar el pdf.", Toast.LENGTH_SHORT).show();
            }


        }

        public void downloadAndOpenPdf(String url) {
            try {
                // Definir el nombre del archivo y la ruta de destino
                String fileName = historialala.getHName_pet() + "-Dig_" + historialala.getHdiagnostico() +"_"+historialala.getHid_diag()+ ".pdf";
                String destination = mCtx.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;

                File file = new File(destination);

                // Verificar si el archivo ya existe
                if (file.exists()) {
                    openPdfFile(file);
                    return;  // Salir del método si el archivo ya existe
                }

                // Crear un objeto URL con la URL del archivo PDF
                URL pdfUrl = new URL(url);
                Uri uri = Uri.parse(pdfUrl.toString());

                // Obtener el DownloadManager
                DownloadManager downloadManager = (DownloadManager) mCtx.getSystemService(Context.DOWNLOAD_SERVICE);

                // Crear una solicitud de descarga
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle(historialala.getHName_pet() + "-Dig_" + historialala.getHdiagnostico()+"_"+historialala.getHid_diag());
                request.setDescription("Diagnostico de la mascota: " + historialala.getHName_pet() + ", Tiene: " + historialala.getHdiagnostico());
                request.setDestinationUri(Uri.parse("file://" + destination));
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                // Iniciar la descarga
                long downloadId = downloadManager.enqueue(request);

                // Registrar el BroadcastReceiver para manejar la descarga completada
                BroadcastReceiver onComplete = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context ctxt, Intent intent) {
                        long completedDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                        if (completedDownloadId == downloadId) {
                            openPdfFile(file);  // Abrir el archivo después de descargarlo
                        }
                    }
                };

                // Registrar el BroadcastReceiver
                mCtx.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            } catch (ActivityNotFoundException e) {
                Log.e("OPEN_PDF_ERROR", "No se encontró una actividad para abrir el archivo PDF.", e);
            } catch (Exception e) {
                Log.e("OPEN_PDF_ERROR", "Error al intentar abrir el archivo PDF.", e);
            }
        }

        private void openPdfFile(File file) {
            Intent intents = new Intent(Intent.ACTION_VIEW);

            // Obtener el URI utilizando FileProvider
            Uri fileUri = FileProvider.getUriForFile(mCtx, mCtx.getApplicationContext().getPackageName() + ".provider", file);

            intents.setDataAndType(fileUri, "application/pdf");
            intents.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intents.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Necesario en versiones más recientes de Android

            try {
                mCtx.startActivity(intents);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(mCtx, "No hay aplicación disponible para abrir PDF", Toast.LENGTH_SHORT).show();
            }
        }



    }

}
