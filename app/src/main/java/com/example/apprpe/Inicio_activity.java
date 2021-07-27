package com.example.apprpe;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.Date;

public class Inicio_activity extends AppCompatActivity {

    SharedPreferences preferencias;
    String nombreUsuario, genero, email = "";
    String estatura, peso = "";
    Date nacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_activity);

        EditText edt_Nombre = findViewById(R.id.Edit_nombre);
        RadioButton radbutton_masculino = findViewById(R.id.radioButton2);
        RadioButton radbutton_femenino = findViewById(R.id.radioButton3);
        EditText edt_Estatura = findViewById(R.id.Edit_estatura);
        EditText edt_Peso = findViewById(R.id.Edit_peso);
        EditText edt_Mail = findViewById(R.id.Edit_email);
        Spinner spinner = findViewById(R.id.spinner);

        String [] arreglo = {"Fuerza", "Aeróbico"};
        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arreglo);
        spinner.setAdapter(adapter_spinner);

        preferencias = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        Button Avanzar = findViewById(R.id.button_avanzar);
        Avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreUsuario = edt_Nombre.getText().toString();
                estatura = edt_Estatura.getText().toString();
                peso = edt_Peso.getText().toString();
                email = edt_Mail.getText().toString();

                if(nombreUsuario.isEmpty()){
                    edt_Nombre.setError("Campo obligatorio");
                } else if(!radbutton_masculino.isChecked() && !radbutton_femenino.isChecked()){
                    radbutton_masculino.setError("Obligatorio");
                    radbutton_femenino.setError("Obligatorio");
                }else if(edt_Estatura.getText().length() != 3){
                    edt_Estatura.setError("Campo obligatorio y solo tres caracteres");
                } else if(edt_Peso.getText().length() < 2 || edt_Peso.getText().length() > 3){
                    edt_Peso.setError("Campo obligatorio, dos o tres caracteres");
                } else if(email.isEmpty()){
                    edt_Mail.setError("Campo obligatorio");
                }
                else{
                    if(radbutton_masculino.isChecked()){
                        genero = "Masculino";
                    } else if(radbutton_femenino.isChecked()){
                        genero = "Femenino";
                    }

                    String spinner_actividad = spinner.getSelectedItem().toString();
                    editor.putString("NombreUsuario", nombreUsuario);
                    editor.putString("Genero", genero);
                    editor.putString("Estatura", estatura);
                    editor.putString("Peso", peso);
                    editor.putString("Email", email);
                    editor.putString("Actividad", spinner_actividad);
                    editor.apply();

                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}