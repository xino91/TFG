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

public class ProgresoViewModel extends AndroidViewModel {

    private EntrenamientoRepository mRepository;
    private LiveData<List<Entrenamiento>> listEntrenamientos;

    public ProgresoViewModel(Application application) {
        super(application);
        mRepository = new EntrenamientoRepository(application);
        listEntrenamientos = mRepository.getAllEntrenamientos();
    }

    public LiveData<List<Entrenamiento>> getAllEntrenamientos() { return listEntrenamientos; }
}