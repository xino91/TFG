package com.AntArDev.MyRpe_Assistant.view;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.AntArDev.MyRpe_Assistant.MainActivity;
import com.AntArDev.MyRpe_Assistant.modelo.Peso;
import com.AntArDev.MyRpe_Assistant.view.navBottom.EntrenamientoNAV.EntrenamientoViewModel;
import com.example.apprpe.R;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Inicio_activity extends AppCompatActivity{

    SharedPreferences preferencias;
    String nombreUsuario, genero, email = "";
    String estatura, peso, pesoanterior = "";
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
        enlazaVistas();
        if(comprobarExistePreferencias()){
            recuperarDatosPreferencias();
            rellenamosVistas();
        }
        escuchadorCalendario();

        buttonAvanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogerDatos();
                if(comprobarDatosCorrectosEditText()) {
                    guardarPreferencias();
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    guardarPeso();
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
    private void enlazaVistas(){
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
        } else if (!comprobarPeso()) {
            edt_Peso.setError("Campo obligatorio, el peso debe tener el formato xx.x");
            return false;
        } else if (nacimiento.isEmpty()) {
            buttonfecha.setError("Año-Mes-Día-Formato: yyyy-mm-dd");
            return false;
        } else if (!validarFecha(buttonfecha.getText().toString())) {
            buttonfecha.setError("Año-Mes-Día-Formato: yyyy-mm-dd");
            Toast.makeText(this, "Introduce una fecha correcta", Toast.LENGTH_SHORT).show();
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
     * Esta función recibe una fechaNacimiento y comprueba si es válida, su formato debe ser yyyy-MM-dd, edad mínima 14 años
     * edad máxima 80 años, también comprueba que la fecha no sea superior a la actual
     * @param fechanacimiento String Fecha de nacimiento
     * @return true, si la fechanacimiento es inferior, 14 o más años, false en caso contrario.
     */
    public  boolean validarFecha(String fechanacimiento) {

        // Definir el formato de la fecha esperada
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        formatoFecha.setLenient(false); // No permitir fechas ambiguas

        try {
            // Intentar analizar la fecha ingresada
            java.sql.Date fecha = new java.sql.Date(Objects.requireNonNull(formatoFecha.parse(fechanacimiento)).getTime());
            String fechaFormateada = formatoFecha.format(fecha);
            if (!fechaFormateada.equals(fechanacimiento)) {
                Toast.makeText(getApplicationContext(), "La fecha no cumple el formato esperado", Toast.LENGTH_SHORT).show();
                return false;
            }

            // Comprobar si la fecha es mayor que la actual
            Calendar calendarioActual = Calendar.getInstance();
            Calendar calendarioIngresado = Calendar.getInstance();
            calendarioIngresado.setTime(fecha);

            /*Calendar calendarioMinimaEdad = Calendar.getInstance();
            calendarioMinimaEdad.add(Calendar.YEAR, -14); // Edad Mínima: 14 años
            if(!calendarioIngresado.before(calendarioMinimaEdad)){
                Toast.makeText(getApplicationContext(), "La edad mínima es de 14 años", Toast.LENGTH_SHORT).show();
                return false;
            }*/
            if (calendarioIngresado.after(calendarioActual)) {
                Toast.makeText(getApplicationContext(), "La fecha es mayor que la actual", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                // Comprobar si la fecha es muy antigua
                Calendar calendarioLimite = Calendar.getInstance();
                calendarioLimite.add(Calendar.YEAR, -80); // Fecha límite: hace 80 años

                if (calendarioIngresado.before(calendarioLimite)) {
                    Toast.makeText(getApplicationContext(), "La fecha es muy antigua, límite 80 años", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    return true;
                }
            }
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), "No se cumple el formato esperado", Toast.LENGTH_SHORT).show();
            return false;
        }
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
        Pattern pattern2 = Pattern.compile("\\d{2}");
        Matcher matcher = pattern.matcher(pesoStr);
        Matcher matcher2 = pattern2.matcher(pesoStr);
        if (matcher.matches() || matcher2.matches()){
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
        return false;
    }

    public void guardarPeso(){
        entrenamientoViewModel = new ViewModelProvider(this).get(EntrenamientoViewModel.class);
        if(!Objects.equals(pesoanterior, peso)){
            entrenamientoViewModel.insert(pesoAguardar);
        }
    }

    public boolean comprobarExistePreferencias(){
        SharedPreferences sharedPreferences = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        return sharedPreferences.contains("NombreUsuario");
    }

    public void recuperarDatosPreferencias(){
        SharedPreferences sharedPreferences = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        nombreUsuario = sharedPreferences.getString("NombreUsuario", "");
        genero = sharedPreferences.getString("Genero", "");
        estatura = sharedPreferences.getString("Estatura", "");
        pesoanterior = sharedPreferences.getString("Peso", "");
        peso = pesoanterior;
        email = sharedPreferences.getString("Email", "");
        nacimiento = sharedPreferences.getString("Fecha", "");
    }

    public void rellenamosVistas(){
        edt_Nombre.setText(nombreUsuario);
        if(Objects.equals(genero, "Masculino")){radbutton_masculino.setChecked(true);}
        else{radbutton_femenino.setChecked(true);}
        edt_Estatura.setText(estatura);
        edt_Peso.setText(peso);
        edt_Mail.setText(email);
        buttonfecha.setText(nacimiento);
    }
}