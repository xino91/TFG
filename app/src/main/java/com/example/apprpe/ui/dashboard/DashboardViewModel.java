package com.example.apprpe.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apprpe.EntrenamientoRepository;
import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.modelo.Entrenamiento;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private EntrenamientoRepository mRepository;
    private LiveData<List<Ent_Realizado>> listEntrenamientosRealizados;

    public DashboardViewModel(Application application) {
        super(application);
        mRepository = new EntrenamientoRepository(application);
        listEntrenamientosRealizados = mRepository.getAllEntrenamientosRealizados();
    }

    public LiveData<List<Ent_Realizado>> getAllEntrenamientosRealizados() { return listEntrenamientosRealizados; }
    //public LiveData<List<Ent_Realizado>> getEntrenamientosFuerzaRealizados() { return mRepository.getEntrenamientosFuerza(); }
    //public LiveData<List<Ent_Realizado>> getEntrenamientosAerobicoRealizados() { return mRepository.getEntrenamientosAerobico(); }
}