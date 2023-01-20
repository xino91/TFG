package com.example.apprpe.ui.Perfil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

import com.example.apprpe.Inicio_activity;
import com.example.apprpe.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.common.Feature;

import java.io.File;
import java.net.URI;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilFragment extends Fragment implements MenuProvider {

    SharedPreferences preferencias;
    TextView txtView_Usuario, txtView_Genero, txtView_Email;
    TextView txtView_Estatura, txtView_Peso, txtView_Nacimiento;
    String nombreUsuario;
    String genero;
    String estatura;
    String peso;
    String email;
    String tipo_actividad;
    String nacimiento;
    String path;
    CircleImageView imagen;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        requireActivity().addMenuProvider(this, getViewLifecycleOwner());

        inicializarComponenetes(root);
        obtenerDatosFicheroPreferencias();
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
        if(menuItem.getItemId() == R.id.action_editar){
            editarPerfil();
            return true;
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
        imagen = root.findViewById(R.id.profile_image);
    }

    private void obtenerDatosFicheroPreferencias() {
        preferencias = requireActivity().getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);

        nombreUsuario = preferencias.getString("NombreUsuario", "");
        genero = preferencias.getString("Genero", "");
        estatura = preferencias.getString("Estatura", "");
        peso = preferencias.getString("Peso", "");
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
                .galleryOnly()
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
            //Log.e("Hola2", uri.getEncodedPath());
        }
    }

    private void editarPerfil() {
        Intent intent = new Intent(getContext(), Inicio_activity.class);
        startActivity(intent);
    }

}
