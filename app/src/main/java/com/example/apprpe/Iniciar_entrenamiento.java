package com.example.apprpe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.ui.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Iniciar_entrenamiento extends AppCompatActivity {

    enum Temporizador{
        PARADO, PAUSADO, CORRIENDO
    }
    private Chronometer cronometro;
    private int Id_entrenamiento;
    private HomeViewModel homeViewModel;
    private Temporizador temporizador = Temporizador.PARADO;
    private long pauseoffset;
    private FloatingActionButton fab_play, fab_stop;
    private Button button_siguiente;
    private TextView nombre_ej, nset, nrepet, rpe, muestra_ejercicio;
    private List<Ejercicio> listEjercicios;
    private int num_ejercicio = 0, pulsado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_entrenamiento);
        getIdEntrenamiento();
        enlazarVistas();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        try {
            listEjercicios = homeViewModel.getListEntrenamientoConEjercicios(Id_entrenamiento);
            modificarVistas();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //ESCUCHADOR DE BOTONES PLAY Y STOP
        fab_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(temporizador == Temporizador.PAUSADO || temporizador == Temporizador.PARADO) { startCronometro(view); }
                else{ pauseCronometro(view);}
            }
        });
        fab_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {stopCronometro(view);}
        });
        button_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num_ejercicio < listEjercicios.size()-1) {
                    num_ejercicio++;
                    modificarVistas();
                }
                if(num_ejercicio == listEjercicios.size()-1){
                    button_siguiente.setText(R.string.finalizar);
                    pulsado++;
                    if(pulsado >= 1){
                        FinEntrenamiento();
                    }
                }
            }
        });
    }

    private void FinEntrenamiento() {
        Intent intent = new Intent(getApplicationContext(), infoentrenamientoRealizado.class);
        //intent.putExtra();
        startActivity(intent);
    }

    public void getIdEntrenamiento(){
        Bundle extras = getIntent().getExtras();
        Id_entrenamiento = extras.getInt("Position", -1);
    }

    public void startCronometro(View view){
        fab_play.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_baseline_pause_24));
        cronometro.setBase(SystemClock.elapsedRealtime() - pauseoffset);
        cronometro.start();
        temporizador = Temporizador.CORRIENDO;
    }

    public void pauseCronometro(View view){
        fab_play.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_baseline_play_arrow_24));
        cronometro.stop();
        pauseoffset = SystemClock.elapsedRealtime() - cronometro.getBase();
        temporizador = Temporizador.PAUSADO;
    }

    public void stopCronometro(View view){
        fab_play.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_baseline_play_arrow_24));
        cronometro.setBase(SystemClock.elapsedRealtime());
        pauseoffset = 0;
        temporizador = Temporizador.PARADO;
        cronometro.stop();
    }

    public void enlazarVistas(){
        nombre_ej = findViewById(R.id.Nombre_ej);
        nset = findViewById(R.id.textView_nset);
        nrepet = findViewById(R.id.textView_repet);
        rpe = findViewById(R.id.textView_ini_rpe);
        fab_play = findViewById(R.id.fab_play);
        fab_stop = findViewById(R.id.fab_stop);
        button_siguiente = findViewById(R.id.butt_siguiente);
        cronometro = findViewById(R.id.cronometro);
        muestra_ejercicio = findViewById(R.id.textView_muestra_ejercicios);
    }

    private void modificarVistas() {
        nombre_ej.setText(listEjercicios.get(num_ejercicio).getNombre());
        nset.setText(String.valueOf(listEjercicios.get(num_ejercicio).getSets()));
        nrepet.setText(String.valueOf(listEjercicios.get(num_ejercicio).getRepeticiones()));
        rpe.setText(String.valueOf(listEjercicios.get(num_ejercicio).getRpe()));
        muestra_ejercicio.setText(num_ejercicio+1 +"/"+ listEjercicios.size());
    }
}