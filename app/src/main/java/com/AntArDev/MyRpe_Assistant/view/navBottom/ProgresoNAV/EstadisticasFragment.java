package com.AntArDev.MyRpe_Assistant.view.navBottom.ProgresoNAV;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.AntArDev.MyRpe_Assistant.modelo.Ent_Realizado;
import com.AntArDev.MyRpe_Assistant.modelo.Peso;
import com.example.apprpe.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EstadisticasFragment extends Fragment implements MenuProvider {

    private EstadisticaViewModel estadisticaViewModel;
    ArrayList<String> arrayglineal = new ArrayList<>();
    ArrayList<String> arraygbar = new ArrayList<>();
    private List<Ent_Realizado> list = new ArrayList<>();

    private List<Peso> listPesos = new ArrayList<>();
    ArrayList<String> array = new ArrayList<>();
    private LineChart lineChart;
    private BarChart barChart;
    private PieChart pieChart;
    String[] valuesDolor;
    private TextView monotonia_line, monotonia_bar, fatiga_line, fatiga_bar;
    private TextView txtmonotonia_line, txtmonotonia_bar, txtfatiga_line, txtfatiga_bar;
    private RadioButton radiobuttonf7, radiobuttonf14, radiobuttonf21, radiobuttonf31;
    String datoGlineal, datoGbarras, datoGpie;

    AutoCompleteTextView spinner_datos;
    private ImageButton filtro_Glineal, filtro_Gbarras, filtro_Gpie;

    public EstadisticasFragment(){
        datoGlineal = "Rpe";
        datoGbarras = "Rpe";
        datoGpie = "Entrenamientos";
        valuesDolor = new String[]{"Sin Dolor", "Suave", "Moderado", "Mucho", "Insoportable"};
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_estadisticas, container, false);
        requireActivity().addMenuProvider(this, getViewLifecycleOwner());
        estadisticaViewModel = new ViewModelProvider(this).get(EstadisticaViewModel.class);
        estadisticaViewModel.getEntrenamientosRealizadosFiltro(31)
                .observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
            @Override
            public void onChanged(List<Ent_Realizado> entrealizados) {
                    list.addAll(entrealizados);
                    Collections.reverse(list);
                    CallLineChart(datoGlineal);
                    CallBarChart(datoGbarras);
                    CallPieChart();
            }
        });
        estadisticaViewModel.getHistorialPesos().observe(getViewLifecycleOwner(), new Observer<List<Peso>>() {
            @Override
            public void onChanged(List<Peso> pesos) {
                listPesos.addAll(pesos);
            }
        });
        return root;
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_estadisticas, menu);
    }
    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_info) {
            return DialogoInfo();
        }
        return false;
    }

    public boolean DialogoInfo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.cuadrodialogo_info, null);
        builder.setView(dialogView);

        TextView textMessage = dialogView.findViewById(R.id.text_message);
        SpannableString spannableString = new SpannableString("Para más información consultar " +
                "https://www.efdeportes.com/efd106/monitorizacion-del-entrenamiento-en-deportes-de-equipo.htm");
        Linkify.addLinks(spannableString, Linkify.WEB_URLS);
        textMessage.setText(spannableString);
        textMessage.setMovementMethod(LinkMovementMethod.getInstance());

        builder.setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
    }


    /*********************** LINE CHART **************************/
    private void CallLineChart(String dato_consulta) {
        View root = getView();
        assert root != null;
        vincularVistas(root);
        escuchadoresFiltrosGLineal();
        CalcularMonotoniaYFatiga(1);
        switch (dato_consulta) {
            case "Rpe":
                txtmonotonia_line.setVisibility(View.VISIBLE);
                txtfatiga_line.setVisibility(View.VISIBLE);
                monotonia_line.setVisibility(View.VISIBLE);
                fatiga_line.setVisibility(View.VISIBLE);
                GRpeGlineal();
                break;
            case "Carga":
                txtmonotonia_line.setVisibility(View.VISIBLE);
                txtfatiga_line.setVisibility(View.VISIBLE);
                monotonia_line.setVisibility(View.VISIBLE);
                fatiga_line.setVisibility(View.VISIBLE);
                GCargaGlineal();
                break;
            case "Dolor":
                txtmonotonia_line.setVisibility(View.GONE);
                txtfatiga_line.setVisibility(View.GONE);
                monotonia_line.setVisibility(View.GONE);
                fatiga_line.setVisibility(View.GONE);
                GDolorGlineal();
                break;
            case "Peso":
                txtmonotonia_line.setVisibility(View.GONE);
                txtfatiga_line.setVisibility(View.GONE);
                monotonia_line.setVisibility(View.GONE);
                fatiga_line.setVisibility(View.GONE);
                GPeso();
                break;
        }
    }

    /*************************** BAR CHART ******************/
    private void CallBarChart(String dato_consulta){
        View root = getView();
        assert root != null;
        vincularVistas(root);
        escuchadoresFiltrosGBar();
        CalcularMonotoniaYFatiga(2);

        switch (dato_consulta) {
            case "Rpe":
                txtmonotonia_bar.setVisibility(View.VISIBLE);
                txtfatiga_bar.setVisibility(View.VISIBLE);
                monotonia_bar.setVisibility(View.VISIBLE);
                fatiga_bar.setVisibility(View.VISIBLE);
                GRpeGbarras();
                break;
            case "Carga":
                txtmonotonia_bar.setVisibility(View.VISIBLE);
                txtfatiga_bar.setVisibility(View.VISIBLE);
                monotonia_bar.setVisibility(View.VISIBLE);
                fatiga_bar.setVisibility(View.VISIBLE);
                GCargaGbarras();
                break;
            case "Dolor":
                txtmonotonia_bar.setVisibility(View.GONE);
                txtfatiga_bar.setVisibility(View.GONE);
                monotonia_bar.setVisibility(View.GONE);
                fatiga_bar.setVisibility(View.GONE);
                GDolorGbarras();
                break;
            case "Peso":
                txtmonotonia_bar.setVisibility(View.GONE);
                txtfatiga_bar.setVisibility(View.GONE);
                monotonia_bar.setVisibility(View.GONE);
                fatiga_bar.setVisibility(View.GONE);
                GPesoGbarras();
                break;
        }
    }

    /*************************** PIE CHART *********************/
    private void CallPieChart(){
        View root = getView();
        assert root != null;
        vincularVistas(root);
        escuchadoresFiltrosGBar();
        List<PieEntry> pievalues_pie = new ArrayList<PieEntry>();

        estadisticaViewModel.getEntrenamientosCount().observe(getViewLifecycleOwner(), new Observer<Map<String, Integer>>() {
            @Override
            public void onChanged(Map<String, Integer> mapa) {
                for (String s : mapa.keySet()) {
                    pievalues_pie.add(new PieEntry(mapa.get(s), s));

                    PieDataSet pieDataSet = new PieDataSet(pievalues_pie,"");
                    ConfigPieDataset(pieDataSet);
                    PieData pieData = new PieData(pieDataSet);
                    ConfigPieChart(pieData);
                }
            }
        });
    }

    private void filtroDialogoGlineal() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext(), R.style.Base_Theme_Material3_Light_Dialog);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_filtro,null);

        String[] arreglo_tipo = {"Rpe", "Carga", "Dolor", "Peso"};
        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arreglo_tipo);
        spinner_datos = view.findViewById(R.id.desplegable);
        spinner_datos.setAdapter(adapter_spinner);
        alertDialog.setView(view);

        vincularVistas(view);
        alertDialog.setTitle("Filtro Gráfico Lineal")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datoGlineal = String.valueOf(spinner_datos.getText());
                        if(!isDatoVacio(datoGlineal)){
                            getFiltroDiasYrpeGlineal();}
                        else{Toast.makeText(getContext(), "Debe introducir un dato a mostrar", Toast.LENGTH_SHORT).show();}
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
    private void filtroDialogoGBarras(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext(), R.style.Base_Theme_Material3_Light_Dialog);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_filtro,null);

        String[] arreglo_tipo = {"Rpe", "Carga", "Dolor", "Peso"};
        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arreglo_tipo);
        spinner_datos = view.findViewById(R.id.desplegable);
        spinner_datos.setAdapter(adapter_spinner);
        alertDialog.setView(view);

        vincularVistas(view);
        alertDialog.setTitle("Filtro Gráfico de barras")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datoGbarras = String.valueOf(spinner_datos.getText());
                        if(!isDatoVacio(datoGbarras)){
                            getFiltroDiasYrpeGbarras();}
                        else{Toast.makeText(getContext(), "Debe introducir un datoGlineal a mostrar", Toast.LENGTH_SHORT).show();}
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
    public void getFiltroDiasYrpeGlineal() {
        if (radiobuttonf7.isChecked() && !isDatoVacio(datoGlineal)) {
            estadisticaViewModel.getEntrenamientosRealizadosFiltro(7).observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
                @Override
                public void onChanged(List<Ent_Realizado> ent_realizados) {
                    list.clear();
                    list.addAll(ent_realizados);
                    Collections.reverse(list);
                    CallLineChart(datoGlineal);
                }
            });
        } else if (radiobuttonf14.isChecked() && !isDatoVacio(datoGlineal)) {
            estadisticaViewModel.getEntrenamientosRealizadosFiltro(14).observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
                @Override
                public void onChanged(List<Ent_Realizado> ent_realizados) {
                    list.clear();
                    list.addAll(ent_realizados);
                    Collections.reverse(list);
                    CallLineChart(datoGlineal);
                }
            });
        } else if (radiobuttonf21.isChecked() && !isDatoVacio(datoGlineal)) {
            estadisticaViewModel.getEntrenamientosRealizadosFiltro(21).observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
                @Override
                public void onChanged(List<Ent_Realizado> ent_realizados) {
                    list.clear();
                    list.addAll(ent_realizados);
                    Collections.reverse(list);
                    CallLineChart(datoGlineal);
                }
            });
        } else if (radiobuttonf31.isChecked()&& !isDatoVacio(datoGlineal)) {
            estadisticaViewModel.getEntrenamientosRealizadosFiltro(31).observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
                @Override
                public void onChanged(List<Ent_Realizado> ent_realizados) {
                    list.clear();
                    list.addAll(ent_realizados);
                    Collections.reverse(list);
                    CallLineChart(datoGlineal);
                }
            });
        }
    }
    public void getFiltroDiasYrpeGbarras() {
        if (radiobuttonf7.isChecked() && !isDatoVacio(datoGbarras)) {
            estadisticaViewModel.getEntrenamientosRealizadosFiltro(7).observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
                @Override
                public void onChanged(List<Ent_Realizado> ent_realizados) {
                    list.clear();
                    list.addAll(ent_realizados);
                    Collections.reverse(list);
                    CallBarChart(datoGbarras);
                }
            });
        } else if (radiobuttonf14.isChecked() && !isDatoVacio(datoGbarras)) {
            estadisticaViewModel.getEntrenamientosRealizadosFiltro(14).observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
                @Override
                public void onChanged(List<Ent_Realizado> ent_realizados) {
                    list.clear();
                    list.addAll(ent_realizados);
                    Collections.reverse(list);
                    CallBarChart(datoGbarras);
                }
            });
        } else if (radiobuttonf21.isChecked() && !isDatoVacio(datoGbarras)) {
            estadisticaViewModel.getEntrenamientosRealizadosFiltro(21).observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
                @Override
                public void onChanged(List<Ent_Realizado> ent_realizados) {
                    list.clear();
                    list.addAll(ent_realizados);
                    Collections.reverse(list);
                    CallBarChart(datoGbarras);
                }
            });
        } else if (radiobuttonf31.isChecked()&& !isDatoVacio(datoGbarras)) {
            estadisticaViewModel.getEntrenamientosRealizadosFiltro(31).observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
                @Override
                public void onChanged(List<Ent_Realizado> ent_realizados) {
                    list.clear();
                    list.addAll(ent_realizados);
                    Collections.reverse(list);
                    CallBarChart(datoGbarras);
                }
            });
        }
    }

    public void GRpeGlineal() {
        List<Entry> linevalues = new ArrayList<Entry>();
        List<Entry> linevalues2 = new ArrayList<Entry>();

        for (int i = 0; i < list.size(); i++) {
            linevalues.add(new Entry(i, list.get(i).getRpe_objetivo()));
            linevalues2.add(new Entry(i, list.get(i).getRpe_subjetivo()));
        }

        for (int i = 0; i < list.size(); i++) {
            arrayglineal.add(list.get(i).getFechaString());
        }

        LineDataSet lineDataSet = new LineDataSet(linevalues, "RPE Objetivo");
        LineDataSet lineDataSet2 = new LineDataSet(linevalues2, "RPE Percibido");
        ConfigLineDataSet(lineDataSet);
        ConfigLineDataSet2(lineDataSet2);

        ArrayList<ILineDataSet> dataset = new ArrayList<>();
        dataset.add(lineDataSet);
        dataset.add(lineDataSet2);

        XAxis axis = lineChart.getXAxis();
        YAxis yaxis = lineChart.getAxisLeft();
        yaxis.setValueFormatter(new myValueFormatter());
        ConfigAxis(axis, yaxis);

        LineData data = new LineData(dataset);
        lineChart.setData(data);
        ConfigLineChart(lineChart);
    }
    public void GCargaGlineal(){
        List<Entry> linevalues = new ArrayList<Entry>();

        for(int i = 0; i< list.size(); i++){
            linevalues.add(new Entry(i, list.get(i).getCarga()));
        }

        for(int i = 0; i< list.size(); i++){
            arrayglineal.add(list.get(i).getFechaString());
        }

        LineDataSet lineDataSet = new LineDataSet(linevalues, "Carga");
        ConfigLineDataSet(lineDataSet);

        ArrayList<ILineDataSet> dataset = new ArrayList<>();
        dataset.add(lineDataSet);

        XAxis axis = lineChart.getXAxis();
        YAxis yaxis = lineChart.getAxisLeft();
        yaxis.setValueFormatter(new myValueFormatter());
        ConfigAxis(axis, yaxis);

        LineData data = new LineData(dataset);
        lineChart.setData(data);
        ConfigLineChart(lineChart);
    }
    public void GDolorGlineal(){
        List<Entry> linevalues = new ArrayList<Entry>();
        for(int i = 0; i< list.size(); i++){
            linevalues.add(new Entry(i, list.get(i).getDolor()));
        }

        LineDataSet lineDataSet = new LineDataSet(linevalues, "Dolor");
        ConfigLineDataSet(lineDataSet);

        ArrayList<ILineDataSet> dataset = new ArrayList<>();
        dataset.add(lineDataSet);

        XAxis axis = lineChart.getXAxis();
        YAxis yaxis = lineChart.getAxisLeft();
        ConfigAxis(axis, yaxis);
        yaxis.setValueFormatter(new IndexAxisValueFormatter(valuesDolor));
        yaxis.setTextSize(10f);
        yaxis.setLabelCount(5);
        yaxis.setGranularityEnabled(true);

        LineData data = new LineData(dataset);
        data.setValueFormatter(new IndexAxisValueFormatter(valuesDolor));
        lineChart.setData(data);
        ConfigLineChart(lineChart);
    }
    public void GPeso(){
        List<Entry> linevalues = new ArrayList<Entry>();

        for(int i=0; i<listPesos.size(); i++){
            linevalues.add(new Entry(i, (float) listPesos.get(i).getPeso()));
        }

        for(int i=0; i<listPesos.size(); i++){
            array.add(listPesos.get(i).getFecha_registro().toString());
        }

        LineDataSet lineDataSet = new LineDataSet(linevalues, "Peso");
        ConfigLineDataSet(lineDataSet);

        ArrayList<ILineDataSet> dataset = new ArrayList<>();
        dataset.add(lineDataSet);

        XAxis axis = lineChart.getXAxis();
        YAxis yaxis = lineChart.getAxisLeft();
        ConfigAxis(axis, yaxis);

        LineData data = new LineData(dataset);
        //data.setValueFormatter(new myFechaValueFormatter(array));
        lineChart.setData(data);
        ConfigLineChart(lineChart);
    }

    public void GRpeGbarras() {
        List<BarEntry> barvalues = new ArrayList<BarEntry>();
        List<BarEntry> barvalues2 = new ArrayList<BarEntry>();

        for(int i = 0; i< list.size(); i++){
            barvalues.add(new BarEntry(i, list.get(i).getRpe_objetivo()));
            barvalues2.add(new BarEntry(i, list.get(i).getRpe_subjetivo()));
        }

        arraygbar.clear();
        for(int i = 0; i< list.size(); i++){
            arraygbar.add(list.get(i).getFechaString());
            arraygbar.add(list.get(i).getFechaString());
        }

        BarDataSet barDataSet = new BarDataSet(barvalues, "RPE Objetivo");
        BarDataSet barDataSet2 = new BarDataSet(barvalues2, "RPE Percibido");
        barDataSet2.setColors(R.color.chart);

        BarData barData = new BarData(barDataSet, barDataSet2);

        XAxis xaxis = barChart.getXAxis();
        YAxis yaxis = barChart.getAxisLeft();
        yaxis.setValueFormatter(new myValueFormatter());
        ConfigAxisBarras(xaxis, yaxis);

        barData.setValueTextSize(10f);

        barChart.setData(barData);
        ConfigBarChart(barChart);
    }
    public void GCargaGbarras(){
        List<BarEntry> barvalues = new ArrayList<BarEntry>();

        for(int i = 0; i< list.size(); i++){
            barvalues.add(new BarEntry(i, list.get(i).getCarga()));
        }

        arraygbar.clear();
        for(int i = 0; i< list.size(); i++){
            arraygbar.add(list.get(i).getFechaString());
            arraygbar.add(list.get(i).getFechaString());
        }

        BarDataSet barDataSet = new BarDataSet(barvalues, "Carga");

        BarData barData = new BarData(barDataSet);

        XAxis xaxis = barChart.getXAxis();
        YAxis yaxis = barChart.getAxisLeft();
        yaxis.setValueFormatter(new myValueFormatter());
        ConfigAxisBarras(xaxis, yaxis);

        barData.setValueTextSize(10f);

        barChart.setData(barData);
        ConfigBarChart(barChart);
    }
    public void GDolorGbarras(){
        List<BarEntry> barvalues = new ArrayList<BarEntry>();

        for(int i = 0; i< list.size(); i++){
            barvalues.add(new BarEntry(i, list.get(i).getDolor()));
        }

        arraygbar.clear();
        for(int i = 0; i< list.size(); i++){
            arraygbar.add(list.get(i).getFechaString());
            arraygbar.add(list.get(i).getFechaString());
        }

        BarDataSet barDataSet = new BarDataSet(barvalues, "Carga");

        BarData barData = new BarData(barDataSet);
        //barData.groupBars(0, 0.1f,0f);
        if(list.size() ==7){
            barData.setValueFormatter(new IndexAxisValueFormatter(valuesDolor));
        }
        XAxis xaxis = barChart.getXAxis();
        YAxis yaxis = barChart.getAxisLeft();
        yaxis.setValueFormatter(new IndexAxisValueFormatter(valuesDolor));
        ConfigAxisBarras(xaxis, yaxis);

        barData.setValueTextSize(10f);
        //barData.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        //barData.setDrawValues(true); por defecto true

        barChart.setData(barData);
        //barChart.setVisibleXRangeMinimum(list.size());
        ConfigBarChart(barChart);
    }
    public void GPesoGbarras(){
        List<BarEntry> barvalues = new ArrayList<BarEntry>();

        for(int i = 0; i< listPesos.size(); i++){
            barvalues.add(new BarEntry(i, (float)listPesos.get(i).getPeso()));
        }

        arraygbar.clear();
        for(int i = 0; i< listPesos.size(); i++){
            arraygbar.add(listPesos.get(i).getFecha_registro().toString());
        }

        BarDataSet barDataSet = new BarDataSet(barvalues, "Peso");

        BarData barData = new BarData(barDataSet);

        XAxis xaxis = barChart.getXAxis();
        YAxis yaxis = barChart.getAxisLeft();
        yaxis.setValueFormatter(new myValueFormatter());
        ConfigAxisBarras(xaxis, yaxis);

        barData.setValueTextSize(10f);
        //barData.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        //barData.setDrawValues(true); por defecto true

        barChart.setData(barData);
        //barChart.setVisibleXRangeMinimum(list.size());
        ConfigBarChart(barChart);
    }

    /**
     *Función que calcula el índice de monotonía y el índice de fatiga y modifica los TextView
     * correspondientes a tráves del parámetro que recibe
     * @param llamada si llamada = 1, modifica TextView del gráfico lineal
     *                si llamada = 2, modifica TextView del gráfico de barras
     */
    public void CalcularMonotoniaYFatiga(int llamada){
        double media_dias = 0.0;
        double sumatoria_cuadrado = 0.0;
        double x = 0.0;
        double desviacion_estandar = 0.0;

        for(int i=0; i<list.size(); i++){
            media_dias = media_dias + list.get(i).getCarga();
        }
        media_dias = (int) (media_dias / list.size());

        for(int i=0; i< list.size(); i++){
            x = list.get(i).getCarga() - media_dias;
            sumatoria_cuadrado = sumatoria_cuadrado + (int) Math.pow(x, 2);
        }

        desviacion_estandar = (int) (Math.sqrt(sumatoria_cuadrado / list.size()-1) );

        double monotonia = 0.0;
        monotonia = media_dias / desviacion_estandar;
        double fatiga;
        fatiga = media_dias * monotonia;

        if(llamada == 1){
            monotonia_line.setText((String.format("%.2f", monotonia)));
            fatiga_line.setText(String.format("%.2f", fatiga));
        }
        if(llamada == 2){
            monotonia_bar.setText((String.format("%.2f", monotonia)));
            fatiga_bar.setText(String.format("%.2f", fatiga));
        }
    }

    /**
     * Escuchador del botón filtro del gráfico Lineal
     */
    public void escuchadoresFiltrosGLineal(){
        filtro_Glineal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtroDialogoGlineal();}
        });
    }

    /**
     * Escuchador del botón filtro del gráfico de barras
     */
    public void escuchadoresFiltrosGBar(){
        filtro_Gbarras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtroDialogoGBarras();}
        });
    }

    public void vincularVistas(View root){
        filtro_Glineal = root.findViewById(R.id.imageButton_filter_linechart);
        filtro_Gbarras = root.findViewById(R.id.imageButton_filter_barchart);
        filtro_Gpie = root.findViewById(R.id.imageButton_filter_piechart);
        lineChart = root.findViewById(R.id.lineChart);
        barChart = root.findViewById(R.id.BarChart);
        pieChart = root.findViewById(R.id.PieChart);
        radiobuttonf7 = root.findViewById(R.id.radioButtonf7);
        radiobuttonf14 = root.findViewById(R.id.radioButtonf14);
        radiobuttonf21 = root.findViewById(R.id.radioButtonf21);
        radiobuttonf31 = root.findViewById(R.id.radioButtonf31);
        monotonia_line = root.findViewById(R.id.textView_monotoniaLineChart);
        monotonia_bar = root.findViewById(R.id.textView_monotoniaBarchart);
        fatiga_line = root.findViewById(R.id.textView_fatigaLinechart);
        fatiga_bar = root.findViewById(R.id.textView_fatigaBarChart);
        txtmonotonia_line = root.findViewById(R.id.textView22_monotonia);
        txtfatiga_line = root.findViewById(R.id.textView22_fatiga);
        txtmonotonia_bar = root.findViewById(R.id.textView27_monotonia);
        txtfatiga_bar = root.findViewById(R.id.textView27_fatiga);
    }

    private boolean isDatoVacio(String dato) {
        return dato.isEmpty();
    }

    /******* FUNCIONES PARA CONFIGURAR LA VISTA DE LINECHART **********/
    public void ConfigLineDataSet(LineDataSet lineDataSet) {
        lineDataSet.setLineWidth(2);
        lineDataSet.setValueTextSize(10);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setCircleRadius(4f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.enableDashedLine(10, 15, 0);
    }
    public void ConfigLineDataSet2(LineDataSet lineDataSet2){lineDataSet2.setLineWidth(2);
        lineDataSet2.setColor(R.color.chart);
        lineDataSet2.setCircleColor(R.color.chart);
        lineDataSet2.setValueTextColor(R.color.chart);
        lineDataSet2.setValueTextSize(10);
        lineDataSet2.setHighlightEnabled(true);
        lineDataSet2.setDrawHighlightIndicators(true);
        lineDataSet2.setCircleRadius(4f);
        lineDataSet2.setDrawCircleHole(false);
        lineDataSet2.enableDashedLine(10,15,0);
    }
    public void ConfigAxis(XAxis axis, YAxis yaxis){
        //axis.setValueFormatter(new myFechaValueFormatter(array));
        axis.setAvoidFirstLastClipping(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setDrawGridLines(true);
        axis.setCenterAxisLabels(true);
        axis.setLabelRotationAngle(-20);
        axis.setGranularity(1f);
        axis.setTextSize(10);
        axis.setValueFormatter(new IndexAxisValueFormatter(arrayglineal));

        //yaxis.setDrawLabels(true);
        //yaxis.setDrawZeroLine(false);
        //yaxis.setZeroLineWidth(15);
        //yaxis.setZeroLineColor(Color.BLACK);
        //yaxis.setGranularityEnabled(false);
        yaxis.setDrawGridLines(false);
        yaxis.setTextSize(13);
        yaxis.setTextColor(Color.rgb(0,0,0));
        yaxis.setLabelCount(7);
        yaxis.setSpaceTop(10f);
        //yaxis.setValueFormatter(new myFechaValueFormatter(array));
    }
    public void ConfigLineChart(LineChart lineChart){
        lineChart.setNoDataText("Sin datos que mostrar");
        lineChart.setNoDataTextColor(Color.rgb(0,0,0));
        lineChart.setNoDataTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        lineChart.getAxisRight().setEnabled(false);
        lineChart.canResolveTextAlignment();
        //lineChart.
        //lineChart.getAxisLeft().setEnabled(false);
        //lineChart.getTransformer(YAxis.AxisDependency.LEFT);
        lineChart.setDrawBorders(true);
        lineChart.setBorderWidth(1);
        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);
        legend.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        legend.setTextSize(11f);
        legend.setXEntrySpace(17f);;
        //lineChart.setTouchEnabled(false);
        //lineChart.setDragEnabled(false);
        //lineChart.setHighlightPerTapEnabled(false);
        //lineChart.setHighlightPerDragEnabled(false);
        //lineChart.setLogEnabled(true);
        //lineChart.setVisibleXRangeMaximum(15);
        lineChart.invalidate();
        lineChart.animateXY(900,500);
    }

    /******* FUNCIONES PARA CONFIGURAR LA VISTA DE BARCHART **********/
    public void ConfigAxisBarras(XAxis xaxis, YAxis yaxis){
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setAvoidFirstLastClipping(true);
        xaxis.setDrawGridLines(true);
        xaxis.setCenterAxisLabels(true);
        xaxis.setLabelRotationAngle(-20);
        xaxis.setGranularity(1);
        xaxis.setGranularityEnabled(true);
        xaxis.setValueFormatter(new IndexAxisValueFormatter(arraygbar));

        yaxis.setTextSize(10);
    }
    public void ConfigBarChart(BarChart barchart){
        barchart.setNoDataText("Sin datos que mostrar");
        barchart.setNoDataTextColor(Color.rgb(0,0,0));
        barchart.setNoDataTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        barchart.getAxisRight().setEnabled(false);
        barChart.setDrawBorders(true);
        barChart.setBorderWidth(1);
        barChart.setFitBars(true);
        barChart.notifyDataSetChanged();
        //barChart.setDragEnabled(true);
        Legend legendbar = barChart.getLegend();
        legendbar.setEnabled(true);
        legendbar.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        legendbar.setWordWrapEnabled(true);
        legendbar.setTextSize(11f);
        legendbar.setXEntrySpace(17f);
        legendbar.setWordWrapEnabled(true);
        legendbar.setYOffset(-0.2f);
        barChart.invalidate();
        barChart.animateXY(900, 500);
    }

    /******* FUNCIONES PARA CONFIGURAR LA VISTA DE BARCHART **********/
    public void ConfigPieDataset(PieDataSet piedataset){
        int[] colorArray = new int[]{
                    ContextCompat.getColor(requireContext(), R.color.piechart_rojo),
                    ContextCompat.getColor(requireContext(), R.color.piechart_verde),
                    ContextCompat.getColor(requireContext(), R.color.piechart_amarillo),
                    ContextCompat.getColor(requireContext(), R.color.piechart_azul),
                    ContextCompat.getColor(requireContext(), R.color.piechart_morado),
                    ContextCompat.getColor(requireContext(), R.color.piechart_naranja),
                    ContextCompat.getColor(requireContext(), R.color.piechart_celeste)};

        piedataset.setColors(colorArray);
        piedataset.setSliceSpace(3f);
        piedataset.setSelectionShift(5f);
        piedataset.setValueTextSize(14f);
    }
    public void ConfigPieChart(PieData pieData){
        pieChart.setDrawHoleEnabled(false);
        pieChart.setData(pieData);
        pieChart.setNoDataText("Sin datos que mostar");
        Description description = new Description();
        description.setText("Entrenamientos más usados");
        description.setTextSize(10f);
        description.setYOffset(-5);
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setYOffset(-12);
        //pieChart.setExtraBottomOffset(20);
        pieChart.setExtraOffsets(10,-5,10,30);
        pieChart.setDescription(description);
        pieChart.invalidate();
        pieChart.animateXY(1600,700);
    }
}

class myValueFormatter extends ValueFormatter {

    public myValueFormatter(){}

    @Override
    public String getFormattedValue(float value) {
        return String.valueOf((int)value);
    }
}
