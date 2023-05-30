package com.example.apprpe.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprpe.R;
import com.example.apprpe.modelo.Ent_Realizado;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class EntRealizadoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mInflater;
    private List<Ent_Realizado> mEntrenamientosRealizados; // Cached copy of Ejercicios
    private EntRealizadoListAdapter.OnItemClickListener mlistener;
    private final int TIPO_FUERZA = 0;
    private final int TIPO_AEROBICO= 1;

    public EntRealizadoListAdapter(Context context) {this.mInflater = LayoutInflater.from(context);}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TIPO_FUERZA:
                View itemView_fuerza = mInflater.inflate(R.layout.recyclerview_item_fuerzarealizado, parent, false);
                return new ViewHolderFuerzaRealizado(itemView_fuerza, mlistener);

            default:
                View itemView_aerobico = mInflater.inflate(R.layout.recyclerview_item_aerobicorealizado, parent, false);
                return new ViewHolderAerobicoRealizado(itemView_aerobico, mlistener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Ent_Realizado current = mEntrenamientosRealizados.get(position);
        Duration duration;
        switch (holder.getItemViewType()){
            case TIPO_FUERZA:
                EntRealizadoListAdapter.ViewHolderFuerzaRealizado holderFuerzaRealizado = (ViewHolderFuerzaRealizado) holder;
                holderFuerzaRealizado.nombre.setText(current.getNombre_entrenamiento());
                holderFuerzaRealizado.date.setText((current.getFecha().toString()));
                duration = Duration.ofSeconds(current.getDuracion()); //Para cambiar la duracion de long a formato minutos,segundos
                holderFuerzaRealizado.duracion.setText(duration.toString().substring(2));
                holderFuerzaRealizado.carga.setText(String.valueOf(current.getCarga()));
                holderFuerzaRealizado.rpe_objetivo.setText(String.valueOf(current.getRpe_objetivo()));
                holderFuerzaRealizado.rpe_subjetivo.setText(String.valueOf(current.getRpe_subjetivo()));
                switch (current.getDolor()){
                    case 1: holderFuerzaRealizado.dolor.setText("Suave"); break;
                    case 2: holderFuerzaRealizado.dolor.setText("Moderado"); break;
                    case 3: holderFuerzaRealizado.dolor.setText("Mucho"); break;
                    case 4: holderFuerzaRealizado.dolor.setText("Insoportable"); break;
                    default: holderFuerzaRealizado.dolor.setText("Sin dolor"); break;
                }
                switch (current.getSatisfaccion()){
                    case 1: holderFuerzaRealizado.satisfaccion.setImageResource(R.drawable.sat_enfermo1); break;
                    case 2: holderFuerzaRealizado.satisfaccion.setImageResource(R.drawable.sat_asustado2); break;
                    case 4: holderFuerzaRealizado.satisfaccion.setImageResource(R.drawable.sat_sonreir4); break;
                    case 5: holderFuerzaRealizado.satisfaccion.setImageResource(R.drawable.sat_risa5); break;
                    default: holderFuerzaRealizado.satisfaccion.setImageResource(R.drawable.sat_silencioso3); break;
                }
                break;
            case TIPO_AEROBICO:
                EntRealizadoListAdapter.ViewHolderAerobicoRealizado holderAerobicoRealizado= (ViewHolderAerobicoRealizado) holder;
                holderAerobicoRealizado.nombre.setText(current.getNombre_entrenamiento());
                holderAerobicoRealizado.date.setText((current.getFecha().toString()));
                duration = Duration.ofSeconds(current.getDuracion());
                holderAerobicoRealizado.duracion.setText(duration.toString().substring(2));
                holderAerobicoRealizado.carga.setText(String.valueOf(current.getCarga()));
                holderAerobicoRealizado.rpe_objetivo.setText(String.valueOf(current.getRpe_objetivo()));
                holderAerobicoRealizado.rpe_subjetivo.setText(String.valueOf(current.getRpe_subjetivo()));
                switch (current.getDolor()){
                    case 1: holderAerobicoRealizado.dolor.setText("Suave"); break;
                    case 2: holderAerobicoRealizado.dolor.setText("Moderado"); break;
                    case 3: holderAerobicoRealizado.dolor.setText("Mucho"); break;
                    case 4: holderAerobicoRealizado.dolor.setText("Insoportable"); break;
                    default: holderAerobicoRealizado.dolor.setText("Sin dolor"); break;
                }
                switch (current.getSatisfaccion()){
                    case 1: holderAerobicoRealizado.satisfaccion.setImageResource(R.drawable.sat_enfermo1); break;
                    case 2: holderAerobicoRealizado.satisfaccion.setImageResource(R.drawable.sat_asustado2); break;
                    case 4: holderAerobicoRealizado.satisfaccion.setImageResource(R.drawable.sat_sonreir4); break;
                    case 5: holderAerobicoRealizado.satisfaccion.setImageResource(R.drawable.sat_risa5); break;
                    default: holderAerobicoRealizado.satisfaccion.setImageResource(R.drawable.sat_silencioso3); break;
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mEntrenamientosRealizados != null)
            return mEntrenamientosRealizados.size();
        else return 0;
    }

    public void setEntrenamientosRealizados(List<Ent_Realizado> EntrenamientosRealizados){
        mEntrenamientosRealizados = EntrenamientosRealizados;
        notifyDataSetChanged();
    }



    public int getId(int pos){
        return mEntrenamientosRealizados.get(pos).getId();
    }

    @Override
    public int getItemViewType(int position) {
        if(Objects.equals(mEntrenamientosRealizados.get(position).getTipo(), "Fuerza")){
            return TIPO_FUERZA;
        }
        if(Objects.equals(mEntrenamientosRealizados.get(position).getTipo(), "Aeróbico")){
            return TIPO_AEROBICO;
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener){this.mlistener = listener;}

    public interface OnItemClickListener{
        void onItemClick(View view, int position, int Id_entrenamiento) throws InterruptedException;
    }

    public class ViewHolderFuerzaRealizado extends RecyclerView.ViewHolder{
        private final TextView nombre;
        private final TextView date;
        private final TextView duracion;
        private final TextView carga;
        private final TextView rpe_objetivo;
        private final TextView rpe_subjetivo;
        private final ImageView satisfaccion;
        private final TextView dolor;

        private ViewHolderFuerzaRealizado(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            date = itemView.findViewById(R.id.TextDate);
            duracion = itemView.findViewById(R.id.textView_duracion);
            carga = itemView.findViewById(R.id.textView_carga);
            nombre = itemView.findViewById(R.id.textView);
            rpe_objetivo = itemView.findViewById(R.id.textView_objetivo);
            rpe_subjetivo = itemView.findViewById(R.id.textView_percibido);
            satisfaccion = itemView.findViewById(R.id.imageView_satisfaccion);
            dolor = itemView.findViewById(R.id.textView_dolor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    try {
                        mlistener.onItemClick(itemView, pos, mEntrenamientosRealizados.get(pos).getId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public class ViewHolderAerobicoRealizado extends RecyclerView.ViewHolder{
        private final TextView nombre;
        private final TextView date;
        private final TextView duracion;
        private final TextView carga;
        private final TextView rpe_objetivo;
        private final TextView rpe_subjetivo;
        private final ImageView satisfaccion;
        private final TextView dolor;

        private ViewHolderAerobicoRealizado(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            date = itemView.findViewById(R.id.TextDate);
            duracion = itemView.findViewById(R.id.textView_duracion);
            carga = itemView.findViewById(R.id.textView_carga);
            nombre = itemView.findViewById(R.id.textView);
            rpe_objetivo = itemView.findViewById(R.id.textView_objetivo);
            rpe_subjetivo = itemView.findViewById(R.id.textView_percibido);
            satisfaccion = itemView.findViewById(R.id.imageView_satisfaccion);
            dolor = itemView.findViewById(R.id.textView_dolor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    try {
                        mlistener.onItemClick(itemView, pos, mEntrenamientosRealizados.get(pos).getId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
