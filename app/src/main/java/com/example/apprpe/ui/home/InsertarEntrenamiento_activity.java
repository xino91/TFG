package com.example.apprpe.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.apprpe.R;

public class InsertarEntrenamiento_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertar_sesion);

        EditText edt_titulo = findViewById(R.id.editTextTitulo);
        EditText edt_rpe = findViewById(R.id.editTextRPE);
        Button btn_insertar = findViewById(R.id.button_insertar);
        Button btn_Cancelar = findViewById(R.id.button_Cancelar);
        Spinner spinner = findViewById(R.id.spinner_sesion);

        String [] arreglo = {"Fuerza", "Aeróbico"};
        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arreglo);
        spinner.setAdapter(adapter_spinner);

        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btn_insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(TextUtils.isEmpty(edt_titulo.getText())) {
                    setResult(RESULT_CANCELED, intent);
                } else{
                    intent.putExtra("sesion_nombre",edt_titulo.getText().toString());
                    Integer integer = null;
                    integer = Integer.parseInt(edt_rpe.getText().toString());
                    intent.putExtra("RPE", String.valueOf(integer));
                    String spinner_actividad = spinner.getSelectedItem().toString();
                    intent.putExtra("TipoDato", spinner_actividad);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });

    }
}