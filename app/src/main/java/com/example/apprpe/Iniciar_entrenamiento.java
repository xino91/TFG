package com.example.apprpe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.ui.EntrenamientoNAV.EntrenamientoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Iniciar_entrenamiento extends AppCompatActivity {

    enum Temporizador{
        PARADO, PAUSADO, CORRIENDO
    }
    private Chronometer cronometro;
    private int Id_entrenamiento;
    private EntrenamientoViewModel entrenamientoViewModel;
    private Temporizador temporizador = Temporizador.PARADO;
    private long pauseoffset = 0;
    private FloatingActionButton fab_play, fab_stop;
    private Button button_siguiente;
    private TextView nombre_ej, nset, nrepet, rpe, indicador_num_ejercicio;
    private List<Ejercicio> listEjercicios;
    private int num_ejercicio = 0, pulsado = 0;
    private long duracion_segundos;
    private String hora_inicio, hora_finalizacion, tipo;
    private int dia, mes, anno;
    private int carga;
    ArrayList<Integer> date = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_entrenamiento);
        hora_inicio = getHora();
        getIdEntrenamiento();
        enlazarVistas();

        entrenamientoViewModel = new ViewModelProvider(this).get(EntrenamientoViewModel.class);
        try {
            listEjercicios = entrenamientoViewModel.getListEntrenamientoConEjercicios(Id_entrenamiento);
            modificarVistas();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        escuchadoresBotones();
    }

    /**
     * Función que crea un Intent y llama a la avtivity infoentrenamientoRealizado.
     * Esta función pasará a la siguiente activity la duración, hora comienzo y finalización del entrenamiento
     */
    private void FinEntrenamiento() {
        getDuracionEntrenamiento();
        hora_finalizacion = getHora();
        Intent intent = new Intent(getApplicationContext(), infoentrenamientoRealizado.class);
        intent.putExtra("DURACION_EJERCICIO", duracion_segundos);
        intent.putExtra("HORA_INICIO", hora_inicio);
        intent.putExtra("HORA_FINALIZACION", hora_finalizacion);
        intent.putExtra("TIPO", tipo);
        startActivity(intent);
    }

    private void getDuracionEntrenamiento() { duracion_segundos = pauseoffset / 1000; }

    /**
     * Función que devuelve un String con la hora
     * @return String con la hora en formato ISO_LOCAL_TIME
     */
    private String getHora() {
        LocalTime hora = LocalTime.now();
        return hora.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void getIdEntrenamiento(){
        Bundle extras = getIntent().getExtras();
        Id_entrenamiento = extras.getInt("POSICION", -1);
        tipo = extras.getString("TIPO", "");
    }

    /**
     * Función que inicia el cronómetro, también modifica la vista del icono play
     */
    public void startCronometro(){
        fab_play.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_baseline_pause_24));
        cronometro.setBase(SystemClock.elapsedRealtime() - pauseoffset);
        cronometro.start();
        temporizador = Temporizador.CORRIENDO;
    }

    /**
     * Función que pausa el cronómetro, también modifica la vista del icono pause
     */
    public void pauseCronometro(){
        if(temporizador == Temporizador.CORRIENDO) {
            fab_play.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_baseline_play_arrow_24));
            cronometro.stop();
            pauseoffset = SystemClock.elapsedRealtime() - cronometro.getBase();
            temporizador = Temporizador.PAUSADO;
        }
    }

    /**
     * Función que para el cronómetro y lo pone a cero.
     */
    public void stopCronometro(){
        fab_play.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_baseline_play_arrow_24));
        cronometro.setBase(SystemClock.elapsedRealtime());
        pauseoffset = 0;
        temporizador = Temporizador.PARADO;
        cronometro.stop();
    }

    /**
     * Función que vincula las View
     */
    public void enlazarVistas(){
        nombre_ej = findViewById(R.id.Nombre_ej);
        nset = findViewById(R.id.textView_nset);
        nrepet = findViewById(R.id.textView_repet);
        rpe = findViewById(R.id.textView_ini_rpe);
        fab_play = findViewById(R.id.fab_play);
        fab_stop = findViewById(R.id.fab_stop);
        button_siguiente = findViewById(R.id.butt_siguiente);
        cronometro = findViewById(R.id.cronometro);
        indicador_num_ejercicio = findViewById(R.id.textView_muestra_ejercicios);
    }

    /**
     * Función que muestra los datos en los View vinculados
     */
    private void modificarVistas() {
        nombre_ej.setText(listEjercicios.get(num_ejercicio).getNombre());
        nset.setText(String.valueOf(listEjercicios.get(num_ejercicio).getSets()));
        nrepet.setText(String.valueOf(listEjercicios.get(num_ejercicio).getRepeticiones()));
        rpe.setText(String.valueOf(listEjercicios.get(num_ejercicio).getRpe()));
        indicador_num_ejercicio.setText(num_ejercicio+1 +"/"+ listEjercicios.size());
    }

    /**
     * Función donde se encuentran los listeners de los botones play, stop y siguiente_ejercicio
     */
    private void escuchadoresBotones() {
        fab_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(temporizador == Temporizador.PAUSADO || temporizador == Temporizador.PARADO) { startCronometro(); }
                else{ pauseCronometro();}
            }
        });
        fab_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {stopCronometro();}
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
                    pulsado++; //Variable usada para obligar a pulsar dos veces en el boton para finalizar el entrenamiento
                    if(pulsado > 1){
                        pauseCronometro();
                        FinEntrenamiento();
                    }
                }
            }
        });
    }
}