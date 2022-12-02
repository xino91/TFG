package com.example.apprpe.ui.ProgresoNAV;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.apprpe.R;
import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.modelo.Ent_RealizadoDao;
import com.example.apprpe.modelo.Entrenamiento;
import com.example.apprpe.ui.EntRealizadoNAV.EntRealizadoViewModel;
import com.example.apprpe.ui.EntrenamientoNAV.EntrenamientoViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProgresoFragment extends Fragment {

    private ProgresoViewModel progresoViewModel;
    private List<Entrenamiento> list = new ArrayList<>();
    //private EntRealizadoViewModel entRealizadoViewModel;
    //private EntrenamientoViewModel entrenamientoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_progreso, container, false);
        progresoViewModel = new ViewModelProvider(this).get(ProgresoViewModel.class);

        LineChart lineChart;
        lineChart = root.findViewById(R.id.lineChart);

        progresoViewModel.getAllEntrenamientos().observe(getViewLifecycleOwner(), new Observer<List<Entrenamiento>>() {
            @Override
            public void onChanged(List<Entrenamiento> entrenamientos) {

                for(int i=0; i<entrenamientos.size(); i++){
                    list.addAll(entrenamientos);
                    Log.i("LIST", String.valueOf(list.get(i).getRpe_Sesion()));
                }
                List<Entry> entradas = new ArrayList<>();

                for(int i=0; i<entrenamientos.size(); i++){
                    entradas.add(new Entry(list.get(i).getRpe_Sesion(), list.get(i).getRpe_Sesion()));
                }

                LineDataSet lineDataSet = new LineDataSet(entradas, "RPE");
                ArrayList<ILineDataSet> dataset = new ArrayList<>();
                dataset.add(lineDataSet);

                LineData data = new LineData(dataset);
                lineChart.setData(data);
                lineChart.invalidate();
            }
        });

        return root;
    }


}