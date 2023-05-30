package com.example.apprpe.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.apprpe.MainActivity;
import com.example.apprpe.R;
import com.example.apprpe.modelo.DB.RPERoomDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class Setting extends PreferenceFragmentCompat {

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    // Aquí puedes hacer algo cuando se pulse el botón atrás, o simplemente devolver true para evitar cerrar el fragmento
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferencias, rootKey);

        BottomNavigationView navView = requireActivity().findViewById(R.id.nav_view);
        navView.setVisibility(View.GONE);

        ActionBar actionBar = ((MainActivity) requireActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Ajustes");
        actionBar.setDisplayHomeAsUpEnabled(true); //botón atrás

        Preference mensajePreference = findPreference("acerca_de");
        assert mensajePreference != null;
        mensajePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                mostrarMensaje();
                return true;}
        });

        Preference resetPreference = findPreference("reset");
        assert resetPreference != null;
        resetPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                ConfirmacionDialog();
                return true;
            }
        });

        //Escala por defecto
        Preference escalaPreference = findPreference("escala");
        assert escalaPreference != null;
        if(Objects.requireNonNull(escalaPreference.getSharedPreferences()).getString("escala", "").equals("1")){
            SharedPreferences.Editor editor = escalaPreference.getSharedPreferences().edit();
            editor.putString("escala", "1");
            editor.apply();
        }

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_preferencias, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == android.R.id.home){
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

    }

    public void resetearDatos() {
        RPERoomDatabase.recreateDatabase(requireContext());
        RPERoomDatabase.getInstanceDatabase(requireContext());
    }

    public void ConfirmacionDialog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
        builder.setMessage("¿Está seguro? Se eliminarán todos los datos guardados y " +
                "se reestablecerán los datos predeterminados de ejemplo ");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                resetearDatos();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void mostrarMensaje() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Acerca de ...");
        builder.setMessage("Esta aplicación ha sido creada como Trabajo de Fin de Grado (TFG) " +
                "para obtener el título de Grado en Ingeniería Informática, desarrollado por Antonio Ariza García y dirigido por " +
                "Dr. Manuel Jesús Marín Jiménez y Dr. Nuria Marín Jiménez ");
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
