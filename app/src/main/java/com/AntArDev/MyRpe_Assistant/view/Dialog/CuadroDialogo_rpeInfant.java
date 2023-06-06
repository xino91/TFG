package com.AntArDev.MyRpe_Assistant.view.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.apprpe.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CuadroDialogo_rpeInfant extends DialogFragment{

        private TextInputEditText rpe;
        private ImageView imageView;
        private CuadroDialogoInfant_listener listener;
        private ImageButton imageInfantButton;

        @NonNull
        @Override
        public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            AlertDialog.Builder dialogo = new AlertDialog.Builder(requireActivity());

            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.cuadrodialogo_rpeinfant, null);
            enlazarVistas(view);

            imageInfantButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarInfo();
                }
            });

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
            imageInfantButton = view.findViewById(R.id.imageinfant_info);
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

    public void mostrarInfo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Escala de Esfuerzo Percibido Infantil");
        builder.setMessage("La escala EPInfant es un instrumento desarrollado para  cuantificar  el  " +
                "esfuerzo  percibido  general  en la  población  infantil  con  base  en  un  " +
                "protocolo  de elaboración y validación de contenido.\n" +
                "Debe hacerse una inducción antes del inicio del ejercicio físico, explicándola en " +
                "términos sencillos apropiados para la edad cognitiva del sujeto." +
                "Si el niño o niña no sabe leer, se sugiere dar instrucciones para la interpretación " +
                "del esfuerzo percibido mediante las ilustraciones de niños haciendo ejercicio" + "A continuación se da un " +
                        "ejemplo de instrucción apropiada:\n\n" +
                "1- Antes, durante y después del ejercicio, te preguntaré cuán cansado te encuentras.\n\n" +
                "2- Debes utilizar los números, las palabras o los niños para indicarme tu nivel de " +
                "cansancio durante la actividad.\n\n" +
                "3- Observa al niño que se encuentra al inicio de la escala, si te sientes como él, significa que no te encuentras cansado.\n\n" +
                "4- Observa a los niños que se encuentran en el centro de la escala (niveles 5 y 6), si te sientes como ellos, significa que " +
                "te encuentras cansado pero puedes seguir haciendo ejercicio.\n\n" +
                "5- Observa al niño que se encuentra al final de la escala, si te sientes como él, significa que " +
                "te encuentras muy cansado y no puedes seguir haciendo ejercicio.\n\n" +
                "6- Puedes utilizar cualquiera de los números, frases o imágenes de niños de la escala, para decirme cuán " +
                "cansado te sientes. No existe una respuesta correcta o incorrecta");
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
