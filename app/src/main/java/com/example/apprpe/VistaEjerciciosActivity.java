package com.example.apprpe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Entrenamiento;
import com.example.apprpe.ui.EntrenamientoNAV.EntrenamientoViewModel;
import com.example.apprpe.ui.EntrenamientoNAV.InsertarNuevoEjercicio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private static final int INSERT_EJERCICIO_CODE = 1;
    private static final int RESULT_EJERCICO_OK = -1;
    private static final int RESULT_EJERCICIO_CANCELED = 0;
    EntrenamientoViewModel entrenamientoViewModel;
    RecyclerView recyclerView;
    int Id_entrenamiento;
    Entrenamiento entrenamiento;
    Button button_iniciar_entrenamiento;
    TextView textVacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_ejercicio_activity);

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
    private void insertarNuevoEjercicio() {
        Intent intent = new Intent(this, InsertarNuevoEjercicio.class);
        intent.putExtra("ID_POSITION", Id_entrenamiento);
        startActivityForResult(intent, INSERT_EJERCICIO_CODE);
    }

    /**
     * Función que elimina el entrenamiento por completo, eliminará todos sus ejercicios
     */
    private void eliminarEntrenamiento(){
        entrenamientoViewModel.deleteAllEjercicioSesion(entrenamiento);
        entrenamientoViewModel.deleteEntrenamiento(entrenamiento);
        finish();
    }

    /**
     * Está función recoge los datos de un nuevo ejercicio (del activity insertar ejercicio), y procede a su insert en la BD
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==INSERT_EJERCICIO_CODE && resultCode == RESULT_EJERCICO_OK){
            Ejercicio ejercicio = new Ejercicio();
            assert data != null;
            ejercicio.setNombre(Objects.requireNonNull(data.getExtras()).getString("Ejercicio_nombre"));
            ejercicio.setSets(Integer.parseInt(data.getExtras().getString("Set")));
            ejercicio.setRepeticiones(Integer.parseInt(data.getExtras().getString("Repeticiones")));
            ejercicio.setRpe(Integer.parseInt(data.getExtras().getString("RPE")));
            ejercicio.setEntrenamiento_Id(Id_entrenamiento);
            entrenamientoViewModel.insert(ejercicio); //INSERTAMOS
            entrenamientoViewModel.update_NumEjerciciosMas(Id_entrenamiento); //ACTUALIZAMOS Numero Ejercicios de la Sesión
        }
        else if(resultCode == RESULT_EJERCICIO_CANCELED){
            Toast.makeText(getApplication(), "Cancelado", Toast.LENGTH_SHORT).show();
        }
    }

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