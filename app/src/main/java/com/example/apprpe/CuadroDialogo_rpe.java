package com.example.apprpe;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class CuadroDialogo_rpe extends DialogFragment{

    private RadioGroup radioGroup;
    private int rpe;
    private CuadroDialogo_listener listener;
    private RadioButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button10;

    @NonNull
    @Override
    public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.cuadrodialogo_rpe, null);
        radioGroup = view.findViewById(R.id.radioGroup2);

        dialogo.setView(view).
            setCancelable(false)
            .setTitle("Esfuerzo percibido (RPE)")
            .setMessage("¿Cómo de cansado te sientes?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(radioGroup.getCheckedRadioButtonId() == -1){
                        Toast.makeText(getContext(), "Selecciona algún item" , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        switch(radioGroup.getCheckedRadioButtonId()){
                            case 2131296819:
                                listener.apply_rpe(1);
                                break;
                            case 2131296821:
                                listener.apply_rpe(2);
                                break;
                            case 2131296822:
                                listener.apply_rpe(3);
                                break;
                            case 2131296823:
                                listener.apply_rpe(4);
                                break;
                            case 2131296824:
                                listener.apply_rpe(5);
                                break;
                            case 2131296825:
                                listener.apply_rpe(6);
                                break;
                            case 2131296826:
                                listener.apply_rpe(7);
                                break;
                            case 2131296827:
                                listener.apply_rpe(8);
                                break;
                            case 2131296828:
                                listener.apply_rpe(9);
                                break;
                            case 2131296820:
                                listener.apply_rpe(10);
                                break;
                        }
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

    public int getRadioButton(){
        return rpe;
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
}
