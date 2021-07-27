package com.example.apprpe.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Sesion;
import com.example.apprpe.modelo.SesionConEjercicios;
import com.example.apprpe.SesionRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private SesionRepository mRepository;
    private LiveData<List<Sesion>> listSesiones;
    private LiveData<List<Ejercicio>> listSesionesConEjercicios;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SesionRepository(application);
        listSesiones = mRepository.getAllSesiones();
    }

    public LiveData<List<Sesion>> getAllSesiones() { return listSesiones; }

    public LiveData<List<Ejercicio>> getSesionConEjercicios(int id){
        listSesionesConEjercicios = mRepository.getSesionConEjercicios(id);
        return listSesionesConEjercicios;
    }

    public Ejercicio getEjercicio(int id) throws InterruptedException {
        Ejercicio ejercicio;
        ejercicio = mRepository.getEjercicio(id);
        return ejercicio;
    }
    public Sesion getSesion(int id) throws InterruptedException {
        Sesion sesion;
        sesion = mRepository.getSesion(id);
        return sesion;
    }

    public void insert(Sesion sesion) { mRepository.insert(sesion); }
    public void insert(Ejercicio ejercicio) { mRepository.insert(ejercicio); }
    public void update_NumEjerciciosMas(int id) { mRepository.update_NumEjerciciosMas(id);}
    public void update_NumEjerciciosMenos(int id) { mRepository.update_NumEjerciciosMenos(id);}
    public void updateEjercicio(Ejercicio ejercicio) { mRepository.updateEjercicio(ejercicio); }
    public void deleteSesion(Sesion sesion) { mRepository.deleteSesion(sesion); }
    public void deleteAllEjercicioSesion(Sesion sesion) { mRepository.deleteAllEjerciciosSesion(sesion);}
    public void deleteEjercicio(Ejercicio ejercicio) {mRepository.deleteEjercicio(ejercicio); }
    public void deleteAll() { mRepository.deleteAll(); }


}
