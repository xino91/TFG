package com.example.apprpe.ui.EntrenamientoNAV;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprpe.R;
import com.example.apprpe.modelo.Entrenamiento;


import java.util.List;
import java.util.Objects;

public class EntrenamientoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final LayoutInflater mInflater;
    private List<Entrenamiento> mEntrenamientos; // Cached copy of Ejercicios
    private OnItemClickListener mlistener;
    private final int TIPO_FUERZA = 0;
    private final int TIPO_AEROBICO= 1;

    EntrenamientoListAdapter(Context context) {this.mInflater = LayoutInflater.from(context);}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TIPO_FUERZA:
                View itemView_fuerza = mInflater.inflate(R.layout.recyclerview_item_fuerza, parent, false);
                return new ViewHolderFuerza(itemView_fuerza, mlistener);

            default:
                View itemView_aerobico = mInflater.inflate(R.layout.recyclerview_item_aerobico, parent, false);
                return new ViewHolderAerobico(itemView_aerobico, mlistener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Entrenamiento current = mEntrenamientos.get(position);
        switch (holder.getItemViewType()){
            case TIPO_FUERZA:
                ViewHolderFuerza holderFuerza = (ViewHolderFuerza) holder;
                holderFuerza.textviewNombre.setText(current.getNombre_Entrenamiento());
                holderFuerza.textviewNum.setText(String.valueOf(current.getNum_ejercicios()));
                holderFuerza.textviewRPE.setText(String.valueOf(current.getRpe_Sesion()));
                break;
            case TIPO_AEROBICO:
                ViewHolderAerobico holderAerobico = (ViewHolderAerobico) holder;
                holderAerobico.textviewNombre.setText(current.getNombre_Entrenamiento());
                holderAerobico.textviewNum.setText(String.valueOf(current.getNum_ejercicios()));
                holderAerobico.textviewRPE.setText(String.valueOf(current.getRpe_Sesion()));
                break;
        }
    }

    void setEntrenamientos(List<Entrenamiento> Entrenamientos){
        mEntrenamientos = Entrenamientos;
        notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mEntrenamientos != null)
            return mEntrenamientos.size();
        else return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(Objects.equals(mEntrenamientos.get(position).getTipo(), "Fuerza")){
            return TIPO_FUERZA;
        }
        if(Objects.equals(mEntrenamientos.get(position).getTipo(), "Aeróbico")){
            return TIPO_AEROBICO;
        }
        return 0;
    }

    public int getId(int pos){
        return mEntrenamientos.get(pos).getId();
    }

    void setOnItemClickListener(OnItemClickListener listener){
        this.mlistener = listener;
    }

    interface OnItemClickListener{
        void onItemClick(View view, int position, int Id_entrenamiento) throws InterruptedException;
    }

    public class ViewHolderFuerza extends RecyclerView.ViewHolder{
        private final ImageView imageView;
       private final TextView textviewNombre;
       private final TextView textviewNum;
       private final TextView textviewRPE;

       private ViewHolderFuerza(View itemView, OnItemClickListener onItemClickListener) {
           super(itemView);
           imageView = itemView.findViewById(R.id.imageView3);
           textviewNombre = itemView.findViewById(R.id.Nombre_textView);
           textviewNum = itemView.findViewById(R.id.Num_textView);
           textviewRPE = itemView.findViewById(R.id.RPE_textView);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int pos = getAbsoluteAdapterPosition();
                   try {
                       mlistener.onItemClick(itemView, pos, mEntrenamientos.get(pos).getId());
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           });
       }
   }

    public class ViewHolderAerobico extends RecyclerView.ViewHolder{
        private final ImageView imageView;
        private final TextView textviewNombre;
        private final TextView textviewNum;
        private final TextView textviewRPE;

        private ViewHolderAerobico(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView3);
            textviewNombre = itemView.findViewById(R.id.Nombre_textView);
            textviewNum = itemView.findViewById(R.id.Num_textView);
            textviewRPE = itemView.findViewById(R.id.RPE_textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    try {
                        mlistener.onItemClick(itemView, pos, mEntrenamientos.get(pos).getId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}


