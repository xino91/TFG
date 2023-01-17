package com.example.apprpe.ui.EntrenamientoNAV;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprpe.R;
import com.example.apprpe.modelo.Entrenamiento;
import com.example.apprpe.VistaEjerciciosActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class EntrenamientoFragment extends Fragment {

    private static final int INSERT_SESION_ACTIVITY_CODE = 1;
    private static final int RESULT_OK = -1;
    private static final int RESULT_CANCELED = 0;
    private EntrenamientoViewModel entrenamientoViewModel;
    private RecyclerView recyclerView;
    private Chip botonFuerza;
    private Chip botonAerobico;
    private Chip botonTodo;
    private ChipGroup chipGroup;
    private FloatingActionButton fab;
    private TextView textVacio;
    EntrenamientoListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_entrenamiento, container, false);
        recyclerView = root.findViewById(R.id.recyclerview_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        entrenamientoViewModel = new ViewModelProvider(this).get(EntrenamientoViewModel.class);
        enlazarVistas(root);

        adapter = new EntrenamientoListAdapter(getContext());
        recyclerView.setAdapter(adapter);

        ObserverTODO();
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                if(botonTodo.getId() == checkedIds.get(0)){
                    ObserverTODO();
                } else if(botonFuerza.getId() == checkedIds.get(0)){
                    ObserverFUERZA();
                } else if (botonAerobico.getId() == checkedIds.get(0)){
                    ObserverAEROBICO();
                }
            }
        });

        //BOTON FLOTANTE Y ESCUCHADOR
        escuchadorBotonFlotante();

        //ESCUCHADOR PARA ADAPTADOR ENTRENAMIENTO
        adapter.setOnItemClickListener(new EntrenamientoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int id_entrenamiento) throws InterruptedException {
                Intent intent = new Intent(getActivity(), VistaEjerciciosActivity.class);
                Entrenamiento entrenamiento = entrenamientoViewModel.getEntrenamiento(id_entrenamiento);
                intent.putExtra("POSICION", entrenamiento.getId());
                intent.putExtra("TIPO", entrenamiento.getTipo());
                startActivity(intent);
            }
        });
        return root;
    }

    public void ObserverTODO(){
        entrenamientoViewModel.getAllEntrenamientos().observe(getViewLifecycleOwner(), new Observer<List<Entrenamiento>>() {
            @Override
            public void onChanged(List<Entrenamiento> entrenamientos) {
                if(entrenamientos.size() == 0){mostrar_TextVacio();}
                else{ocultar_TextVacio();}
                adapter.setEntrenamientos(entrenamientos);
            }
        });
    }

    public void ObserverFUERZA(){
        entrenamientoViewModel.getEntrenamientosFuerza().observe(getViewLifecycleOwner(), new Observer<List<Entrenamiento>>() {
            @Override
            public void onChanged(List<Entrenamiento> entrenamientos) {
                adapter.setEntrenamientos(entrenamientos);
            }
        });
    }

    public void ObserverAEROBICO(){
        entrenamientoViewModel.getEntrenamientosAerobico().observe(getViewLifecycleOwner(), new Observer<List<Entrenamiento>>() {
            @Override
            public void onChanged(List<Entrenamiento> entrenamientos) {
                adapter.setEntrenamientos(entrenamientos);
            }
        });
    }


    /***
     * Recibe un entrenamiento de otra activity(InsertarEntrenamiento_activity) para realizar el insert en la BD
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==INSERT_SESION_ACTIVITY_CODE && resultCode == RESULT_OK){
                Entrenamiento entrenamiento = new Entrenamiento();
                assert data != null;
                entrenamiento.setNombre_Entrenamiento(Objects.requireNonNull(data.getExtras()).getString("sesion_nombre"));
                entrenamiento.setRpe_Sesion(Integer.parseInt(data.getExtras().getString("RPE")));
                entrenamiento.setTipo(data.getExtras().getString("TipoDato"));
                entrenamientoViewModel.insert(entrenamiento);
                Log.i("INSERT", "sdfsfd");
                ObserverTODO();
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
            }
    }

    public void escuchadorBotonFlotante(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsertarEntrenamiento_activity.class);
                startActivityForResult(intent, INSERT_SESION_ACTIVITY_CODE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("RESUME", "RESUME");
    }

    /**
     * Vincula las vistas con las variables
     * @param root (View root)
     */
    private void enlazarVistas(View root) {
        fab = root.findViewById(R.id.floatingActionButton);
        botonFuerza = root.findViewById(R.id.buttonFuerza);
        botonAerobico = root.findViewById(R.id.buttonAerobico);
        botonTodo = root.findViewById(R.id.buttonTodo);
        textVacio = root.findViewById(R.id.textVacio);
        chipGroup = root.findViewById(R.id.chipgroup);
    }

    public void mostrar_TextVacio(){
        textVacio.setVisibility(View.VISIBLE);
    }

    public void ocultar_TextVacio(){
        textVacio.setVisibility(View.GONE);
    }



    /*private void CambiarBackgroundBotonesFiltro(Button botonFuerza, Button botonAerobico, Button botonTodo, String botonPulsado) {
        switch (botonPulsado){
            case "Fuerza":
                botonFuerza.setBackground(getActivity().getDrawable(R.drawable.button_filter_home));
                botonAerobico.setBackground(getActivity().getDrawable(R.drawable.button_filter_home_off));
                botonTodo.setBackground(getActivity().getDrawable(R.drawable.button_filter_home_off));
                break;
            case "Aerobico":
                botonFuerza.setBackground(getActivity().getDrawable(R.drawable.button_filter_home_off));
                botonAerobico.setBackground(getActivity().getDrawable(R.drawable.button_filter_home));
                botonTodo.setBackground(getActivity().getDrawable(R.drawable.button_filter_home_off));
                break;
            case "Todo":
                botonFuerza.setBackground(getActivity().getDrawable(R.drawable.button_filter_home_off));
                botonAerobico.setBackground(getActivity().getDrawable(R.drawable.button_filter_home_off));
                botonTodo.setBackground(getActivity().getDrawable(R.drawable.button_filter_home));
                break;
        }
    }*/
}