package com.example.apprpe;

import android.content.Intent;
import android.os.Bundle;

import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Sesion;
import com.example.apprpe.ui.home.HomeViewModel;
import com.example.apprpe.ui.home.InsertarNuevoEjercicio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

public class VistaEjercicioActivity extends AppCompatActivity {

    private static final int INSERT_EJERCICIO_CODE = 1;
    private static final int RESULT_EJERCICO_OK = -1;
    private static final int RESULT_EJERCICIO_CANCELED = 0;
    HomeViewModel homeViewModel;
    RecyclerView recyclerView;
    int Id_sesion;
    Sesion sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_ejercicio_activity);

        recyclerView = findViewById(R.id.id_recyclerview_ejercicio);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        Bundle extras = getIntent().getExtras();
        Id_sesion = extras.getInt("Position", -1);
        try {
            sesion = homeViewModel.getSesion(Id_sesion);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        EjercicioListAdapter adapter = new EjercicioListAdapter(this);
        recyclerView.setAdapter(adapter);

        homeViewModel.getSesionConEjercicios(Id_sesion).observe(this, new Observer<List<Ejercicio>>() {
            @Override
            public void onChanged(List<Ejercicio> Ejercicios) {
                adapter.setEjercicio(Ejercicios);
            }
        });

        //ESCUCHADOR PARA ADAPTADOR EJERCICIO
        adapter.setOnItemClickListenerEjercicio(new EjercicioListAdapter.OnItemClickListenerEjercicio(){
            @Override
            public void onItemClick(View view, int position, int Id_ejercicio) throws InterruptedException {

                Intent i = new Intent(getApplication(), EditarEjercicio.class);
                Ejercicio ejercicio = new Ejercicio(homeViewModel.getEjercicio(Id_ejercicio));

                i.putExtra("NombreEjercicio", ejercicio.getNombre());
                i.putExtra("Sets", ejercicio.getSets());
                i.putExtra("Repeticiones", ejercicio.getRepeticiones());
                i.putExtra("RPE", ejercicio.getRpe());
                i.putExtra("ID_EJERCICIO", ejercicio.getId_Ejercicio());
                i.putExtra("ID_SESION", ejercicio.getSesion_Id());
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vista_ejercicio, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
            if(id == R.id.menu_insertar_ejercicio){
                insertarNuevoEjercicio();
                return true;
            }
            if(id == R.id.menu_eliminar_sesion){
                eliminarSesion();
                return true;
            }
        return false;
    }

    private void insertarNuevoEjercicio() {
        Intent intent = new Intent(this, InsertarNuevoEjercicio.class);
        intent.putExtra("ID_POSITION", Id_sesion);
        startActivityForResult(intent, INSERT_EJERCICIO_CODE);
    }

    private void eliminarSesion() {
        homeViewModel.deleteAllEjercicioSesion(sesion);
        homeViewModel.deleteSesion(sesion);
        finish();
    }

    //INSERTAMOS EJERCICIO OBTENIDO DE ACTIVITY NUEVO_EJERCICIO
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("RESULT_EJERCICIO_OK", String.valueOf(RESULT_EJERCICO_OK));
        //Log.e("Resul Code", String.valueOf(resultCode));
        if(requestCode==INSERT_EJERCICIO_CODE && resultCode == RESULT_EJERCICO_OK){
            Ejercicio ejercicio = new Ejercicio();
            assert data != null;
            ejercicio.setNombre(Objects.requireNonNull(data.getExtras()).getString("Ejercicio_nombre"));
            ejercicio.setSets(Integer.parseInt(data.getExtras().getString("Set")));
            ejercicio.setRepeticiones(Integer.parseInt(data.getExtras().getString("Repeticiones")));
            ejercicio.setRpe(Integer.parseInt(data.getExtras().getString("RPE")));
            ejercicio.setSesion_Id(Id_sesion);
            homeViewModel.insert(ejercicio); //INSERTAMOS
            homeViewModel.update_NumEjerciciosMas(Id_sesion); //ACTUALIZAMOS Numero Ejercicios de la Sesión
        }
        else if(resultCode == RESULT_EJERCICIO_CANCELED){
            Toast.makeText(getApplication(), "Cancelado", Toast.LENGTH_SHORT).show();
        }
    }
}