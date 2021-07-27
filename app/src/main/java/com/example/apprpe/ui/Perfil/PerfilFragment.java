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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        preferencias = requireActivity().getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);

        String nombreUsuario = preferencias.getString("NombreUsuario", "");
        String genero = preferencias.getString("Genero", "");
        String estatura = preferencias.getString("Estatura", "");
        String peso = preferencias.getString("Peso", "");
        String email = preferencias.getString("Email", "");
        String tipo_actividad = preferencias.getString("Actividad", "");

        txtView_Usuario = root.findViewById(R.id.textView_Nombre);
        txtView_Genero = root.findViewById(R.id.textView_Genero);
        txtView_Estatura = root.findViewById(R.id.textView_Estatura);
        txtView_Peso = root.findViewById(R.id.textView_Peso);
        txtView_Email = root.findViewById(R.id.textView_Email);
        txtView_Actividad = root.findViewById(R.id.textView_Actividad);

        txtView_Usuario.setText(nombreUsuario);
        txtView_Genero.setText(genero);
        txtView_Estatura.setText(estatura);
        txtView_Peso.setText(peso);
        txtView_Email.setText(email);
        txtView_Actividad.setText(tipo_actividad);

        return root;
    }
}
