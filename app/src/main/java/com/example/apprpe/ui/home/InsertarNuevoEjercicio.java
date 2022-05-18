package com.example.apprpe.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.apprpe.R;

public class InsertarNuevoEjercicio extends AppCompatActivity {

    EditText edtNombre, edtSet, edtRepeticiones, edtRPE;
    Button buttonGuardar, buttonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ejercicio);

        edtNombre = findViewById(R.id.editTextNombre_Ejercicio);
        edtSet = findViewById(R.id.editTextTNum_Set);
        edtRepeticiones = findViewById(R.id.editTextRepeticiones);
        edtRPE = findViewById(R.id.editTextRPEEjercicio);
        buttonGuardar = findViewById(R.id.buttonguardar);
        buttonCancelar = findViewById(R.id.button_cancelar);

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if(TextUtils.isEmpty(edtNombre.getText())) {
                    setResult(RESULT_CANCELED, intent);
                } else{
                    Integer set, repeticiones, rpe = null;
                    set = Integer.parseInt(edtSet.getText().toString());
                    repeticiones = Integer.parseInt(edtRepeticiones.getText().toString());
                    rpe = Integer.parseInt(edtRPE.getText().toString());

                    intent.putExtra("Ejercicio_nombre",edtNombre.getText().toString());
                    intent.putExtra("Set", String.valueOf(set));
                    intent.putExtra("Repeticiones", String.valueOf(repeticiones));
                    intent.putExtra("RPE", String.valueOf(rpe));
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
    }
}