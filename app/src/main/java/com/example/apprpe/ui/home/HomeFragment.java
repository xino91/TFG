package com.example.apprpe.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class HomeFragment extends Fragment {

    private static final int INSERT_SESION_ACTIVITY_CODE = 1;
    private static final int RESULT_OK = -1;
    private static final int RESULT_CANCELED = 0;
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerview_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        EntrenamientoListAdapter adapter = new EntrenamientoListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        homeViewModel.getAllEntrenamientos().observe(getViewLifecycleOwner(), new Observer<List<Entrenamiento>>() {
            @Override
            public void onChanged(List<Entrenamiento> entrenamientos) {
                adapter.setSesion(entrenamientos);
            }
        });

        //BOTON FLOTANTE Y ESCUCHADOR
        fab = root.findViewById(R.id.floatingActionButton);
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
            public void onItemClick(View view, int position, int id_sesion) throws InterruptedException {
                Intent intent = new Intent(getActivity(), VistaEjerciciosActivity.class);
                Entrenamiento entrenamiento = homeViewModel.getEntrenamiento(id_sesion);
                intent.putExtra("Position", entrenamiento.getId());
                startActivity(intent);
            }
        });
        return root;
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
                homeViewModel.insert(entrenamiento);
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
            }
    }
}