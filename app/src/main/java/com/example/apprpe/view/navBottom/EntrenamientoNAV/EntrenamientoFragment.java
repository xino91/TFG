package com.example.apprpe.view.navBottom.EntrenamientoNAV;


import android.app.Activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprpe.R;
import com.example.apprpe.modelo.Entrenamiento;
import com.example.apprpe.modelo.EntrenamientoConEjercicios;
import com.example.apprpe.view.InsertarEntrenamiento;
import com.example.apprpe.view.VistaEjerciciosActivity;
import com.example.apprpe.view.adapters.EntrenamientoListAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class EntrenamientoFragment extends Fragment {

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
        //recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));


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

        //BOTON FLOTANTE, ESCUCHADOR
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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return root;
    }

    /**
     * ItemtouchHelper -> Clase de utilidad que permite el deslizamiento a derecha, izquierda, arriba o abajo
     * en el recyclerView para crear utilidades de borrar o mover un elemento
     */
     ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int pos_viewHolder = viewHolder.getAbsoluteAdapterPosition();
            int pos_target = target.getAbsoluteAdapterPosition();
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAbsoluteAdapterPosition();
            int id = adapter.getId(position);
            try {
                Entrenamiento entrenamiento = entrenamientoViewModel.getEntrenamiento(id);
                entrenamientoViewModel.deleteAllEjerciciosEntrenamiento(entrenamiento);
                entrenamientoViewModel.deleteEntrenamiento(entrenamiento);
                //Snackbar para deshacer el borrado
                Snackbar.make(recyclerView, "" , Snackbar.LENGTH_LONG).setAction("DESHACER", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        entrenamientoViewModel.insert(entrenamiento);
                    }
                }).show();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(),R.color.boton_home))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .setSwipeLeftLabelColor(R.color.black)
                    .addSwipeLeftLabel("ELIMINAR")
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

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
     * Recibe un entrenamiento de otra activity(InsertarEntrenamiento) para realizar el insert en la BD
     */

    public final ActivityResultLauncher<Intent> insertSesionActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Entrenamiento entrenamiento = new Entrenamiento();
                    Intent data = result.getData();
                    assert data != null;
                    entrenamiento.setNombre_Entrenamiento(Objects.requireNonNull(data.getExtras()).getString("sesion_nombre"));
                    entrenamiento.setRpe_Objetivo(Integer.parseInt(data.getExtras().getString("RPE")));
                    entrenamiento.setTipo(data.getExtras().getString("TipoDato"));
                    entrenamientoViewModel.insert(entrenamiento);
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                }
            }
    );


    public void escuchadorBotonFlotante(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsertarEntrenamiento.class);
                insertSesionActivityResultLauncher.launch(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        chipGroup.check(R.id.buttonTodo);
    }

    /**
     * Vincula las vistas con las variables
     * @param root (View root)
     */
    public void enlazarVistas(View root) {
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

}