package com.example.apprpe.ui.EntRealizadoNAV;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprpe.R;
import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.modelo.Entrenamiento;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class EntRealizadoFragment extends Fragment implements MenuProvider {

    private EntRealizadoViewModel entRealizadoViewModel;
    private RecyclerView recyclerView;
    private EntRealizadoListAdapter adapter;
    private TextView textVacio;
    private Chip botonFuerza;
    private Chip botonAerobico;
    private Chip botonTodo;
    private ChipGroup chipGroup;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_entrealizado, container, false);
        requireActivity().addMenuProvider(this, getViewLifecycleOwner());

        textVacio = root.findViewById(R.id.textView_vacio);
        enlazarVistas(root);
        recyclerView = root.findViewById(R.id.recyclerview_ent_realizado);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        entRealizadoViewModel = new ViewModelProvider(this).get(EntRealizadoViewModel.class);

        adapter = new EntRealizadoListAdapter(getContext());
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

        adapter.setOnItemClickListener(new EntRealizadoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int Id_entrenamiento) throws InterruptedException {

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
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAbsoluteAdapterPosition();
            int id = adapter.getId(position);
            try {
                Ent_Realizado ent_realizado = entRealizadoViewModel.getEntRealizado(id);
                entRealizadoViewModel.deleteEnt_Realizado(ent_realizado);
                //Snackbar para deshacer el borrado
                Snackbar.make(recyclerView, "", Snackbar.LENGTH_LONG).setAction("DESHACER", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        entRealizadoViewModel.insert(ent_realizado);
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
        entRealizadoViewModel.getAllEntrenamientosRealizados().observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
            @Override
            public void onChanged(List<Ent_Realizado> entrenamientos) {
                if(entrenamientos.size() == 0){mostrar_TextVacio();}
                else{ocultar_TextVacio();}
                adapter.setEntrenamientosRealizados(entrenamientos);
            }
        });
    }

    public void ObserverFUERZA(){
        Log.i("FUERZA", "FUERZA");
        entRealizadoViewModel.getEntrenamientosFuerzaRealizados().observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
            @Override
            public void onChanged(List<Ent_Realizado> entrenamientos) {
                adapter.setEntrenamientosRealizados(entrenamientos);
            }
        });
    }

    public void ObserverAEROBICO(){
        Log.i("AEROBICO", "AEROBICO");
        entRealizadoViewModel.getEntrenamientosAerobicoRealizados().observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
            @Override
            public void onChanged(List<Ent_Realizado> entrenamientos) {
                adapter.setEntrenamientosRealizados(entrenamientos);
            }
        });
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_entrealizado, menu);
    }
    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_eliminar_todo) {
            return ConfirmacionDialog();
        }
        return false;
    }

    private void enlazarVistas(View root) {
        botonFuerza = root.findViewById(R.id.buttonFuerza);
        botonAerobico = root.findViewById(R.id.buttonAerobico);
        botonTodo = root.findViewById(R.id.buttonTodo);
        textVacio = root.findViewById(R.id.textView_vacio);
        chipGroup = root.findViewById(R.id.chipgroup);
    }

    public void mostrar_TextVacio(){
        textVacio.setVisibility(View.VISIBLE);
    }

    public void ocultar_TextVacio(){
        textVacio.setVisibility(View.GONE);
    }

    public void eliminarTodosEntrenamientosRealizados(){
        entRealizadoViewModel.deleteAllEntrenamientosRealizados();
    }

    public boolean ConfirmacionDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("¿Está seguro que desea eliminar todos los entrenamientos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                eliminarTodosEntrenamientosRealizados();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
        return true;
    }
}