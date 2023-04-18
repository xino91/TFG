package com.example.apprpe.ui.EntRealizadoNAV;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.apprpe.EntrenamientoRepository;
import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.modelo.Entrenamiento;

import java.util.List;

public class EntRealizadoViewModel extends AndroidViewModel {

    private EntrenamientoRepository mRepository;
    private LiveData<List<Ent_Realizado>> listEntrenamientosRealizados;

    public EntRealizadoViewModel(Application application) {
        super(application);
        mRepository = new EntrenamientoRepository(application);
        listEntrenamientosRealizados = mRepository.getAllEntrenamientosRealizados();
    }

    public void insert(Ent_Realizado ent_realizado) {mRepository.insert(ent_realizado);}

    public LiveData<List<Ent_Realizado>> getAllEntrenamientosRealizados() { return listEntrenamientosRealizados; }
    public LiveData<List<Ent_Realizado>> getEntrenamientosFuerzaRealizados() { return mRepository.getEntrenamientosFuerzaRealizados(); }
    public LiveData<List<Ent_Realizado>> getEntrenamientosAerobicoRealizados() { return mRepository.getEntrenamientosAerobicosRealizados(); }

    public Ent_Realizado getEntRealizado(int id) throws InterruptedException {
        Ent_Realizado ent_realizado;
        ent_realizado = mRepository.getEntRealizado(id);
        return ent_realizado;
    }

    public void deleteEnt_Realizado(Ent_Realizado entrealizado) {
        mRepository.deleteEnt_Realizado(entrealizado);
    }
}