package com.AntArDev.MyRpe_Assistant.view.navBottom.ProgresoNAV;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.AntArDev.MyRpe_Assistant.modelo.Ent_Realizado;
import com.AntArDev.MyRpe_Assistant.modelo.Peso;
import com.AntArDev.MyRpe_Assistant.Repository;

import java.util.List;
import java.util.Map;

public class EstadisticaViewModel extends AndroidViewModel {

    private Repository mRepository;
    private LiveData<List<Ent_Realizado>> listEntrenamientos;

    public EstadisticaViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        listEntrenamientos = mRepository.getEntrenamientosRealizadosFiltro(31);
    }

    public LiveData<List<Ent_Realizado>> getAllEntrenamientosRealizados() { return listEntrenamientos; }
    public LiveData<List<Ent_Realizado>> getAllEntrenamientosRealizadosOrderAsc() { return listEntrenamientos; }
    public LiveData<List<Ent_Realizado>> getEntrenamientosRealizadosFiltro(int dias) {
        return mRepository.getEntrenamientosRealizadosFiltro(dias);
    }

    public LiveData<Map<String, Integer>> getEntrenamientosCount(){
        return mRepository.getEntrenamientosCount();
    }

    public LiveData<List<Peso>> getHistorialPesos(){
        return mRepository.getHistorialPesos();
    }

}