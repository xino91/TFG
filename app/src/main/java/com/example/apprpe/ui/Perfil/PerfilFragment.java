package com.example.apprpe.ui.Perfil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.apprpe.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PerfilFragment extends Fragment {

    SharedPreferences preferencias;
    TextView txtView_Usuario, txtView_Genero, txtView_Email;
    TextView txtView_Estatura, txtView_Peso, txtView_Nacimiento;
    TextView txtView_Actividad;
    String nombreUsuario;
    String genero;
    String estatura;
    String peso;
    String email;
    String tipo_actividad;
    String nacimiento;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        inicializarComponenetes(root);
        obtenerDatosFicheroPreferencias();
        vistas();

        return root;
    }

    private void inicializarComponenetes(View root) {

        txtView_Usuario = root.findViewById(R.id.textView_Nombre);
        txtView_Genero = root.findViewById(R.id.textView_Genero);
        txtView_Estatura = root.findViewById(R.id.textView_Estatura);
        txtView_Peso = root.findViewById(R.id.textView_Peso);
        txtView_Email = root.findViewById(R.id.textView_Email);
        txtView_Actividad = root.findViewById(R.id.textView_Actividad);
        txtView_Nacimiento = root.findViewById(R.id.TextView_fecha);
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
    }

    private void vistas(){
        txtView_Usuario.setText(nombreUsuario);
        txtView_Genero.setText(genero);
        txtView_Estatura.setText(estatura);
        txtView_Peso.setText(peso);
        txtView_Email.setText(email);
        txtView_Actividad.setText(tipo_actividad);
        txtView_Nacimiento.setText(nacimiento);
    }

}
