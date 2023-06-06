package com.AntArDev.MyRpe_Assistant.view.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.apprpe.R;

public class CuadroDialogo_rpe extends DialogFragment{

    private RadioGroup radioGroup;
    private RadioButton boton0, boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8, boton9, boton10;
    private CuadroDialogo_listener listener;
    private ImageButton imageinfo;

    @NonNull
    @Override
    public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.cuadrodialogo_rpe, null);
        enlazarVistas(view);

        dialogo.setView(view).
            setCancelable(false)
            .setTitle("Esfuerzo percibido (RPE)")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(radioGroup.getCheckedRadioButtonId() == -1){
                        Toast.makeText(getContext(), "Selecciona algún item" , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(boton0.isChecked()){listener.apply_rpe(0);}
                        else if(boton1.isChecked()) {listener.apply_rpe(1);}
                        else if(boton2.isChecked()){listener.apply_rpe(2);}
                        else if(boton3.isChecked()){listener.apply_rpe(3);}
                        else if(boton4.isChecked()){listener.apply_rpe(4);}
                        else if(boton5.isChecked()){listener.apply_rpe(5);}
                        else if(boton6.isChecked()){listener.apply_rpe(6);}
                        else if(boton7.isChecked()){listener.apply_rpe(7);}
                        else if(boton8.isChecked()){listener.apply_rpe(8);}
                        else if(boton9.isChecked()){listener.apply_rpe(9);}
                        else if(boton10.isChecked()){listener.apply_rpe(10);}
                    }
                }
            }).setPositiveButtonIcon(requireActivity().getDrawable(R.drawable.ic_baseline_check_24));
            dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }).setNegativeButtonIcon(requireActivity().getDrawable(R.drawable.ic_baseline_close_24));

        imageinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarInfo();
            }
        });

        return dialogo.create();
    }

    public void enlazarVistas(View view){
        radioGroup = view.findViewById(R.id.radioGroup1);
        boton0 = view.findViewById(R.id.radioButton0);
        boton1 = view.findViewById(R.id.radioButton1);
        boton2 = view.findViewById(R.id.radioButton2);
        boton3 = view.findViewById(R.id.radioButton3);
        boton4 = view.findViewById(R.id.radioButton4);
        boton5 = view.findViewById(R.id.radioButton5);
        boton6 = view.findViewById(R.id.radioButton6);
        boton7 = view.findViewById(R.id.radioButton7);
        boton8 = view.findViewById(R.id.radioButton8);
        boton9 = view.findViewById(R.id.radioButton9);
        boton10 = view.findViewById(R.id.radioButton10);
        imageinfo = view.findViewById(R.id.image_info);
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
    }

    public void getRPE(CuadroDialogo_listener cuadrolistener){
        listener = cuadrolistener;
    }

    public interface CuadroDialogo_listener{
        void apply_rpe(int rpe);
    }

    public void mostrarInfo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Escala de Esfuerzo Percibido (RPE)");
        builder.setMessage("Es una medida subjetiva utilizada en el ámbito del deporte y la actividad " +
                "física para evaluar la intensidad percibida del esfuerzo durante el ejercicio." +
                "Se utiliza comúnmente en programas de entrenamiento, tanto para deportistas profesionales " +
                        "como para personas que realizan ejercicio regularmente, como una forma de autoregulación " +
                        "y control de la intensidad del entrenamiento.");
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
