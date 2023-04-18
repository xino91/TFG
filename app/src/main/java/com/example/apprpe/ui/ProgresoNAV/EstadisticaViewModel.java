package com.example.apprpe.ui.ProgresoNAV;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apprpe.EntrenamientoRepository;
import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.modelo.Entrenamiento;

import java.util.List;
import java.util.Map;

public class EstadisticaViewModel extends AndroidViewModel {

    private EntrenamientoRepository mRepository;
    private LiveData<List<Ent_Realizado>> listEntrenamientos;

    public EstadisticaViewModel(Application application) {
        super(application);
        mRepository = new EntrenamientoRepository(application);
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

}