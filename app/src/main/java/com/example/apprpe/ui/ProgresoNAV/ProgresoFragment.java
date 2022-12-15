package com.example.apprpe.ui.ProgresoNAV;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.Dataset;
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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ProgresoFragment extends Fragment {

    private ProgresoViewModel progresoViewModel;
    private List<Entrenamiento> list = new ArrayList<>();
    private List<Entry> entradas = new ArrayList<>();
    private LineChart lineChart;
    ArrayList<ILineDataSet> dataset = new ArrayList<>();
    //private EntRealizadoViewModel entRealizadoViewModel;
    //private EntrenamientoViewModel entrenamientoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_progreso, container, false);
        progresoViewModel = new ViewModelProvider(this).get(ProgresoViewModel.class);

        lineChart = root.findViewById(R.id.lineChart);

        progresoViewModel.getAllEntrenamientos().observe(getViewLifecycleOwner(), new Observer<List<Entrenamiento>>() {
            @Override
            public void onChanged(List<Entrenamiento> entrenamientos) {
                for(int i=0; i<entrenamientos.size(); i++){
                    list.addAll(entrenamientos);
                    Log.i("LIST", String.valueOf(list.get(i).getRpe_Sesion()));
                }
                List<Entry> linevalues = new ArrayList<Entry>();
                List<String> linevalueString = new ArrayList<String>();


                linevalues.add(new Entry(1.0f, 4.0F, "aasdoiad"));
                linevalues.add(new Entry(2.0f, 3.0F, "sfsfsfd"));
                linevalues.add(new Entry(3.0f, 2.0F));
                linevalues.add(new Entry(4f, 1.0F));
                linevalues.add(new Entry(5f, 8.0F));
                linevalues.add(new Entry(6f, 3.0F));
                linevalues.add(new Entry(7f, 2.0F));
                linevalues.add(new Entry(8f, 5.0F));
                linevalues.add(new Entry(9f, 6.0F));
                linevalues.add(new Entry(10f, 5.0F));
                linevalues.add(new Entry(11f, 4.0F));
                linevalues.add(new Entry(12f, 7.0F));
                linevalues.add(new Entry(13f, 9.0F));
                /*linevalues.add(new Entry(14f, 5.0F));
                linevalues.add(new Entry(15f, 6.0F));
                linevalues.add(new Entry(16f, 5.0F));
                linevalues.add(new Entry(17f, 5.0F));
                linevalues.add(new Entry(18f, 6.0F));
                linevalues.add(new Entry(19f, 5.0F));
                linevalues.add(new Entry(20f, 9.0F));
                linevalues.add(new Entry(21f, 5.0F));
                linevalues.add(new Entry(22f, 6.0F));
                linevalues.add(new Entry(23f, 5.0F));
                linevalues.add(new Entry(24f, 5.0F));
                linevalues.add(new Entry(25f, 6.0F));
                linevalues.add(new Entry(26f, 5.0F));*/


                //linevalues.add(new Entry(9, 3.0F));


                String[] array = new String[]{"2022/11/23", "2022/11/24", "2022/11/25", "2022/11/28", "2022/12/02", "2022/12/05",
                        "2022/12/08", "2022/12/09", "2022/12/11", "2022/12/12", "2022/12/13", "2022/12/14", "2022/12/15" };

                ArrayList<String> array2 = new ArrayList<>();
                array2.add("2022/11/23");
                array2.add("2022/11/24");
                array2.add("2022/11/25");
                array2.add("2022/11/26");
                array2.add("2022/11/27");

                //Collections.sort(linevalues, new EntryXComparator());

                //for(int i=0; i<linevalues.size(); i++){
                    //Log.i("LINE", String.valueOf(linevalues.get(i).getX()));
                //}

                LineDataSet lineDataSet = new LineDataSet(linevalues, "RPE");
                lineDataSet.setLineWidth(2);
                lineDataSet.setValueTextColor(Color.rgb(0,0,0));
                lineDataSet.setValueTextSize(10);
                lineDataSet.setHighlightEnabled(true);
                lineDataSet.setDrawHighlightIndicators(true);
                lineDataSet.setCircleRadius(4f);
                lineDataSet.setDrawCircleHole(false);
                lineDataSet.enableDashedLine(10,15,0);

                ArrayList<ILineDataSet> dataset = new ArrayList<>();
                dataset.add(lineDataSet);

                XAxis axis = lineChart.getXAxis();
                //axis.setValueFormatter(new myFechaValueFormatter(array));
                axis.setAvoidFirstLastClipping(true);
                axis.setPosition(XAxis.XAxisPosition.BOTTOM);
                axis.setDrawGridLines(false);
                axis.setCenterAxisLabels(true);
                axis.setLabelRotationAngle(-25);
                axis.setGranularity(1f);
                axis.setTextSize(10);
                axis.setValueFormatter(new IndexAxisValueFormatter(array));


                YAxis yaxis = lineChart.getAxisLeft();
                yaxis.setDrawLabels(true);
                //yaxis.setDrawZeroLine(false);
                //yaxis.setZeroLineWidth(15);
                //yaxis.setZeroLineColor(Color.BLACK);
                yaxis.setDrawGridLines(false);
                yaxis.setTextSize(13);
                yaxis.setTextColor(Color.rgb(0,0,0));
                yaxis.setLabelCount(7);
                yaxis.setSpaceTop(10f);
                //yaxis.setValueFormatter(new myFechaValueFormatter(array));


                LineData data = new LineData(dataset);
                //data.setValueFormatter(new myFechaValueFormatter(array));
                lineChart.setData(data);
                lineChart.setNoDataText("Sin datos que mostrar");
                lineChart.setNoDataTextColor(Color.rgb(0,0,0));
                lineChart.setNoDataTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                lineChart.getAxisRight().setEnabled(false);
                //lineChart.getAxisLeft().setEnabled(true);
                //lineChart.getTransformer(YAxis.AxisDependency.LEFT);
                lineChart.setDrawBorders(true);
                lineChart.setBorderWidth(1);
                Legend legend = lineChart.getLegend();
                legend.setEnabled(true);
                legend.setWordWrapEnabled(true);
                legend.setMaxSizePercent(0.8f);
                legend.setYEntrySpace(5f);
                //lineChart.setTouchEnabled(false);
                //lineChart.setDragEnabled(false);
                //lineChart.setHighlightPerTapEnabled(false);
                //lineChart.setHighlightPerDragEnabled(false);
                //lineChart.setLogEnabled(true);
                //lineChart.setVisibleXRangeMaximum(15);
                lineChart.invalidate();
                lineChart.animateXY(900,500);
            }

        });
        return root;
    }

}

class myFechaValueFormatter extends ValueFormatter {

    String[] mValues;

    public myFechaValueFormatter(String[] values){
        this.mValues = values;
        Log.i("FORMA", Arrays.toString(values));
    }

    @Override
    public String getFormattedValue(float value) {
        //if (mValues.length > value & value != mValues.length){
            Log.i("FORMATTER", String.valueOf(value));
            return mValues[(int)value];
        //}
        //return "";
    }
}
