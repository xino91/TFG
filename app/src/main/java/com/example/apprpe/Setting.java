package com.example.apprpe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;
import java.util.Set;

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

    private void mostrarMensaje() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Acerca de ...");
        builder.setMessage("Esta aplicación ha sido creada como parte del Trabajo de Fin de Grado (TFG)" +
                "para la obtención del título de Grado en Ingeniería Informática. ");
        builder.setPositiveButton("OK", null);
        builder.show();
    }


}
