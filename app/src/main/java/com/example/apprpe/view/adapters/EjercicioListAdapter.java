package com.example.apprpe.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprpe.R;
import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.EntrenamientoConEjercicios;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EjercicioListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mInflater;
    private List<Ejercicio> mEjercicio;
    private List<EntrenamientoConEjercicios> mEjercicios;
    private EjercicioListAdapter.OnItemClickListenerEjercicio mlistener;
    private final int TIPO_FUERZA = 0;
    private final int TIPO_AEROBICO= 1;

    public EjercicioListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TIPO_FUERZA:
                View itemView_fuerza = mInflater.inflate(R.layout.recyclerview_ejercicio_fuerza, parent, false);
                return new EjercicioViewHolder_Fuerza(itemView_fuerza, mlistener);

            default:
                View itemView_aerobico = mInflater.inflate(R.layout.recyclerview_ejercicio_aerobico, parent, false);
                return new EjercicioViewHolder_Aerobico(itemView_aerobico, mlistener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (mEjercicio.size() != 0) {
                Ejercicio current = mEjercicio.get(position);
                switch (holder.getItemViewType()) {
                    case TIPO_FUERZA:
                        EjercicioViewHolder_Fuerza holderFuerza = (EjercicioViewHolder_Fuerza) holder ;
                        holderFuerza.textViewTitulo.setText(current.getNombre());
                        holderFuerza.textviewSets.setText(String.valueOf(current.getSets()));
                        holderFuerza.textviewRepeticiones.setText(String.valueOf(current.getRepeticiones()));
                        //holderFuerza.textviewRPE.setText(String.valueOf(current.getRpe()));
                        break;
                    case TIPO_AEROBICO:
                        EjercicioViewHolder_Aerobico holderAerobico = (EjercicioViewHolder_Aerobico) holder ;
                        holderAerobico.textViewTitulo.setText(current.getNombre());
                        break;
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setEjercicio(List<Ejercicio> ejercicios){
        mEjercicio = ejercicios;
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    void setEjercicios(List<EntrenamientoConEjercicios> ejercicios){
        mEjercicios = ejercicios;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mEjercicio != null)
            return mEjercicio.size();
        else return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(mEjercicio.get(position).getSets() == 0 || mEjercicio.get(position).getRepeticiones() == 0){
            return TIPO_AEROBICO;
        }
        return TIPO_FUERZA;
    }

    public void setOnItemClickListenerEjercicio(OnItemClickListenerEjercicio listener){
        this.mlistener = listener;
    }

    public interface OnItemClickListenerEjercicio{
        void onItemClick(View view, int position, int Id_ejercicio) throws InterruptedException;
    }

    public class EjercicioViewHolder_Fuerza extends RecyclerView.ViewHolder{
        private final TextView textViewTitulo;
        private final TextView textviewSets;
        private final TextView textviewRepeticiones;

        public EjercicioViewHolder_Fuerza(View itemView, OnItemClickListenerEjercicio mlistener) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.nombre_titulo);
            textviewSets = itemView.findViewById(R.id.numero_sets);
            textviewRepeticiones = itemView.findViewById(R.id.numero_repeticiones);
            //textviewRPE = itemView.findViewById(R.id.textView_num_RPE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    try {
                        EjercicioListAdapter.this.mlistener.onItemClick(itemView, pos, mEjercicio.get(pos).getId_Ejercicio());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public class EjercicioViewHolder_Aerobico extends RecyclerView.ViewHolder{
        private final TextView textViewTitulo;

        public EjercicioViewHolder_Aerobico(View itemView, OnItemClickListenerEjercicio mlistener) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.nombre_titulo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    try {
                        EjercicioListAdapter.this.mlistener.onItemClick(itemView, pos, mEjercicio.get(pos).getId_Ejercicio());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
