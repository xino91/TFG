package com.example.apprpe;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.ui.EntrenamientoNAV.EntrenamientoViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class EditarEjercicio extends AppCompatActivity {

    Ejercicio ejercicio;
    EditText edt_Nombre, edt_Sets, edt_Repeticiones, edt_RPE;
    Button button_Cancelar, button_Editar;
    EntrenamientoViewModel entrenamientoViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ejercicio);

        entrenamientoViewModel = new ViewModelProvider(this).get(EntrenamientoViewModel.class);

        edt_Nombre = findViewById(R.id.editTextNombre_EjercicioEditar);
        edt_Sets = findViewById(R.id.editTextTNum_SetEditar);
        edt_Repeticiones = findViewById(R.id.editTextRepeticiones_editar);
        edt_RPE = findViewById(R.id.editTextRPEEjercicio_editar);
        button_Editar = findViewById(R.id.button_guardarEditar);
        button_Cancelar = findViewById(R.id.button_cancelarEditar);

        ejercicio = new Ejercicio();
        Bundle extras = getIntent().getExtras();
        ejercicio.setNombre(extras.getString("NombreEjercicio", ""));
        ejercicio.setSets(extras.getInt("Sets", 0));
        ejercicio.setRepeticiones(extras.getInt("Repeticiones", 0));
        ejercicio.setRpe(extras.getInt("RPE", 0));
        ejercicio.setId_Ejercicio(extras.getInt("ID_EJERCICIO", 0));
        ejercicio.setEntrenamiento_Id(extras.getInt("ID_SESION", 0));

        edt_Nombre.setText(ejercicio.getNombre());
        edt_Sets.setText(String.valueOf(ejercicio.getSets()));
        edt_Repeticiones.setText(String.valueOf(ejercicio.getRepeticiones()));
        edt_RPE.setText(String.valueOf(ejercicio.getRpe()));

        button_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        button_Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
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
            eliminarEjercicio();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void eliminarEjercicio() {
        entrenamientoViewModel.deleteEjercicio(ejercicio);
        entrenamientoViewModel.update_NumEjerciciosMenos(ejercicio.getEntrenamiento_Id());
        Toast.makeText(getApplicationContext(), "Eliminado con Éxito", Toast.LENGTH_SHORT).show();
        finish();
    }
}
