package com.AntArDev.MyRpe_Assistant.view.navBottom.EntrenamientoNAV;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.AntArDev.MyRpe_Assistant.Repository;
import com.AntArDev.MyRpe_Assistant.modelo.Ejercicio;
import com.AntArDev.MyRpe_Assistant.modelo.Ent_Realizado;
import com.AntArDev.MyRpe_Assistant.modelo.Entrenamiento;
import com.AntArDev.MyRpe_Assistant.modelo.EntrenamientoConEjercicios;
import com.AntArDev.MyRpe_Assistant.modelo.Peso;

import java.util.List;

public class EntrenamientoViewModel extends AndroidViewModel {

    private Repository mRepository;
    private LiveData<List<Entrenamiento>> listEntrenamientos;
    private LiveData<List<Ejercicio>> listEjercicios;

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

    public List<Ejercicio> getListEntrenamientoConEjercicios(int id_entrenamiento) throws InterruptedException {
        return mRepository.getListEntrenamientoConEjercicios(id_entrenamiento);
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
    public void insert(Peso peso) { mRepository.insert(peso); }
    public void updateEjercicio(Ejercicio ejercicio) { mRepository.updateEjercicio(ejercicio); }
    public void deleteEntrenamiento(Entrenamiento entrenamiento) { mRepository.deleteEntrenamiento(entrenamiento); }
    public void deleteAllEjerciciosEntrenamiento(Entrenamiento entrenamiento) { mRepository.deleteAllEjerciciosSesion(entrenamiento);}
    public void deleteTableEntrenamiento() { mRepository.deleteTableEntrenamiento();}
    public void deleteTableEjercicios() { mRepository.deleteTableEjercicios();}
    public void deleteEjercicio(Ejercicio ejercicio) {mRepository.deleteEjercicio(ejercicio); }

    public LiveData<Float> getPesoMaximo(){
        return mRepository.getPesoMaximo();
    }

    public LiveData<Float> getPesoMinimo(){
        return mRepository.getPesoMinimo();
    }

    public LiveData<Float> getPesoActual(){
        return mRepository.getPesoActual();
    }

    public LiveData<List<EntrenamientoConEjercicios>> getAllSesionesConEjerciciosCross(){ return mRepository.getAllSesionesConEjerciciosCross();}

    public void resetDatosPorDefecto(List<Entrenamiento> entrenamientos, List<Ejercicio> ejercicios,
                                     List<Ent_Realizado> entrealizados, List<Peso> historialPeso){
        mRepository.resetDatosPorDefecto(entrenamientos, ejercicios, entrealizados, historialPeso);
    }
}
