package com.AntArDev.MyRpe_Assistant.view;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.AntArDev.MyRpe_Assistant.modelo.Ejercicio;
import com.AntArDev.MyRpe_Assistant.view.navBottom.EntrenamientoNAV.EntrenamientoViewModel;
import com.example.apprpe.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class EditarEjercicio extends AppCompatActivity {

    Ejercicio ejercicio;
    TextInputLayout l_set, l_rep;
    TextInputEditText edt_Nombre, edt_Sets, edt_Repeticiones, edt_RPE;
    Button button_Cancelar, button_Editar;
    EntrenamientoViewModel entrenamientoViewModel;
    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_editar_ejercicio);

        entrenamientoViewModel = new ViewModelProvider(this).get(EntrenamientoViewModel.class);
        VincularVistas();
        RecuperarDatos();
        SetVistas();
        EscuchadorBotonCancelar();
        EscuchadorBotonGuardar();
    }

    /**
     * Escuchador del botón guardar edición, comprobara que todos los datos sean correctos y ningún campo sea vacío
     */
    private void EscuchadorBotonGuardar() {
        button_Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comprobarDatos()){
                    if(Objects.equals(tipo, "Fuerza")){
                    if(TextUtils.isEmpty(edt_Nombre.getText()) || TextUtils.isEmpty(edt_Repeticiones.getText()) ||
                            TextUtils.isEmpty(edt_Sets.getText()) || TextUtils.isEmpty(edt_RPE.getText())) {
                        Toast.makeText(getApplicationContext(), "Error, Ningún Campo puede ser vacío", Toast.LENGTH_SHORT).show();
                        finish();
                    } else{
                        Toast.makeText(getApplicationContext(), "Editado con éxito", Toast.LENGTH_SHORT).show();
                        ejercicio.setNombre(String.valueOf(edt_Nombre.getText()));
                        ejercicio.setSets(Integer.parseInt(String.valueOf(edt_Sets.getText())));
                        ejercicio.setRepeticiones(Integer.parseInt(String.valueOf(edt_Repeticiones.getText())));
                        ejercicio.setRpe(Integer.parseInt(String.valueOf(edt_RPE.getText())));
                        entrenamientoViewModel.updateEjercicio(ejercicio);
                    }
                    finish();
                }
                else { //AEROBICO
                    if(TextUtils.isEmpty(edt_Nombre.getText()) || TextUtils.isEmpty(edt_RPE.getText())) {
                        Toast.makeText(getApplicationContext(), "Error, Ningún Campo puede ser vacío", Toast.LENGTH_SHORT).show();
                        finish();
                    } else{
                        Toast.makeText(getApplicationContext(), "Editado con éxito", Toast.LENGTH_SHORT).show();
                        ejercicio.setNombre(String.valueOf(edt_Nombre.getText()));
                        ejercicio.setSets(Integer.parseInt(String.valueOf(edt_Sets.getText())));
                        ejercicio.setDuracion(Integer.parseInt(String.valueOf(edt_Repeticiones.getText()))); //durancion
                        ejercicio.setRepeticiones(0);
                        ejercicio.setRpe(Integer.parseInt(String.valueOf(edt_RPE.getText())));
                        entrenamientoViewModel.updateEjercicio(ejercicio);
                    }
                    finish();
                }}

            }
        });
    }

    private void EscuchadorBotonCancelar() {
        button_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void SetVistas(){
        if(Objects.equals(tipo, "Fuerza")){
            edt_Nombre.setText(ejercicio.getNombre());
            edt_Sets.setText(String.valueOf(ejercicio.getSets()));
            edt_Repeticiones.setText(String.valueOf(ejercicio.getRepeticiones()));
            edt_RPE.setText(String.valueOf(ejercicio.getRpe()));
        }
        else{
            l_rep.setHint("Duración en minutos");
            edt_Nombre.setText(ejercicio.getNombre());
            edt_Sets.setText(String.valueOf(ejercicio.getSets()));
            edt_Repeticiones.setText(String.valueOf(ejercicio.getDuracion())); //Duracion
            edt_RPE.setText(String.valueOf(ejercicio.getRpe()));
        }
    }


    /**
     * Recupera los datos recibidos del activity anterior
     */
    private void RecuperarDatos() {
        ejercicio = new Ejercicio();
        Bundle extras = getIntent().getExtras();
        ejercicio.setNombre(extras.getString("NombreEjercicio", ""));
        ejercicio.setSets(extras.getInt("Sets", 0));
        ejercicio.setRepeticiones(extras.getInt("Repeticiones", 0));
        ejercicio.setDuracion((extras.getInt("Duracion", 0)));
        ejercicio.setRpe(extras.getInt("RPE", 0));
        ejercicio.setId_Ejercicio(extras.getInt("ID_EJERCICIO", 0));
        ejercicio.setEntrenamiento_Id(extras.getInt("ID_SESION", 0));
        tipo = extras.getString("TIPO", "");
    }

    /**
     * Vincula las View a UI
     */
    private void VincularVistas() {
        edt_Nombre = findViewById(R.id.editTextNombre_EjercicioEditar);
        edt_Sets = findViewById(R.id.editTextTNum_SetEditar);
        edt_Repeticiones = findViewById(R.id.editTextRepeticiones_editar);
        edt_RPE = findViewById(R.id.editTextRPEEjercicio_editar);
        button_Editar = findViewById(R.id.button_guardarEditar);
        button_Cancelar = findViewById(R.id.button_cancelarEditar);
        l_set = findViewById(R.id.textInputLayout_sets);
        l_rep = findViewById(R.id.textInputLayout_repeticiones);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vista_edicion, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.eliminar_ejercicio) {
            dialogoConfirmacionDelete();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Función para eliminar un ejercicio
     */
    private void eliminarEjercicio() {
        entrenamientoViewModel.deleteEjercicio(ejercicio);
        Toast.makeText(getApplicationContext(), "Eliminado con Éxito", Toast.LENGTH_SHORT).show();
        finish();
    }
    /**
     * Función que muestra un dialogAlert para la confirmación del botón eliminar ejercicio
     */
    public void dialogoConfirmacionDelete(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.DialogBasico);
        alertDialog.setTitle("Confirmación");
        alertDialog.setMessage("¿Está seguro que desea eliminar el ejercicio?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarEjercicio();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    public boolean comprobarDatos() {
        if(Objects.equals(tipo, "Fuerza")){
            if (TextUtils.isEmpty(edt_Nombre.getText())) {
                edt_Nombre.setError("Campo obligatorio");
                return false;
            } else if (TextUtils.isEmpty(edt_Sets.getText()) || (Integer.parseInt((Objects.requireNonNull(edt_Sets.getText())).toString()) > 8) || (Integer.parseInt((Objects.requireNonNull(edt_Sets.getText().toString()))) == 0)){
                edt_Sets.setError("Campo obligatorio, comprendido entre 1 y 8");
                return false;
            } else if(TextUtils.isEmpty(edt_Repeticiones.getText()) || (Integer.parseInt(Objects.requireNonNull(edt_Repeticiones.getText()).toString())) >=100 || (Integer.parseInt((Objects.requireNonNull(edt_Repeticiones.getText())).toString()) == 0)){
                edt_Repeticiones.setError("Campo obligatorio, comprendido entre 1 y 100");
                return false;
            } else if (TextUtils.isEmpty(edt_RPE.getText()) || (Integer.parseInt((Objects.requireNonNull(edt_RPE.getText())).toString()) > 10) || (Integer.parseInt((edt_RPE.getText()).toString()) < 0)) {
                edt_RPE.setError("Campo Obligatorio, valor comprendido entre 0 y 10");
                return false;
            }
            return true;
        }
        else{
            if (TextUtils.isEmpty(edt_Nombre.getText())) {
                edt_Nombre.setError("Campo obligatorio");
                return false;
            } else if (TextUtils.isEmpty(edt_Sets.getText()) || (Integer.parseInt((Objects.requireNonNull(edt_Sets.getText())).toString()) > 8) || (Integer.parseInt((Objects.requireNonNull(edt_Sets.getText().toString()))) <= 0)) {
                edt_Sets.setError("Campo obligatorio, comprendido entre 1 y 8");
                return false;
            } else if (TextUtils.isEmpty(edt_Repeticiones.getText()) || (Integer.parseInt((Objects.requireNonNull(edt_Repeticiones.getText())).toString()) < 1) || (Integer.parseInt((Objects.requireNonNull(edt_Repeticiones.getText().toString()))) > 300)) {
                edt_Repeticiones.setError("Campo obligatorio, comprendido entre 1 y 300");
                return false;
            } else if (TextUtils.isEmpty(edt_RPE.getText()) || (Integer.parseInt((Objects.requireNonNull(edt_RPE.getText())).toString()) >= 10) || (Integer.parseInt((edt_RPE.getText()).toString()) < 0)) {
                edt_RPE.setError("Campo Obligatorio, valor comprendido entre 0 y 10");
                return false;
            }
            return true;
        }
    }
}
