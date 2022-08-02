package com.example.apprpe.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprpe.R;
import com.example.apprpe.modelo.Entrenamiento;


import java.util.List;

public class EntrenamientoListAdapter extends RecyclerView.Adapter<EntrenamientoListAdapter.SesionViewHolder>{

    private final LayoutInflater mInflater;
    private List<Entrenamiento> mSesiones; // Cached copy of Ejercicios
    private OnItemClickListener mlistener;

    EntrenamientoListAdapter(Context context) {this.mInflater = LayoutInflater.from(context);}

    @NonNull
    @Override
    public SesionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new SesionViewHolder(itemView, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull SesionViewHolder holder, int position) {
        if (mSesiones != null) {
            Entrenamiento current = mSesiones.get(position);
            holder.textViewID.setText(String.valueOf(current.getId()));
            holder.textviewNombre.setText(current.getNombre_Entrenamiento());
            holder.textviewNum.setText(String.valueOf(current.getNum_ejercicios()));
            holder.textviewRPE.setText(String.valueOf(current.getRpe_Sesion()));
        } else {
            // Covers the case of data not being ready yet.
            holder.textviewNombre.setText("Ninguna Sesion");
        }
    }

    void setSesion(List<Entrenamiento> sesiones){
        mSesiones = sesiones;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mSesiones != null)
            return mSesiones.size();
        else return 0;
    }
    void setOnItemClickListener(OnItemClickListener listener){
        this.mlistener = listener;
    }

    interface OnItemClickListener{
        void onItemClick(View view, int position, int Id_sesion) throws InterruptedException;
    }

    public class SesionViewHolder extends RecyclerView.ViewHolder{
       private final TextView textViewID;
       private final TextView textviewNombre;
       private final TextView textviewNum;
       private final TextView textviewRPE;

       private SesionViewHolder(View itemView, OnItemClickListener onItemClickListener) {
           super(itemView);
           textViewID = itemView.findViewById(R.id.Id_textView);
           textviewNombre = itemView.findViewById(R.id.Nombre_textView);
           textviewNum = itemView.findViewById(R.id.Num_textView);
           textviewRPE = itemView.findViewById(R.id.RPE_textView);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int pos = getAbsoluteAdapterPosition();
                   try {
                       mlistener.onItemClick(itemView, pos, mSesiones.get(pos).getId());
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           });
       }
   }
}


