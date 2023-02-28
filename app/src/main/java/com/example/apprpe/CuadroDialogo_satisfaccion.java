package com.example.apprpe;

import android.content.DialogInterface;
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

public class CuadroDialogo_satisfaccion extends DialogFragment {

    private RadioGroup radioGroup;
    private SeekBar seekBar;
    private CuadroDialogo_listener listener;
    private int dolor;

    @NonNull
    @Override
    public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.cuadrodialogo_satisfaccion, null);
        radioGroup = view.findViewById(R.id.radioGroup2);
        seekBar = view.findViewById(R.id.seekBar);

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
                        switch(radioGroup.getCheckedRadioButtonId()) {
                            case 2131296819:
                                listener.apply_satisfaccion(1, dolor);
                                break;
                            case 2131296821:
                                listener.apply_satisfaccion(2, dolor);
                                break;
                            case 2131296822:
                                listener.apply_satisfaccion(3, dolor);
                                break;
                            case 2131296823:
                                listener.apply_satisfaccion(4, dolor);
                                break;
                            case 2131296824:
                                listener.apply_satisfaccion(5, dolor);
                                break;
                        }
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

    private void apply_satisfaccion(int i) {
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
    }

    public void getSatisfaccion(CuadroDialogo_listener cuadrolistener){
        listener = cuadrolistener;
    }

    public interface CuadroDialogo_listener{
        void apply_satisfaccion(int satisfaccion, int dolor);
    }
}
