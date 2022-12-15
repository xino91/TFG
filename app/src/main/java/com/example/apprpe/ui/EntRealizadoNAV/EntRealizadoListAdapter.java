package com.example.apprpe.ui.EntRealizadoNAV;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprpe.R;
import com.example.apprpe.modelo.Ent_Realizado;

import java.util.List;
import java.util.Objects;

public class EntRealizadoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mInflater;
    private List<Ent_Realizado> mEntrenamientosRealizados; // Cached copy of Ejercicios
    private EntRealizadoListAdapter.OnItemClickListener mlistener;
    private final int TIPO_FUERZA = 0;
    private final int TIPO_AEROBICO= 1;

    EntRealizadoListAdapter(Context context) {this.mInflater = LayoutInflater.from(context);}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TIPO_FUERZA:
                View itemView_fuerza = mInflater.inflate(R.layout.recyclerview_item_fuerzarealizado, parent, false);
                return new EntRealizadoListAdapter.ViewHolderFuerzaRealizado(itemView_fuerza, mlistener);

            default:
                View itemView_aerobico = mInflater.inflate(R.layout.recyclerview_item_aerobicorealizado, parent, false);
                return new EntRealizadoListAdapter.ViewHolderAerobicoRealizado(itemView_aerobico, mlistener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Ent_Realizado current = mEntrenamientosRealizados.get(position);
        switch (holder.getItemViewType()){
            case TIPO_FUERZA:
                EntRealizadoListAdapter.ViewHolderFuerzaRealizado holderFuerzaRealizado = (ViewHolderFuerzaRealizado) holder;
                holderFuerzaRealizado.textDate.setText((current.getFecha().toString()));
                holderFuerzaRealizado.text_duracion.setText(String.valueOf(current.getDuracion()));
                break;
            case TIPO_AEROBICO:
                break;

        }
    }

    @Override
    public int getItemCount() {
        if (mEntrenamientosRealizados != null)
            return mEntrenamientosRealizados.size();
        else return 0;
    }

    void setEntrenamientosRealizados(List<Ent_Realizado> EntrenamientosRealizados){
        mEntrenamientosRealizados = EntrenamientosRealizados;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(Objects.equals(mEntrenamientosRealizados.get(position), "Fuerza")){
            return TIPO_FUERZA;
        }
        if(Objects.equals(mEntrenamientosRealizados.get(position), "Aeróbico")){
            return TIPO_AEROBICO;
        }
        return 0;
    }

    void setOnItemClickListener(OnItemClickListener listener){this.mlistener = listener;}

    interface OnItemClickListener{
        void onItemClick(View view, int position, int Id_entrenamiento) throws InterruptedException;
    }

    public class ViewHolderFuerzaRealizado extends RecyclerView.ViewHolder{
        private final TextView textDate;
        private final TextView text_duracion;

        private ViewHolderFuerzaRealizado(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            textDate = itemView.findViewById(R.id.TextDate);
            text_duracion = itemView.findViewById(R.id.textView_duracion);

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
        private final TextView textviewNombre;
        private final TextView textviewNum;
        private final TextView textviewRPE;

        private ViewHolderAerobicoRealizado(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            textviewNombre = itemView.findViewById(R.id.Nombre_textView);
            textviewNum = itemView.findViewById(R.id.Num_textView);
            textviewRPE = itemView.findViewById(R.id.RPE_textView);

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
