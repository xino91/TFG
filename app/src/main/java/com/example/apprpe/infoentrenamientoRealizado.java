package com.example.apprpe;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.ui.EntRealizadoNAV.EntRealizadoViewModel;
import com.example.apprpe.ui.EntrenamientoNAV.EntrenamientoViewModel;
import java.sql.Date;
import java.util.Calendar;

/**
 * Activity que muestra los datos de un entrenamiento finalizado
 */

public class infoentrenamientoRealizado extends AppCompatActivity {

    private EntRealizadoViewModel entrealizadoViewModel;
    Ent_Realizado entrenamiento_realizado;
    private TextView view_fecha, view_h_inicio, view_h_fin, view_carga, view_duracion;
    private String nombre_entrenamiento;
    private Date date;
    private long duracion;
    private int rpe_objetivo;
    private int rpe_subjetivo;
    private int carga, resto, cociente;
    private Button butt_terminar;
    private String hora_inicio, hora_finalizacion, tipo, duracion_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoentrenamiento_realizado);
        enlazarVistas();
        obtenerDatosAnteriorActivity();
        CargaEnMinutos();
        mostrarVistas();
        escuchadorBotonTerminar();
    }

    /**
     * Función que recoge los datos del activity anterior y los guarda en las variables..
     * duracion del entrenamiento, hora de inicio y finalización y el día en que se realizó.
     */
    private void obtenerDatosAnteriorActivity() {
        Bundle extras = getIntent().getExtras();
        nombre_entrenamiento = extras.getString("NOMBRE_ENTRENAMIENTO", "");
        duracion = extras.getLong("DURACION_EJERCICIO", 0);
        hora_inicio = extras.getString("HORA_INICIO", "");
        hora_finalizacion = extras.getString("HORA_FINALIZACION", "");
        tipo = extras.getString("TIPO", "");
        carga = extras.getInt("CARGA", 0);
        rpe_objetivo = extras.getInt("RPE_OBJ", 0);
        date = new Date(Calendar.getInstance().getTimeInMillis());
        //Log.i("HORA_INICIO", hora_inicio);
        //Log.i("HORA_FINALIZACION", hora_finalizacion);
    }

    /**
     * Funcion que crea un Intent para volver a la pantalla principal
     */
    private void escuchadorBotonTerminar() {
        butt_terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CuadroDialogo_rpe dialogo = new CuadroDialogo_rpe();
                dialogo.show(getSupportFragmentManager(), "Dialog_rpe");
                dialogo.setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialog_AppCompat);
                dialogo.setCancelable(false);
                dialogo.getRPE(new CuadroDialogo_rpe.CuadroDialogo_listener() {
                    @Override
                    public void apply_rpe(int rpe) {
                        CuadroDialogo_satisfaccion dialogo = new CuadroDialogo_satisfaccion();
                        dialogo.show(getSupportFragmentManager(), "Dialog_satisfaccion");
                        dialogo.setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialog_AppCompat);
                        dialogo.setCancelable(false);
                        rpe_subjetivo = rpe;
                        Log.i("RPE_SUB", String.valueOf(rpe_subjetivo));
                        dialogo.getSatisfaccion(new CuadroDialogo_satisfaccion.CuadroDialogo_listener() {
                            @Override
                            public void apply_satisfaccion(int satisfaccion, int dolor) {
                                Log.i("SATISFACCION", String.valueOf(satisfaccion));
                                Log.i("DOLOR", String.valueOf(dolor));
                                entrenamiento_realizado = new Ent_Realizado(nombre_entrenamiento, date, duracion, tipo, carga, hora_inicio,
                                        hora_finalizacion, rpe_objetivo, rpe_subjetivo, satisfaccion, dolor );
                                entrealizadoViewModel.insert(entrenamiento_realizado);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                Toast.makeText(getApplicationContext(), "Entrenamiento Guardado", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Función para vincular las Vistas
     */
    void enlazarVistas() {
        view_fecha = findViewById(R.id.txtView_fecha);
        view_h_inicio = findViewById(R.id.txtView_h_inicio);
        view_h_fin = findViewById(R.id.txtView_h_fin);
        view_duracion = findViewById(R.id.txtView_tiempo);
        view_carga = findViewById(R.id.txtView_Carga);
        butt_terminar = findViewById(R.id.bt_terminar);
        entrealizadoViewModel = new ViewModelProvider(this).get(EntRealizadoViewModel.class);
    }

    /**
     * Función para mostrar la duracion en minutos y segundos, ejemplo 2:16 minutos
     */
    public void CargaEnMinutos(){
        resto = (int)duracion % 60;
        cociente = (int)duracion / 60;
        Log.i("RESTO", String.valueOf(resto));
        Log.i("COCIENTE", String.valueOf(cociente));
        duracion_string = String.valueOf(cociente) + ':' + String.valueOf(resto);
    }


    /**
     * Función para mostrar los datos en View vinculados
     */
    void mostrarVistas() {
        //Log.i("DURACION", String.valueOf(duracion));
        //Log.i("DIA", String.valueOf(date.getDate()) + "/" + String.valueOf(date.getMonth()+1) + "/" + String.valueOf(date.getYear()));
        view_fecha.setText(date.toString());
        view_duracion.setText(duracion_string);
        view_h_inicio.setText(String.valueOf(hora_inicio));
        view_h_fin.setText(String.valueOf(hora_finalizacion));
        view_carga.setText(String.valueOf(carga));
    }

    /**
     * Si se pulsa botón atrás volveremos al activity MAIN sin guardar los datos del entrenamiento
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Toast.makeText(this, "Cancelado, datos no guardados", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
}