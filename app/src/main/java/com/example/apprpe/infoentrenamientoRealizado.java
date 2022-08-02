package com.example.apprpe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.modelo.Ent_RealizadoDao;
import com.example.apprpe.ui.home.HomeViewModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class infoentrenamientoRealizado extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    private Ent_Realizado ent_realizado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoentrenamiento_realizado);
        Bundle extras = getIntent().getExtras();
        long duracion = extras.getLong("DURACION_EJERCICIO", 0);
        Date date = new Date(Calendar.getInstance().getTimeInMillis());

        //ent_realizado = new Ent_Realizado(date, duracion, 1,1,1,1,1);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        Log.i("DURACION", String.valueOf(duracion));
        Log.i("DIA", String.valueOf(date.getDate()) + "/" + String.valueOf(date.getMonth()+1) + "/" + String.valueOf(date.getYear()));
    }

}