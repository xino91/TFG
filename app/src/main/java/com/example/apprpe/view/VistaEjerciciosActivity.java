package com.example.apprpe.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.apprpe.modelo.EntrenamientoConEjercicios;
import com.example.apprpe.view.adapters.EjercicioListAdapter;
import com.example.apprpe.R;
import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Entrenamiento;
import com.example.apprpe.view.navBottom.EntrenamientoNAV.EntrenamientoViewModel;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

public class VistaEjerciciosActivity extends AppCompatActivity {
    private EntrenamientoViewModel entrenamientoViewModel;
    private RecyclerView recyclerView;
    private int Id_entrenamiento;
    private Entrenamiento entrenamiento;
    private Button button_iniciar_entrenamiento;
    private TextView textVacio;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_ejercicio_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        VincularView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EjercicioListAdapter adapter = new EjercicioListAdapter(this);
        recyclerView.setAdapter(adapter);
        entrenamientoViewModel = new ViewModelProvider(this).get(EntrenamientoViewModel.class);
        try {
            getEntrenamiento();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        escuchadorBotonIniciarEntrenamiento();
        escuchadorAdapterEjercicio(adapter);
        entrenamientoViewModel.getEntrenamientoConEjercicios(Id_entrenamiento).observe(this, new Observer<List<Ejercicio>>() {
            @Override
            public void onChanged(List<Ejercicio> ejercicios) {
                if (ejercicios.size() == 0) {
                    button_iniciar_entrenamiento.setVisibility(View.GONE);
                    textVacio.setVisibility(View.VISIBLE);
                } else {
                    button_iniciar_entrenamiento.setVisibility(View.VISIBLE);
                    textVacio.setVisibility(View.GONE);
                }
                adapter.setEjercicio(ejercicios);
            }
        });

        /*entrenamientoViewModel.getAllSesionesConEjerciciosCross().observe(this, new Observer<List<EntrenamientoConEjercicios>>() {
            @Override
            public void onChanged(List<EntrenamientoConEjercicios> entrenamientoConEjercicios) {
                for(int i=0; i<entrenamientoConEjercicios.size(); i++){
                    Log.i("FOR", String.valueOf(entrenamientoConEjercicios.get(i).entrenamiento.getNombre_Entrenamiento()));
                    for(int j=0; j<entrenamientoConEjercicios.get(i).list_ejercicios.size(); j++){
                        Log.i("FOR", String.valueOf(entrenamientoConEjercicios.get(i).list_ejercicios.get(j).getNombre()));
                    }
                }
            }
        });*/

    }

