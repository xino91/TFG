package com.example.apprpe.ui.EntRealizadoNAV;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprpe.R;
import com.example.apprpe.modelo.Ent_Realizado;

import java.util.List;

public class EntRealizadoFragment extends Fragment {

    private EntRealizadoViewModel entRealizadoViewModel;
    private RecyclerView recyclerView;
    private TextView textVacio;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        textVacio = root.findViewById(R.id.textView_vacio);

        recyclerView = root.findViewById(R.id.recyclerview_ent_realizado);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        entRealizadoViewModel = new ViewModelProvider(this).get(EntRealizadoViewModel.class);

        EntRealizadoListAdapter adapter = new EntRealizadoListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        entRealizadoViewModel.getAllEntrenamientosRealizados().observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
            @Override
            public void onChanged(List<Ent_Realizado> ent_realizados) {
                if(ent_realizados.size() == 0){textVacio.setVisibility(View.VISIBLE);}
                else{textVacio.setVisibility(View.GONE);}
                adapter.setEntrenamientosRealizados(ent_realizados);
            }
        });

        adapter.setOnItemClickListener(new EntRealizadoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int Id_entrenamiento) throws InterruptedException {

            }
        });
        return root;
    }
}