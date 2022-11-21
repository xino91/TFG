package com.example.apprpe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.ui.EntrenamientoNAV.EntrenamientoViewModel;
import java.sql.Date;
import java.util.Calendar;

public class infoentrenamientoRealizado extends AppCompatActivity {

    private EntrenamientoViewModel entrenamientoViewModel;
    Ent_Realizado entrenamiento_realizado;
    private TextView view_fecha, view_h_inicio, view_h_fin, view_carga, view_duracion, view_ind_monotonia;
    private Date date;
    private long duracion;
    private Button butt_terminar;
    private String hora_inicio, hora_finalizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoentrenamiento_realizado);
        enlazarVistas();
        obtenerDatosAnteriorActivity();
        Bundle extras = getIntent().getExtras();
        duracion = extras.getLong("DURACION_EJERCICIO", 0);

        date = new Date(Calendar.getInstance().getTimeInMillis());
        mostrarVistas();
        escuchadorBotonTerminar();
    }

    private void obtenerDatosAnteriorActivity() {
        Bundle extras = getIntent().getExtras();
        duracion = extras.getLong("DURACION_EJERCICIO", 0);
        hora_inicio = extras.getString("HORA_INICIO", "");
        hora_finalizacion = extras.getString("HORA_FINALIZACION", "");
        date = new Date(Calendar.getInstance().getTimeInMillis());
        Log.i("HORA_INICIO", hora_inicio);
        Log.i("HORA_FINALIZACION", hora_finalizacion);
    }

    //Funcion que crea un Intent para volver a la pantalla principal
    private void escuchadorBotonTerminar() {
        butt_terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrenamiento_realizado = new Ent_Realizado(date, duracion, 0, hora_inicio, hora_finalizacion, 0,0 );
                entrenamientoViewModel.insert(entrenamiento_realizado);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Toast.makeText(getApplicationContext(), "Entrenamiento Guardado", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }

    void enlazarVistas() {
        view_fecha = findViewById(R.id.txtView_fecha);
        view_h_inicio = findViewById(R.id.txtView_h_inicio);
        view_h_fin = findViewById(R.id.txtView_h_fin);
        view_duracion = findViewById(R.id.txtView_tiempo);
        view_carga = findViewById(R.id.txtView_Carga);
        view_ind_monotonia = findViewById(R.id.txtView_indice);
        butt_terminar = findViewById(R.id.bt_terminar);
        entrenamientoViewModel = new ViewModelProvider(this).get(EntrenamientoViewModel.class);
    }

    void mostrarVistas() {
        Log.i("DURACION", String.valueOf(duracion));
        Log.i("DIA", String.valueOf(date.getDate()) + "/" + String.valueOf(date.getMonth()+1) + "/" + String.valueOf(date.getYear()));
        view_fecha.setText(date.toString());
        view_duracion.setText(String.valueOf(duracion));
        view_h_inicio.setText(String.valueOf(hora_inicio));
        view_h_fin.setText(String.valueOf(hora_finalizacion));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Toast.makeText(this, "Cancelado, datos no guardados", Toast.LENGTH_LONG).show();
        startActivity(intent);

    }
}