    /**
     * Está función es el escuchador del adapter Ejercicio, cada vez que se pulse en un Ejercicio, está función cogerá los datos
     * y mediante un Intent nos mandará al activity EditarEjercicio
     * @param adapter para realizar la llamada al setOnItemClickListenerEjercicio
     */
    public void escuchadorAdapterEjercicio(EjercicioListAdapter adapter) {
        adapter.setOnItemClickListenerEjercicio(new EjercicioListAdapter.OnItemClickListenerEjercicio(){
            @Override
            public void onItemClick(View view, int position, int Id_ejercicio) throws InterruptedException {

                Intent i = new Intent(getApplication(), EditarEjercicio.class);
                Ejercicio ejercicio = new Ejercicio(entrenamientoViewModel.getEjercicio(Id_ejercicio));

                i.putExtra("NombreEjercicio", ejercicio.getNombre());
                i.putExtra("Sets", ejercicio.getSets());
                i.putExtra("Repeticiones", ejercicio.getRepeticiones());
                i.putExtra("RPE", ejercicio.getRpe());
                i.putExtra("ID_EJERCICIO", ejercicio.getId_Ejercicio());
                i.putExtra("ID_SESION", ejercicio.getEntrenamiento_Id());
                i.putExtra("TIPO", entrenamiento.getTipo());
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
                dialogoConfirmacionDelete();
                return true;
            }
        return false;
    }

    /**
     * Función que crea un Intent para el activity InsertarNuevoEjercicio y le pasa el Id_entrenamiento (ID_POSITION)
     * Llama al starActivityForResult
     */
    public void insertarNuevoEjercicio() {
        Intent intent = new Intent(this, InsertarNuevoEjercicio.class);
        intent.putExtra("TIPO", entrenamiento.getTipo());
        insertarEjercicioActivityResultLauncher.launch(intent);
    }

    /**
     * Función que elimina el entrenamiento por completo, eliminará todos sus ejercicios
     */
    public void eliminarEntrenamiento(){
        entrenamientoViewModel.deleteAllEjerciciosEntrenamiento(entrenamiento);
        entrenamientoViewModel.deleteEntrenamiento(entrenamiento);
        finish();
    }

    /**
     * Está función recoge los datos de un nuevo ejercicio (del activity insertar ejercicio), y procede a su insert en la BD
     */
    public final ActivityResultLauncher<Intent> insertarEjercicioActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;
                    Ejercicio ejercicio = new Ejercicio();
                    if(Objects.equals(entrenamiento.getTipo(), "Fuerza")){
                        ejercicio.setNombre(Objects.requireNonNull(data.getExtras()).getString("Ejercicio_nombre"));
                        ejercicio.setSets(Integer.parseInt(data.getExtras().getString("Set")));
                        ejercicio.setRepeticiones(Integer.parseInt(data.getExtras().getString("Repeticiones")));
                        ejercicio.setRpe(Integer.parseInt(data.getExtras().getString("RPE")));
                        ejercicio.setEntrenamiento_Id(Id_entrenamiento);
                        entrenamientoViewModel.insert(ejercicio); //INSERTAMOS
                    }
                    else{
                        ejercicio.setNombre(Objects.requireNonNull(data.getExtras()).getString("Ejercicio_nombre"));
                        ejercicio.setSets(0);
                        ejercicio.setRepeticiones(0);
                        ejercicio.setRpe(Integer.parseInt(data.getExtras().getString("RPE")));
                        ejercicio.setEntrenamiento_Id(Id_entrenamiento);
                        entrenamientoViewModel.insert(ejercicio); //INSERTAMOS
                    }
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                }
            }
    );

    /**
     * Esta función recoge el entrenamiento en una variable para su futura eliminación o edición
     */
    public void getEntrenamiento() throws InterruptedException {
        Bundle extras = getIntent().getExtras();
        Id_entrenamiento = extras.getInt("POSICION", -1);
        entrenamiento = entrenamientoViewModel.getEntrenamiento(Id_entrenamiento);
    }

    /**
     * Función escuchador de botón iniciar entrenamiento
     */
    public void escuchadorBotonIniciarEntrenamiento(){
        button_iniciar_entrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), Iniciar_entrenamiento.class);
                intent.putExtra("POSICION", Id_entrenamiento);
                intent.putExtra("NOMBRE_ENTRENAMIENTO", entrenamiento.getNombre_Entrenamiento());
                intent.putExtra("TIPO", entrenamiento.getTipo());
                intent.putExtra("RPE_OBJ", entrenamiento.getRpe_Objetivo());
                startActivity(intent);
            }
        });
    }

    /**
     * Función que muestra un dialogAlert para la confirmación del botón eliminar entrenamiento
     */
    public void dialogoConfirmacionDelete(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.DialogBasico);
        alertDialog.setTitle("Confirmación");
        alertDialog.setMessage("¿Está seguro que desea eliminar el entrenamiento?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarEntrenamiento();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
        .setCancelable(false)
        .show();
    }

    /**
     * Función para vincular las Vistas (View)
     */
    public void VincularView(){
        textVacio = findViewById(R.id.textVacio);
        button_iniciar_entrenamiento = findViewById(R.id.button_inicio_entrenamiento);
        recyclerView = findViewById(R.id.id_recyclerview_ejercicio);
    }
}