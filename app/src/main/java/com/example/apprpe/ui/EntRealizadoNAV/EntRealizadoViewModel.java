package com.example.apprpe.ui.EntRealizadoNAV;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.apprpe.EntrenamientoRepository;
import com.example.apprpe.modelo.Ent_Realizado;

import java.util.List;

public class EntRealizadoViewModel extends AndroidViewModel {

    private EntrenamientoRepository mRepository;
    private LiveData<List<Ent_Realizado>> listEntrenamientosRealizados;

    public EntRealizadoViewModel(Application application) {
        super(application);
        mRepository = new EntrenamientoRepository(application);
        listEntrenamientosRealizados = mRepository.getAllEntrenamientosRealizados();
    }

    public LiveData<List<Ent_Realizado>> getAllEntrenamientosRealizados() { return listEntrenamientosRealizados; }
    //public LiveData<List<Ent_Realizado>> getEntrenamientosFuerzaRealizados() { return mRepository.getEntrenamientosFuerza(); }
    //public LiveData<List<Ent_Realizado>> getEntrenamientosAerobicoRealizados() { return mRepository.getEntrenamientosAerobico(); }

    public void deleteAllEnt_Realizado() {mRepository.deleteEnt_Realizado(); }
}