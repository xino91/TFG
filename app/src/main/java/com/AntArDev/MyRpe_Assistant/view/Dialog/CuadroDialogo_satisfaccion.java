package com.AntArDev.MyRpe_Assistant.view.Dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.apprpe.R;

public class CuadroDialogo_satisfaccion extends DialogFragment {

    private RadioGroup radioGroup;
    private SeekBar seekBar;
    private RadioButton boton1, boton2, boton3, boton4, boton5;
    private CuadroDialogo_listener listener;
    private int dolor;

    @NonNull
    @Override
    public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.cuadrodialogo_satisfaccion, null);
        enlazarVistas(view);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dolor = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        dialogo.setView(view).
                setCancelable(false)
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    if(radioGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getContext(), "Selecciona algún item", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Log.i("SATISFACCION", String.valueOf(radioGroup.getCheckedRadioButtonId()));
                        if(boton1.isChecked()) {listener.apply_Satisfaccion_Dolor(1,dolor);}
                        else if(boton2.isChecked()) {listener.apply_Satisfaccion_Dolor(2,dolor);}
                        else if(boton3.isChecked()) {listener.apply_Satisfaccion_Dolor(3,dolor);}
                        else if(boton4.isChecked()) {listener.apply_Satisfaccion_Dolor(4,dolor);}
                        else if(boton5.isChecked()) {listener.apply_Satisfaccion_Dolor(5,dolor);}
                    }
                })
                .setPositiveButtonIcon(requireActivity().getDrawable(R.drawable.ic_baseline_check_24))
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                    dismiss();
                })
                .setNegativeButtonIcon(requireActivity().getDrawable(R.drawable.ic_baseline_close_24));

        return dialogo.create();
    }

    public void enlazarVistas(View view){
        radioGroup = view.findViewById(R.id.radioGroup2);
        seekBar = view.findViewById(R.id.seekBar);
        boton1 = view.findViewById(R.id.radioButton1);
        boton2 = view.findViewById(R.id.radioButton2);
        boton3 = view.findViewById(R.id.radioButton3);
        boton4 = view.findViewById(R.id.radioButton4);
        boton5 = view.findViewById(R.id.radioButton5);
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
    }

    public void getSatisfaccion(CuadroDialogo_listener cuadrolistener){
        listener = cuadrolistener;
    }

    public interface CuadroDialogo_listener{
        void apply_Satisfaccion_Dolor(int satisfaccion, int dolor);
    }
}
