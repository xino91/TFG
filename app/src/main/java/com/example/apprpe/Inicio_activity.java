package com.example.apprpe;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Inicio_activity extends AppCompatActivity {

    SharedPreferences preferencias;
    String nombreUsuario, genero, email = "";
    String estatura, peso = "";
    String nacimiento;
    TextInputEditText edt_Nombre;
    RadioButton radbutton_masculino;
    RadioButton radbutton_femenino;
    EditText edt_Estatura;
    EditText edt_Peso;
    EditText edt_Mail;
    EditText buttonfecha;
    ImageButton imagebutton;
    Button buttonAvanzar;
    String spinner_actividad;
    int dia,mes,ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_activity);
        inicializarComponentes();

        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                ano = c.get(Calendar.YEAR);

                DatePickerDialog.OnDateSetListener setlistener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        buttonfecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(Inicio_activity.this, setlistener, ano, mes, dia);
                datePickerDialog.show();
            }
        });

        buttonAvanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogerDatos();
                if(comprobarDatos()) {
                    guardarPreferencias();
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private void inicializarComponentes(){
        edt_Nombre = findViewById(R.id.Edit_nombre);
        radbutton_masculino = findViewById(R.id.radioButton2);
        radbutton_femenino = findViewById(R.id.radioButton3);
        edt_Estatura = findViewById(R.id.Edit_estatura);
        edt_Peso = findViewById(R.id.Edit_peso);
        edt_Mail = findViewById(R.id.Edit_email);
        buttonfecha = findViewById(R.id.Edit_fecha);
        buttonAvanzar = findViewById(R.id.button_avanzar);
        imagebutton = findViewById(R.id.imageButton_fecha);
    }

    private void recogerDatos(){
        nombreUsuario = Objects.requireNonNull(edt_Nombre.getText()).toString();
        estatura = edt_Estatura.getText().toString();
        peso = edt_Peso.getText().toString();
        email = edt_Mail.getText().toString();
        nacimiento = buttonfecha.getText().toString();
    }

    private boolean comprobarDatos(){
        if (nombreUsuario.isEmpty()) {
            edt_Nombre.setError("Campo obligatorio");
            return false;
        } else if (!radbutton_masculino.isChecked() && !radbutton_femenino.isChecked()) {
            radbutton_masculino.setError("Obligatorio");
            radbutton_femenino.setError("Obligatorio");
            return false;
        } else if (edt_Estatura.getText().length() != 3) {
            edt_Estatura.setError("Campo obligatorio en cm, solo tres carácteres");
            return false;
        } else if (edt_Peso.getText().length() < 2 || edt_Peso.getText().length() > 3) {
            edt_Peso.setError("Campo obligatorio, dos o tres caracteres");
            return false;
        } else if (email.isEmpty()) {
            edt_Mail.setError("Campo obligatorio");
            return false;
        } else if (nacimiento.isEmpty()) {
            buttonfecha.setError("Campo obligatorio");
            return false;
        } else {
            if (radbutton_masculino.isChecked()) {
                genero = "Masculino";
            } else if (radbutton_femenino.isChecked()) {
                genero = "Femenino";
            }else if(!radbutton_masculino.isChecked() && !radbutton_femenino.isChecked()){
                return false;
            }
        }
        return true;
    }

    private void guardarPreferencias(){
        preferencias = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString("NombreUsuario", nombreUsuario);
        editor.putString("Genero", genero);
        editor.putString("Estatura", estatura);
        editor.putString("Peso", peso);
        editor.putString("Email", email);
        editor.putString("Actividad", spinner_actividad);
        editor.putString("Fecha", nacimiento);
        editor.apply();
    }

}