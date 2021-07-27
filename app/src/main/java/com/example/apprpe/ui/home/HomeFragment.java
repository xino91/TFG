package com.example.apprpe.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.apprpe.Inicio_activity;
import com.example.apprpe.R;
import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Sesion;
import com.example.apprpe.VistaEjercicioActivity;
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

        SesionListAdapter adapter = new SesionListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        homeViewModel.getAllSesiones().observe(getViewLifecycleOwner(), new Observer<List<Sesion>>() {
            @Override
            public void onChanged(List<Sesion> sesiones) {
                adapter.setSesion(sesiones);
            }
        });

        //BOTON FLOTANTE Y ESCUCHADOR
        fab = root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsertarSesion_activity.class);
                startActivityForResult(intent, INSERT_SESION_ACTIVITY_CODE);
            }
        });

        //ESCUCHADOR PARA ADAPTADOR SESION
        adapter.setOnItemClickListener(new SesionListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int id_sesion) throws InterruptedException {
                Intent intent = new Intent(getActivity(), VistaEjercicioActivity.class);
                Sesion sesion = homeViewModel.getSesion(id_sesion);
                intent.putExtra("Position", sesion.getId());
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
                Sesion sesion = new Sesion();
                assert data != null;
                sesion.setNombre_Sesion(Objects.requireNonNull(data.getExtras()).getString("sesion_nombre"));
                sesion.setRpe_Sesion(Integer.parseInt(data.getExtras().getString("RPE")));
                sesion.setTipo_Dato(data.getExtras().getString("TipoDato"));
                homeViewModel.insert(sesion);
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
            }
    }

}