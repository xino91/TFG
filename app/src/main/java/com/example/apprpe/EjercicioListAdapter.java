package com.example.apprpe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.EntrenamientoConEjercicios;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EjercicioListAdapter extends RecyclerView.Adapter<EjercicioListAdapter.EjercicioViewHolder> {

    private final LayoutInflater mInflater;
    private List<Ejercicio> mEjercicio;
    private List<EntrenamientoConEjercicios> mEjercicios;
    private EjercicioListAdapter.OnItemClickListenerEjercicio mlistener;

    public EjercicioListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NotNull
    @Override
    public EjercicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_ejercicio, parent, false);
        return new EjercicioListAdapter.EjercicioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EjercicioViewHolder holder, int position) {
        if (mEjercicio.size() != 0) {
            Ejercicio current = mEjercicio.get(position);
            holder.textViewTitulo.setText(current.getNombre());
            holder.textviewSets.setText(String.valueOf(current.getSets()));
            holder.textviewRepeticiones.setText(String.valueOf(current.getRepeticiones()));
            holder.textviewRPE.setText(String.valueOf(current.getRpe()));
        } else {
            // Covers the case of data not being ready yet.
            holder.textViewTitulo.setText("Ningun Ejercicio");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    void setEjercicio(List<Ejercicio> ejercicios){
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

    void setOnItemClickListenerEjercicio(EjercicioListAdapter.OnItemClickListenerEjercicio listener){
        this.mlistener = listener;
    }

    interface OnItemClickListenerEjercicio{
        void onItemClick(View view, int position, int Id_ejercicio) throws InterruptedException;
    }

    public class EjercicioViewHolder extends RecyclerView.ViewHolder{
        private final TextView textViewTitulo;
        private final TextView textviewSets;
        private final TextView textviewRepeticiones;
        private final TextView textviewRPE;

        public EjercicioViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.nombre_titulo);
            textviewSets = itemView.findViewById(R.id.numero_sets);
            textviewRepeticiones = itemView.findViewById(R.id.numero_repeticiones);
            textviewRPE = itemView.findViewById(R.id.textView_num_RPE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    try {
                        mlistener.onItemClick(itemView, pos, mEjercicio.get(pos).getId_Ejercicio());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
