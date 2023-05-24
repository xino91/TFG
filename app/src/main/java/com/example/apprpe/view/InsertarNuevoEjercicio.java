package com.example.apprpe.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.apprpe.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class InsertarNuevoEjercicio extends AppCompatActivity {

    TextInputEditText edtNombre, edtSet, edtRepeticiones, edtRPE;
    TextInputLayout lay_set, lay_repet;
    Button buttonGuardar, buttonCancelar;
    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ejercicio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getTipo();
        vincularView();
        escuchadorBotonCancelar();
        escuchadorBotonGuardar();
    }

    /**
     * Escuchador del botón Guardar, el cual prepara los datos y entraga un RESULT_OK a la otra activity que recogerá los datos
     */
    private void escuchadorBotonGuardar() {
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comprobarDatos()) {
                    Intent intent = new Intent();
                    Integer set, repeticiones, rpe = null;

                    if(Objects.equals(tipo, "Fuerza")){
                        set = Integer.parseInt(Objects.requireNonNull(edtSet.getText()).toString());
                        repeticiones = Integer.parseInt(Objects.requireNonNull(edtRepeticiones.getText()).toString());
                        rpe = Integer.parseInt(Objects.requireNonNull(edtRPE.getText()).toString());
                        intent.putExtra("Ejercicio_nombre", Objects.requireNonNull(edtNombre.getText()).toString());
                        intent.putExtra("Set", String.valueOf(set));
                        intent.putExtra("Repeticiones", String.valueOf(repeticiones));
                        intent.putExtra("RPE", String.valueOf(rpe));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else{
                        rpe = Integer.parseInt(Objects.requireNonNull(edtRPE.getText()).toString());
                        intent.putExtra("Ejercicio_nombre", Objects.requireNonNull(edtNombre.getText()).toString());
                        intent.putExtra("RPE", String.valueOf(rpe));
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                }
            }
        });
    }

    /**
     * Escuchador del botón cancelar
     */
    private void escuchadorBotonCancelar() {
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    /**
     * Función que comprueba que las Vistas Editext no esten vacías, además de comprobar que los valores sean válidos
     * @return true si están correctos todos los datos, false en caso contrario
     */
    public boolean comprobarDatos() {
        if(Objects.equals(tipo, "Fuerza")){
            if (TextUtils.isEmpty(edtNombre.getText())) {
                edtNombre.setError("Campo obligatorio");
                return false;
            } else if (TextUtils.isEmpty(edtSet.getText()) || (Integer.parseInt((Objects.requireNonNull(edtSet.getText())).toString()) > 6) || (Integer.parseInt((Objects.requireNonNull(edtSet.getText().toString()))) == 0)){
                edtSet.setError("Campo obligatorio, comprendido entre 1 y 6");
                return false;
            } else if(TextUtils.isEmpty(edtRepeticiones.getText()) || (Integer.parseInt(Objects.requireNonNull(edtRepeticiones.getText()).toString())) >=100 || (Integer.parseInt((Objects.requireNonNull(edtRepeticiones.getText())).toString()) == 0)){
                edtRepeticiones.setError("Campo obligatorio, comprendido entre 1 y 100");
                return false;
            } else if (TextUtils.isEmpty(edtRPE.getText()) || (Integer.parseInt((Objects.requireNonNull(edtRPE.getText())).toString()) > 10) || (Integer.parseInt((edtRPE.getText()).toString()) < 0)) {
                edtRPE.setError("Campo Obligatorio, valor comprendido entre 0 y 10");
                return false;
            }
            return true;
        }
        else{
            if (TextUtils.isEmpty(edtNombre.getText())) {
                edtNombre.setError("Campo obligatorio");
                return false;
            } else if (TextUtils.isEmpty(edtRPE.getText()) || (Integer.parseInt((Objects.requireNonNull(edtRPE.getText())).toString()) >= 10) || (Integer.parseInt((edtRPE.getText()).toString()) == 0)) {
                edtRPE.setError("Campo Obligatorio, valor comprendido entre 1 y 10");
                return false;
            }
            return true;
        }
    }

    /**
     * Función para vincular las Vistas
     */
    public void vincularView(){
        if(Objects.equals(tipo, "Fuerza")){
            edtNombre = findViewById(R.id.editTextNombre_Ejercicio);
            edtSet = findViewById(R.id.editTextTNum_Set);
            edtRepeticiones = findViewById(R.id.editTextRepeticiones);
            edtRPE = findViewById(R.id.editTextRPEEjercicio);
            buttonGuardar = findViewById(R.id.buttonguardar);
            buttonCancelar = findViewById(R.id.button_cancelar);
        }
        else{
            lay_repet = findViewById(R.id.InputLayout_nom_set);
            lay_set = findViewById(R.id.InputLayout_nom_repe);
            edtNombre = findViewById(R.id.editTextNombre_Ejercicio);
            lay_repet.setVisibility(View.GONE);
            lay_set.setVisibility(View.GONE);
            edtRPE = findViewById(R.id.editTextRPEEjercicio);
            buttonGuardar = findViewById(R.id.buttonguardar);
            buttonCancelar = findViewById(R.id.button_cancelar);
        }
    }

    /**
     * Esta función recoge el entrenamiento en una variable para su futura eliminación o edición
     */
    public void getTipo(){
        Bundle extras = getIntent().getExtras();
        tipo = extras.getString("TIPO", "");
    }

}