package com.example.apprpe.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.modelo.Entrenamiento;
import com.example.apprpe.EntrenamientoRepository;


import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private EntrenamientoRepository mRepository;
    private LiveData<List<Entrenamiento>> listEntrenamientos;
    private LiveData<List<Ejercicio>> listEjercicios;
    private List<Ejercicio> listEjercicios2;
    private LiveData<List<com.example.apprpe.modelo.EntrenamientoConEjercicios>> EntrenamientoConEjercicios;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new EntrenamientoRepository(application);
        listEntrenamientos = mRepository.getAllEntrenamientos();
    }

    public LiveData<List<Entrenamiento>> getAllEntrenamientos() { return listEntrenamientos; }
    public LiveData<List<Entrenamiento>> getEntrenamientosFuerza() { return mRepository.getEntrenamientosFuerza(); }
    public LiveData<List<Entrenamiento>> getEntrenamientosAerobico() { return mRepository.getEntrenamientosAerobico(); }

    public LiveData<List<Ejercicio>> getEntrenamientoConEjercicios(int id) {
        listEjercicios = mRepository.getEntrenamientoConEjercicios(id);
        return listEjercicios;
    }

    public List<Ejercicio> getListEntrenamientoConEjercicios(int id) throws InterruptedException {
        listEjercicios2 = mRepository.getListEntrenamientoConEjercicios(id);
        return listEjercicios2;
    }

    public Ejercicio getEjercicio(int id) throws InterruptedException {
        Ejercicio ejercicio;
        ejercicio = mRepository.getEjercicio(id);
        return ejercicio;
    }
    public Entrenamiento getEntrenamiento(int id) throws InterruptedException {
        Entrenamiento entrenamiento;
        entrenamiento = mRepository.getEntrenamiento(id);
        return entrenamiento;
    }

    public void insert(Entrenamiento entrenamiento) { mRepository.insert(entrenamiento); }
    public void insert(Ejercicio ejercicio) { mRepository.insert(ejercicio); }
    public void insert(Ent_Realizado ent_realizado) {mRepository.insert(ent_realizado);}
    public void update_NumEjerciciosMas(int id) { mRepository.update_NumEjerciciosMas(id);}
    public void update_NumEjerciciosMenos(int id) { mRepository.update_NumEjerciciosMenos(id);}
    public void updateEjercicio(Ejercicio ejercicio) { mRepository.updateEjercicio(ejercicio); }
    public void deleteSesion(Entrenamiento entrenamiento) { mRepository.deleteSesion(entrenamiento); }
    public void deleteAllEjercicioSesion(Entrenamiento entrenamiento) { mRepository.deleteAllEjerciciosSesion(entrenamiento);}
    public void deleteEjercicio(Ejercicio ejercicio) {mRepository.deleteEjercicio(ejercicio); }
    public void deleteAllEnt_Realizado() {mRepository.deleteEnt_Realizado(); }
    public void deleteAll() { mRepository.deleteAll(); }


}
