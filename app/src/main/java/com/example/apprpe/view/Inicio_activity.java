package com.example.apprpe.view;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.apprpe.MainActivity;
import com.example.apprpe.R;
import com.example.apprpe.modelo.Peso;
import com.example.apprpe.view.navBottom.EntrenamientoNAV.EntrenamientoViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Inicio_activity extends AppCompatActivity{

    SharedPreferences preferencias;
    String nombreUsuario, genero, email = "";
    String estatura, peso = "";
    String nacimiento;
    TextInputEditText edt_Nombre;
    RadioButton radbutton_masculino, radbutton_femenino;
    EditText edt_Estatura, edt_Peso, edt_Mail, buttonfecha;
    ImageButton imagebutton;
    Button buttonAvanzar;
    int dia,mes,ano;
    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private EntrenamientoViewModel entrenamientoViewModel;

    private Peso pesoAguardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        inicializarComponentes();

        entrenamientoViewModel = new ViewModelProvider(this).get(EntrenamientoViewModel.class);

        escuchadorCalendario();

        buttonAvanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogerDatos();
                if(comprobarDatosCorrectosEditText()) {
                    guardarPreferencias();
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    entrenamientoViewModel.insert(pesoAguardar);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    /**
     * Escuchador para el boton del calendario que abre un DatePickerDialog
     */
    public void escuchadorCalendario(){
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
                        buttonfecha.setText(year + "-" + String.format("%02d",(month + 1)) + "-" + String.format("%02d",dayOfMonth));
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(Inicio_activity.this, setlistener, ano, mes, dia);
                datePickerDialog.show();
            }
        });
    }

    /**
     * Vincula las vistas con las variables respectivas
     */
    private void inicializarComponentes(){
        edt_Nombre = findViewById(R.id.Edit_nombre);
        radbutton_masculino = findViewById(R.id.radioButton_masculino);
        radbutton_femenino = findViewById(R.id.radioButton_femenino);
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

    /**
     * Comprueba que los datos introducidos por el usuario sean correctos y también que tengamos Editext vacíos
     * @return true si todos los datos son correctos, false caso contrario
     */
    private boolean comprobarDatosCorrectosEditText(){
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
        } else if (edt_Peso.getText().length() != 4) {
            edt_Peso.setError("Campo obligatorio, el peso debe tener el formato xx.x\"");
            return false;
        } else if (!comprobarPeso()) {
            edt_Peso.setError("Campo obligatorio, el peso debe tener el formato xx.x");
            return false;
        } else if (nacimiento.isEmpty()) {
            buttonfecha.setError("Campo obligatorio");
            return false;
        } else if (!validarFecha(buttonfecha.getText().toString())) {
            buttonfecha.setError("La fecha");
            Toast.makeText(this, "La edad mínima es de 14 años", Toast.LENGTH_SHORT).show();
            return false;
        }  else if (email.isEmpty()) {
            edt_Mail.setError("Campo obligatorio");
            return false;
        } else if (!validarCorreo(edt_Mail.getText().toString())){
            edt_Mail.setError("Introduce un email válido");
            return false;
        }
        else {
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

    /**
     * Función que guardar los datos en el fichero de preferencias
     */
    private void guardarPreferencias(){
        preferencias = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString("NombreUsuario", nombreUsuario);
        editor.putString("Genero", genero);
        editor.putString("Estatura", estatura);
        editor.putString("Peso", peso);
        editor.putString("Email", email);
        editor.putString("Fecha", nacimiento);
        editor.apply();
    }

    /**
     * Esta función recibe una fechaNacimiento y comprueba que sea iniferior a la actual 14 años o más
     * @param fechanacimiento String Fecha de nacimiento
     * @return true, si la fechanacimiento es inferior, 14 o más años, false en caso contrario.
     */
    public  boolean validarFecha(String fechanacimiento) {
        LocalDate fechaNacimiento = LocalDate.parse(fechanacimiento, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate fechaActual = LocalDate.now();

        return fechaNacimiento.isBefore(fechaActual.minusYears(14));
    }

    /**
     * Valida un correo eléctronico mediante una expresión regular
     * @param correo de tipo string
     * @return true si cumple la expresión regular
     */
    public boolean validarCorreo(String correo){
        Matcher matcher = EMAIL_PATTERN.matcher(correo);
        return matcher.find();
    }

    public boolean comprobarPeso(){
        final String pesoStr = edt_Peso.getText().toString().trim();
        // Comprobamos que el valor tiene el formato correcto
        Pattern pattern = Pattern.compile("\\d{2}\\.\\d");
        Matcher matcher = pattern.matcher(pesoStr);
        if (!matcher.matches()) {
            return false;
        }
        // Convertimos el valor a un número decimal
        final double pesodouble = Double.parseDouble(pesoStr);
        // Comprobamos que el peso está dentro del rango de 0 a 150
        if (pesodouble < 0 || pesodouble > 150) {
            return false;
        }
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        pesoAguardar = new Peso(pesodouble, date);
        return true;
    }
}