package com.example.apprpe.view.navBottom.Perfil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.text.LineBreakConfig;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.apprpe.modelo.Peso;
import com.example.apprpe.view.Inicio_activity;
import com.example.apprpe.view.Setting;
import com.example.apprpe.R;
import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.view.navBottom.EntRealizadoNAV.EntRealizadoViewModel;
import com.example.apprpe.view.navBottom.EntrenamientoNAV.EntrenamientoViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilFragment extends Fragment implements MenuProvider {

    private SharedPreferences preferencias;
    private TextView txtView_Usuario, txtView_Genero, txtView_Email;
    private TextView txtView_Estatura, txtView_Peso, txtView_Nacimiento, txtView_peso_maximo, txtView_peso_minimo, textView_nent;
    private String nombreUsuario, genero, estatura, peso, email, tipo_actividad, nacimiento;
    private String path;
    private CircleImageView imagen;
    private BottomNavigationView navView;
    private EntrenamientoViewModel entrenamientoViewModel;
    private EntRealizadoViewModel entRealizadoViewModel;
    private int numero_ejercicios;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        requireActivity().addMenuProvider(this, getViewLifecycleOwner());

        inicializarComponenetes(root);
        entrenamientoViewModel = new ViewModelProvider(this).get(EntrenamientoViewModel.class);
        entRealizadoViewModel = new ViewModelProvider(this).get(EntRealizadoViewModel.class);
        getPesoActual();
        getPesoMaximo();
        getPesoMinimo();
        obtenerDatosFicheroPreferencias();
        getNumeroEntrenamientos();
        vistas();

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elegirImagen();
            }
        });

        return root;
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_perfil, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == android.R.id.home){
            requireActivity().getSupportFragmentManager().popBackStack();
            requireActivity().setContentView(R.layout.activity_main);
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_progreso, R.id.navigation_estadisticas, R.id.navigation_perfil)
                    .build();

            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity(), navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);
            return true;
        }
        if (menuItem.getItemId() == R.id.action_configuracion) {
            editarConfiguracion();
            return true;
        }
        if(menuItem.getItemId() == R.id.action_editar){
            editarPerfil();
            return true;
        }
        if(menuItem.getItemId() == R.id.nuevo_peso){
            nuevoPeso();
            return true;
        }
        if(menuItem.getItemId() == R.id.action_exportar){
            if(numero_ejercicios > 0) {
                return AbrirDialog();
            }
            else{
                Toast.makeText(requireContext(), "Sin datos que exportar", Toast.LENGTH_SHORT).show();
            }

        }
        return false;
    }

    private void inicializarComponenetes(View root) {
        txtView_Usuario = root.findViewById(R.id.textView_Nombre);
        txtView_Genero = root.findViewById(R.id.textView_Genero);
        txtView_Estatura = root.findViewById(R.id.textView_Estatura);
        txtView_Peso = root.findViewById(R.id.textView_Peso);
        txtView_Email = root.findViewById(R.id.textView_Email);
        txtView_Nacimiento = root.findViewById(R.id.TextView_fecha);
        txtView_peso_maximo = root.findViewById(R.id.View_pesomaximo);
        txtView_peso_minimo = root.findViewById(R.id.View_pesominimo);
        textView_nent = root.findViewById(R.id.view_nent);
        imagen = root.findViewById(R.id.profile_image);
        navView = root.findViewById(R.id.nav_view);
    }

    private void obtenerDatosFicheroPreferencias() {
        preferencias = requireActivity().getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);

        nombreUsuario = preferencias.getString("NombreUsuario", "");
        genero = preferencias.getString("Genero", "");
        estatura = preferencias.getString("Estatura", "");
        email = preferencias.getString("Email", "");
        tipo_actividad = preferencias.getString("Actividad", "");
        nacimiento = preferencias.getString("Fecha", "");
        path = preferencias.getString("Path","");
    }

    private void vistas(){
        txtView_Usuario.setText(nombreUsuario);
        txtView_Genero.setText(genero);
        txtView_Estatura.setText(estatura);
        txtView_Peso.setText(peso);
        txtView_Email.setText(email);
        txtView_Nacimiento.setText(nacimiento);
        CargarImagen();
    }

    private void CargarImagen() {
        if(path.isEmpty()){
            imagen.setImageResource(R.mipmap.perfil_pordefecto);
            imagen.setBorderColor(Color.BLACK);
        }
        else {
            File file = new File(path);
            imagen.setImageURI(Uri.fromFile(file));
        }
    }

    private void elegirImagen() {
        ImagePicker.Companion.with(this)
                .crop()
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            Uri uri = Objects.requireNonNull(data).getData();
            imagen.setImageURI(uri);

            preferencias = requireActivity().getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putString("Path", uri.getEncodedPath());
            editor.apply();
        }
    }

    private void editarPerfil() {
        Intent intent = new Intent(getContext(), Inicio_activity.class);
        startActivity(intent);
    }

    private void editarConfiguracion() {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new Setting())
                .addToBackStack(null)
                .commit();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        BottomNavigationView navView = requireActivity().findViewById(R.id.nav_view);
        navView.setVisibility(View.VISIBLE);
    }

    public void getPesoMaximo(){
        entrenamientoViewModel.getPesoMaximo().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float pesoMax) {
                txtView_peso_maximo.setText(String.valueOf(pesoMax));
            }
        });
    }

    public void getPesoMinimo(){
        entrenamientoViewModel.getPesoMinimo().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float pesoMin) {
                txtView_peso_minimo.setText(String.valueOf(pesoMin));
            }
        });
    }

    public void getPesoActual(){
        entrenamientoViewModel.getPesoActual().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float pesoActual) {
                txtView_Peso.setText(String.valueOf(pesoActual));
            }
        });
    }

    public void getNumeroEntrenamientos(){
        entRealizadoViewModel.getCountEntrenamientosRealizados().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                numero_ejercicios = integer;
                textView_nent.setText(String.valueOf(integer));
            }
        });
    }

    /**
     * Crea una hoja de excel que rellena con los datos de Entrenamientos Realizados y llama enviarEmailConArchivoAdjunto
     */
    private void exportarDatosExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet hoja = workbook.createSheet("Entrenamientos Realizados");
        PrepararCabeceraExcel(workbook,hoja);
        entRealizadoViewModel.getAllEntrenamientosRealizados().observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
            @Override
            public void onChanged(List<Ent_Realizado> entList) {
                //Rellenamos datos
                for(int i=0; i < entList.size(); i++){
                    Row row = hoja.createRow(i+1);
                    createCell(workbook, row, 0, entList.get(i).getNombre_entrenamiento(), HorizontalAlignment.LEFT);
                    createCell(workbook, row, 1, entList.get(i).getTipo(), HorizontalAlignment.LEFT);
                    createCell(workbook, row, 2, entList.get(i).getFechaString(), HorizontalAlignment.CENTER);
                    createCell(workbook, row, 3, (int) entList.get(i).getDuracion(), HorizontalAlignment.RIGHT);
                    createCell(workbook, row, 4, entList.get(i).getHora_inicio(), HorizontalAlignment.RIGHT);
                    createCell(workbook, row, 5, entList.get(i).getHora_finalizacion(), HorizontalAlignment.RIGHT);
                    createCell(workbook, row, 6, entList.get(i).getSatisfaccion(), HorizontalAlignment.RIGHT);
                    createCell(workbook, row, 7, entList.get(i).getDolor(), HorizontalAlignment.RIGHT);
                    createCell(workbook, row, 8, entList.get(i).getCarga(), HorizontalAlignment.RIGHT);
                    createCell(workbook, row, 9, entList.get(i).getRpe_objetivo(), HorizontalAlignment.RIGHT);
                    createCell(workbook, row, 10, entList.get(i).getRpe_subjetivo(), HorizontalAlignment.RIGHT);
                }

                // Crear un objeto File para el archivo de destino
                File file = new File(requireView().getContext().getExternalFilesDir(null),
                        "Entrenamientos Realizados.xlsx");
                // Crear un flujo de salida para el archivo
                FileOutputStream fos = null;
                try {fos = new FileOutputStream(file);} catch (FileNotFoundException e) {throw new RuntimeException(e);}
                try {workbook.write(fos);} catch (IOException e) {throw new RuntimeException(e);}
                try {fos.close();} catch (IOException e) {throw new RuntimeException(e);}
                try {workbook.close();} catch (IOException e) {throw new RuntimeException(e);}

                enviarEmailConArchivoAdjunto(file);
            }
        });
    }

    /**
     * Es llamada desde ExportarDatosExcel, está función rellena la cabecera del excel con los títulos de las columnas,
     * para ello llama a la función createCell
     * @param wb es el Workbook
     * @param hoja es la hoja (Sheet)
     */
    public void PrepararCabeceraExcel(Workbook wb, Sheet hoja){
        Row row = hoja.createRow(0);
        createCell(wb,row,0, "Nombre entrenamiento", HorizontalAlignment.LEFT);
        hoja.setColumnWidth(0, 7000);
        createCell(wb,row,1,"Tipo", HorizontalAlignment.LEFT);
        createCell(wb,row,2,"Fecha", HorizontalAlignment.CENTER);
        hoja.setColumnWidth(2, 3000);
        createCell(wb,row,3,"Duración", HorizontalAlignment.RIGHT);
        createCell(wb,row,4,"Hora inicio", HorizontalAlignment.RIGHT);
        hoja.setColumnWidth(4, 3000);
        createCell(wb,row,5,"Hora finalización", HorizontalAlignment.RIGHT);
        hoja.setColumnWidth(5, 4000);
        createCell(wb,row,6,"Satisfacción", HorizontalAlignment.RIGHT);
        hoja.setColumnWidth(6, 3000);
        createCell(wb,row,7,"Dolor", HorizontalAlignment.RIGHT);
        createCell(wb,row,8,"Carga", HorizontalAlignment.RIGHT);
        createCell(wb,row,9,"Rpe objetivo", HorizontalAlignment.RIGHT);
        hoja.setColumnWidth(9, 3000);
        createCell(wb,row,10,"Rpe subjetivo", HorizontalAlignment.RIGHT);
        hoja.setColumnWidth(10, 3000);
    }

    /**
     * Función utilizado por crearCabeceraExcel, también se puede utilizar en cualquier momento para añadir texto a una celda
     * y modificar su estilo de HorizontalAlignment
     * @param wb, Workbook
     * @param row de tipo Row, es la fila
     * @param column de tipo int, es la columna
     * @param dato de tipo String, es el texto que se escribira en la celda [row,column]
     * @param halign de tipo HorizontalAlignment, para indicar la alineación, CENTER, LIGHT, RIGHT ...
     */
    private static void createCell(Workbook wb, Row row, int column, String dato, HorizontalAlignment halign) {
        Cell cell = row.createCell(column);
        cell.setCellValue(dato);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
    }

    /**
     * Función utilizado por crearCabeceraExcel, también se puede utilizar en cualquier momento para añadir texto a una celda
     * y modificar su estilo de HorizontalAlignment
     * @param wb, Workbook
     * @param row de tipo Row, es la fila
     * @param column de tipo int, es la columna
     * @param dato de tipo int, es el texto que se escribira en la celda [row,column]
     * @param halign de tipo HorizontalAlignment, para indicar la alineación, CENTER, LIGHT, RIGHT ...
     */
    private static void createCell(Workbook wb, Row row, int column, int dato, HorizontalAlignment halign) {
        Cell cell = row.createCell(column);
        cell.setCellValue(dato);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
    }

    /**
     * Mando por correo eléctrico el excel que se crear al exportar datos, esta función es llamada
     * en exportarDatosExcel()
     * @param archivo de tipo File, que representa el archivo que se desea adjuntar al correo
     */
    private void enviarEmailConArchivoAdjunto(File archivo) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"antonio_aariza@hotmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Entrenamientos Realizados");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Cuerpo del correo");

        // Adjuntar el archivo al correo
        Uri archivoUri = FileProvider.getUriForFile(requireContext(), requireActivity().getPackageName() + ".provider", archivo);
        emailIntent.putExtra(Intent.EXTRA_STREAM, archivoUri);

        PackageManager packageManager = requireActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(emailIntent, 0);
        boolean isIntentSafe = activities.size() > 0;
        if (isIntentSafe) {
            startActivity(emailIntent);
        }
    }


    public boolean AbrirDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Se Exportarán los entrenamientos realizados a excel y se mandarán por correo electrónico");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                exportarDatosExcel();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
        return true;
    }

    public void nuevoPeso(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Introduce tu peso actual");

        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(5) });
        builder.setView(input);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                final String pesoStr = input.getText().toString().trim();
                // Comprobamos que el valor tiene el formato correcto
                Pattern pattern = Pattern.compile("\\d{2}\\.\\d");
                Matcher matcher = pattern.matcher(pesoStr);
                if (!matcher.matches()) {
                    Toast.makeText(requireContext(), "El peso debe tener el formato xx.x", Toast.LENGTH_LONG).show();
                    return;
                }
                // Convertimos el valor a un número decimal
                final double pesodouble = Double.parseDouble(pesoStr);
                // Comprobamos que el peso está dentro del rango de 0 a 150
                if (pesodouble < 0 || pesodouble > 150) {
                    Toast.makeText(requireContext(), "El peso debe estar entre 0 y 150", Toast.LENGTH_SHORT).show();
                    return;
                }
                Date date = new Date(Calendar.getInstance().getTimeInMillis());
                Peso peso = new Peso(pesodouble, date);
                entrenamientoViewModel.insert(peso);
                Toast.makeText(requireContext(), "Peso guardado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
