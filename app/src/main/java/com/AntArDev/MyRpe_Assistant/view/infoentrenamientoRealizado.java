package com.AntArDev.MyRpe_Assistant.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.AntArDev.MyRpe_Assistant.MainActivity;
import com.AntArDev.MyRpe_Assistant.modelo.Ent_Realizado;
import com.AntArDev.MyRpe_Assistant.view.Dialog.CuadroDialogo_rpe;
import com.AntArDev.MyRpe_Assistant.view.Dialog.CuadroDialogo_rpeInfant;
import com.AntArDev.MyRpe_Assistant.view.Dialog.CuadroDialogo_satisfaccion;
import com.AntArDev.MyRpe_Assistant.view.navBottom.EntRealizadoNAV.EntRealizadoViewModel;
import com.example.apprpe.R;

import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

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
    private String key_escala;
    private int rpe_subjetivo;
    private int carga, resto, cociente;
    private Button butt_terminar;
    private String hora_inicio, hora_finalizacion, tipo, duracion_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_infoentrenamiento_realizado);
        enlazarVistas();
        obtenerDatosAnteriorActivity();
        obtenerSharedPreference();
        DuracionEnMinutos();
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
    }

    /**
     * Funcion que crea un Intent para volver a la pantalla principal
     */
    private void escuchadorBotonTerminar() {
        butt_terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.equals(key_escala, "Escala original (0-10)")){
                    CuadroDialogo_rpe cuadro_rpe = new CuadroDialogo_rpe();
                    CuadroRpe(cuadro_rpe);
                }
                else {
                    CuadroDialogo_rpeInfant cuadro_infant = new CuadroDialogo_rpeInfant();
                    CuadroRpeInfant(cuadro_infant);
                }
            }
        });
    }

    /**
     * Cuadro dialogo para escala original 1-10
     * @param dialogo de la clase CuadroDialogo_rpe
     */
    public void CuadroRpe(CuadroDialogo_rpe dialogo){
        dialogo.show(getSupportFragmentManager(), "Dialog_rpe");
        dialogo.setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialog_AppCompat);
        dialogo.setCancelable(false);
        dialogo.getRPE(new CuadroDialogo_rpe.CuadroDialogo_listener() {
            @Override
            public void apply_rpe(int rpe) {
                CuadroDialogo_satisfaccion dialogo_sat = new CuadroDialogo_satisfaccion();
                dialogo_sat.show(getSupportFragmentManager(), "Dialog_satisfaccion");
                dialogo_sat.setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialog_AppCompat);
                dialogo_sat.setCancelable(false);
                rpe_subjetivo = rpe;
                dialogo_sat.getSatisfaccion(new CuadroDialogo_satisfaccion.CuadroDialogo_listener() {
                    @Override
                    public void apply_Satisfaccion_Dolor(int satisfaccion, int dolor) {
                        CalcularCarga();
                        entrenamiento_realizado = new Ent_Realizado(nombre_entrenamiento, date, duracion, tipo, carga, hora_inicio,
                                hora_finalizacion, rpe_objetivo, rpe_subjetivo, satisfaccion, dolor );
                        entrealizadoViewModel.insert(entrenamiento_realizado);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Toast.makeText(getApplicationContext(), "Entrenamiento Guardado", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });
            }
        });
    }

    /**
     * Cuadro dialogo para Infant
     * @param dialogo de la clase CuadroDialogo_rpeInfant
     */
    public void CuadroRpeInfant(CuadroDialogo_rpeInfant dialogo){
        dialogo.show(getSupportFragmentManager(), "Dialog_rpe");
        dialogo.setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialog_AppCompat);
        dialogo.setCancelable(false);
        dialogo.getRPE(new CuadroDialogo_rpeInfant.CuadroDialogoInfant_listener() {
            @Override
            public void apply_rpeinfant(int rpe) {
                CuadroDialogo_satisfaccion dialogo_sat = new CuadroDialogo_satisfaccion();
                dialogo_sat.show(getSupportFragmentManager(), "Dialog_satisfaccion");
                dialogo_sat.setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialog_AppCompat);
                dialogo_sat.setCancelable(false);
                rpe_subjetivo = rpe;
                dialogo_sat.getSatisfaccion(new CuadroDialogo_satisfaccion.CuadroDialogo_listener() {
                    @Override
                    public void apply_Satisfaccion_Dolor(int satisfaccion, int dolor) {
                        CalcularCarga();
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
    public void DuracionEnMinutos(){
        resto = (int)duracion % 60;
        cociente = (int)duracion / 60;
        if(resto < 10 ) {
            duracion_string = String.valueOf(cociente) + ':' + String.valueOf(0) + String.valueOf(resto);
        }
        else{
            duracion_string = String.valueOf(cociente) + ':' + String.valueOf(resto);
        }
    }

    /**
     * Función para mostrar los datos en View vinculados
     */
    void mostrarVistas() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se perderán los datos de este entrenamiento, se necesita " +
                "continuar para seleccionar el esfuerzo percibido");
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Salir y no guardar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Salir();
            }
        });
        builder.show();

    }

    /**
     * Diferencia si un ejercicio es Fuerza o Aeróbico y calcula su carga con respecto al RPE subjetivo
     * ya que el RPE objetivo no sería la carga real
     */
    public void CalcularCarga(){
        if(Objects.equals(tipo, "Fuerza")) {
            if (rpe_objetivo == 0) {
                rpe_objetivo = 1;
                carga = 0;
            }
            carga = (carga / rpe_objetivo) * rpe_subjetivo;
        }
        else{
            float duracion_minutos = (float) duracion / 60;
            carga = Math.round(rpe_subjetivo * duracion_minutos); //Redondeamos el float
        }
    }

    public void obtenerSharedPreference(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        key_escala = preferences.getString("escala", "Escala original (0-10)");
    }

    public void Salir(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(this, "Datos no guardados", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

}