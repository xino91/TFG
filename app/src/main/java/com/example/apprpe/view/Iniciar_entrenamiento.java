package com.example.apprpe.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.apprpe.R;
import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.view.navBottom.EntrenamientoNAV.EntrenamientoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Iniciar_entrenamiento extends AppCompatActivity {

    enum Temporizador{
        PARADO, PAUSADO, CORRIENDO
    }
    private Chronometer cronometro;
    private int Id_entrenamiento;
    private Temporizador temporizador = Temporizador.PARADO;
    private long pauseoffset = 0;
    private FloatingActionButton fab_play, fab_stop;
    private Button button_siguiente;
    private TextView nombre_ej, nset, nrepet, rpe, indicador_num_ejercicio;
    private List<Ejercicio> listEjercicios;
    private int num_ejercicio = 0, pulsado = 0;
    private long duracion_segundos;
    private String hora_inicio, hora_finalizacion, tipo, nombre_entrenamiento;
    private int carga;
    private int rpe_objetivo;
    ArrayList<Integer> date = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_iniciar_entrenamiento);
        hora_inicio = getHora();
        getIdEntrenamiento();
        enlazarVistas();

        EntrenamientoViewModel entrenamientoViewModel = new ViewModelProvider(this).get(EntrenamientoViewModel.class);
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
     * Esta función pasará a la siguiente activity la duración, hora comienzo y finalización del entrenamiento, nombre, tipo, carga
     */
    private void FinEntrenamiento() {
        hora_finalizacion = getHora();
        CargaTotal();
        Intent intent = new Intent(getApplicationContext(), infoentrenamientoRealizado.class);
        intent.putExtra("NOMBRE_ENTRENAMIENTO", nombre_entrenamiento);
        intent.putExtra("DURACION_EJERCICIO", duracion_segundos);
        intent.putExtra("HORA_INICIO", hora_inicio);
        intent.putExtra("HORA_FINALIZACION", hora_finalizacion);
        intent.putExtra("TIPO", tipo);
        intent.putExtra("CARGA", carga);
        intent.putExtra("RPE_OBJ", rpe_objetivo);
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
        Id_entrenamiento = extras.getInt("POSICION", 0);
        tipo = extras.getString("TIPO", "");
        rpe_objetivo = extras.getInt("RPE_OBJ", 0);
        nombre_entrenamiento = extras.getString("NOMBRE_ENTRENAMIENTO", "");
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
        CalcularCargaFuerza();
    }

    /**
     * Esta función va calculando la carga de todos los ejercicios según se realizan
     */
    public void CalcularCargaFuerza(){
        if(Objects.equals(tipo, "Fuerza")){
            int sets, repeticiones, rpe_obj;
            sets = listEjercicios.get(num_ejercicio).getSets();
            repeticiones = listEjercicios.get(num_ejercicio).getRepeticiones();
            rpe_obj = listEjercicios.get(num_ejercicio).getRpe();
            carga = carga + (rpe_obj * (sets * repeticiones) );
        }
    }

    /**
     * En caso de un entrenamiento de Fuerza está función divide la carga de todos los ejercicios entre el número de ejercicios realizados,
     * en caso de un entrenamiento Aeróbico multiplica la duración por Rpe objetivo
     */
    public void CargaTotal(){
        if(Objects.equals(tipo, "Fuerza")) {
            carga = carga / listEjercicios.size();
        }
        else{
            float duracion_minutos = (float) duracion_segundos / 60;
            carga = carga + (int)(listEjercicios.get(num_ejercicio).getRpe() * duracion_minutos);
        }
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
                        getDuracionEntrenamiento();
                        FinEntrenamiento();
                    }
                }
            }
        });
    }
}