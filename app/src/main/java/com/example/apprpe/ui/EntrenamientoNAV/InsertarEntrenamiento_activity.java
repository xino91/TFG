package com.example.apprpe.ui.EntrenamientoNAV;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.apprpe.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class InsertarEntrenamiento_activity extends AppCompatActivity {

    TextInputEditText edt_titulo, edt_rpe;
    Button btn_insertar, btn_Cancelar;
    AutoCompleteTextView spinner_desplegable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertar_entrenamiento);

        VincularView();
        listener_botonCancelar();
        listener_botonInsertar();
    }

    /**
     * Escuchador del boton cancelar, finalizará el activity sin guardar datos
     */
    private void listener_botonCancelar() {
        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    /**
     * Escuchador del botón insertar o guardar, llama a comprobarDatos(), en caso de true enviará un RESULT_OK al activity anterior
     * con los datos de las Vistas (View)
     */
    private void listener_botonInsertar(){
        btn_insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobarDatos()) {
                    Intent intent = new Intent();
                    intent.putExtra("sesion_nombre", Objects.requireNonNull(edt_titulo.getText()).toString());
                    Integer integer = null;
                    integer = Integer.parseInt(Objects.requireNonNull(edt_rpe.getText()).toString());
                    intent.putExtra("RPE", String.valueOf(integer));
                    String spinner = spinner_desplegable.getText().toString();
                    intent.putExtra("TipoDato", spinner);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    /**
     * Esta función comprobará que ningún campo de las View es vacío
     * @return boolean, false si algún campo de View es vacío, true en caso contrario
     */
    private boolean comprobarDatos() {
        if(TextUtils.isEmpty(edt_titulo.getText())) {
            edt_titulo.setError("Campo Obligatorio");
            return false;
        } else if (TextUtils.isEmpty(edt_rpe.getText()) || (Integer.parseInt((Objects.requireNonNull(edt_rpe.getText())).toString()) > 10) || (Integer.parseInt((edt_rpe.getText()).toString()) == 0)){
            edt_rpe.setError("Campo Obligatorio, valor comprendido entre 1 y 10");
            return false;
        } else if(TextUtils.isEmpty(spinner_desplegable.getText())){
            spinner_desplegable.setError("Campo Obligatorio");
            return false;
        }
        return true;
    }

    /**
     * Esta función vincula los datos con las View
     */
    private void VincularView() {
        edt_titulo = findViewById(R.id.editTextTitulo);
        edt_rpe = findViewById(R.id.editTextRPE);
        btn_insertar = findViewById(R.id.button_insertar);
        btn_Cancelar = findViewById(R.id.button_Cancelar);
        //Creamo valores para el spinner
        String[] arreglo_tipo = {"Fuerza", "Aeróbico"};
        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arreglo_tipo);
        spinner_desplegable = findViewById(R.id.despegable);
        spinner_desplegable.setAdapter(adapter_spinner);
    }
}