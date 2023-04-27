package com.example.apprpe.view.navBottom.EntrenamientoNAV;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Entrenamiento;
import com.example.apprpe.Repository;


import java.util.List;

public class EntrenamientoViewModel extends AndroidViewModel {

    private Repository mRepository;
    private LiveData<List<Entrenamiento>> listEntrenamientos;
    private LiveData<List<Ejercicio>> listEjercicios;
    private List<Ejercicio> listEjercicios2;
    private LiveData<List<com.example.apprpe.modelo.EntrenamientoConEjercicios> > EntrenamientoConEjercicios;

    public EntrenamientoViewModel(@NonNull Application application) throws InterruptedException {
        super(application);
        mRepository = new Repository(application);
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

    public void intercambiarId(int id_anterior, int id_posterior){
        mRepository.intercambiarId(id_anterior, id_posterior);
    }

    public void insert(Entrenamiento entrenamiento) { mRepository.insert(entrenamiento); }
    public void insert(Ejercicio ejercicio) { mRepository.insert(ejercicio); }
    public void updateEjercicio(Ejercicio ejercicio) { mRepository.updateEjercicio(ejercicio); }
    public void deleteEntrenamiento(Entrenamiento entrenamiento) { mRepository.deleteEntrenamiento(entrenamiento); }
    public void deleteAllEjerciciosEntrenamiento(Entrenamiento entrenamiento) { mRepository.deleteAllEjerciciosSesion(entrenamiento);}
    public void deleteEjercicio(Ejercicio ejercicio) {mRepository.deleteEjercicio(ejercicio); }

    public LiveData<Float> getPesoMaximo(){
        return mRepository.getPesoMaximo();
    }

    public LiveData<Float> getPesoMinimo(){
        return mRepository.getPesoMinimo();
    }
}
