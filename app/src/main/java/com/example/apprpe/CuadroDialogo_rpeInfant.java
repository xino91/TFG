package com.example.apprpe;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CuadroDialogo_rpeInfant extends DialogFragment{

        private TextInputEditText rpe;
        private ImageView imageView;
        private CuadroDialogoInfant_listener listener;

        @NonNull
        @Override
        public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            AlertDialog.Builder dialogo = new AlertDialog.Builder(requireActivity());

            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.cuadrodialogo_rpeinfant, null);
            enlazarVistas(view);

            dialogo.setView(view).
                    setCancelable(false)
                    .setTitle("Esfuerzo percibido escala infantil")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(Objects.equals(Objects.requireNonNull(rpe.getText()).toString(), "")){
                                Toast.makeText(getContext(), "Introduce el valor de RPE percibido" , Toast.LENGTH_SHORT).show();
                            }
                            else if(Integer.parseInt(rpe.getText().toString()) < 0 ||
                                    Integer.parseInt(rpe.getText().toString()) > 10 ){
                                Toast.makeText(getContext(), "Selecciona un valor del 0 al 10" , Toast.LENGTH_SHORT).show();
                            }
                            else{
                                listener.apply_rpeinfant(Integer.parseInt(String.valueOf(rpe.getText())));
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

            return dialogo.create();
        }

        //public int getRadioButton(){return rpe;}

        public void enlazarVistas(View view){
            imageView = view.findViewById(R.id.image_rpeinfant);
            rpe = view.findViewById(R.id.rpe);
        }

        @Override
        public void setCancelable(boolean cancelable) {
            super.setCancelable(cancelable);
        }

        public void getRPE(CuadroDialogoInfant_listener cuadrolistener){
            listener = cuadrolistener;
        }

        public interface CuadroDialogoInfant_listener{
            void apply_rpeinfant(int rpe);
        }
}
