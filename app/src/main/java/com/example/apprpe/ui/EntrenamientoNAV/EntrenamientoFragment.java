package com.example.apprpe.ui.EntrenamientoNAV;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class EntrenamientoFragment extends Fragment {

    private static final int INSERT_SESION_ACTIVITY_CODE = 1;
    private static final int RESULT_OK = -1;
    private static final int RESULT_CANCELED = 0;
    private EntrenamientoViewModel entrenamientoViewModel;
    private RecyclerView recyclerView;
    private Button botonFuerza;
    private Button botonAerobico;
    private Button botonTodo;
    private FloatingActionButton fab;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerview_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        entrenamientoViewModel = new ViewModelProvider(this).get(EntrenamientoViewModel.class);
        enlazarVistas(root);

        EntrenamientoListAdapter adapter = new EntrenamientoListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        entrenamientoViewModel.getAllEntrenamientos().observe(getViewLifecycleOwner(), new Observer<List<Entrenamiento>>() {
            @Override
            public void onChanged(List<Entrenamiento> entrenamientos) {
                adapter.setEntrenamientos(entrenamientos);
            }
        });

        botonFuerza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CambiarBackgroundBotonesFiltro(botonFuerza,botonAerobico,botonTodo, "Fuerza");
                entrenamientoViewModel.getEntrenamientosFuerza().observe(getViewLifecycleOwner(), new Observer<List<Entrenamiento>>() {
                    @Override
                    public void onChanged(List<Entrenamiento> entrenamientos) {
                        adapter.setEntrenamientos(entrenamientos);
                    }
                });
            }
        });
        botonAerobico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CambiarBackgroundBotonesFiltro(botonFuerza,botonAerobico,botonTodo, "Aerobico");
                entrenamientoViewModel.getEntrenamientosAerobico().observe(getViewLifecycleOwner(), new Observer<List<Entrenamiento>>() {
                    @Override
                    public void onChanged(List<Entrenamiento> entrenamientos) {
                        adapter.setEntrenamientos(entrenamientos);
                    }
                });
            }
        });
        botonTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CambiarBackgroundBotonesFiltro(botonFuerza,botonAerobico,botonTodo, "Todo");
                entrenamientoViewModel.getAllEntrenamientos().observe(getViewLifecycleOwner(), new Observer<List<Entrenamiento>>() {
                    @Override
                    public void onChanged(List<Entrenamiento> entrenamientos) {
                        adapter.setEntrenamientos(entrenamientos);
                    }
                });
            }
        });

        //BOTON FLOTANTE Y ESCUCHADOR
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsertarEntrenamiento_activity.class);
                startActivityForResult(intent, INSERT_SESION_ACTIVITY_CODE);
            }
        });

        //ESCUCHADOR PARA ADAPTADOR ENTRENAMIENTO
        adapter.setOnItemClickListener(new EntrenamientoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int id_entrenamiento) throws InterruptedException {
                Intent intent = new Intent(getActivity(), VistaEjerciciosActivity.class);
                Entrenamiento entrenamiento = entrenamientoViewModel.getEntrenamiento(id_entrenamiento);
                intent.putExtra("Position", entrenamiento.getId());
                startActivity(intent);
            }
        });
        return root;
    }

    private void enlazarVistas(View root) {
        fab = root.findViewById(R.id.floatingActionButton);
        botonFuerza = root.findViewById(R.id.buttonFuerza);
        botonAerobico = root.findViewById(R.id.buttonAerobico);
        botonTodo = root.findViewById(R.id.buttonTodo);
    }

    private void CambiarBackgroundBotonesFiltro(Button botonFuerza, Button botonAerobico, Button botonTodo, String botonPulsado) {
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
    }

    //Recibe una sesion de otra activity(InsertarSesion_activity) para realizar el insert en la BD
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
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
            }
    }
}