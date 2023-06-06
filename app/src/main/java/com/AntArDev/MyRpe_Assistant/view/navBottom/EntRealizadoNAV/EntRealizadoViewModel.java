package com.AntArDev.MyRpe_Assistant.view.navBottom.EntRealizadoNAV;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.AntArDev.MyRpe_Assistant.modelo.Ent_Realizado;
import com.AntArDev.MyRpe_Assistant.Repository;

import java.util.List;

public class EntRealizadoViewModel extends AndroidViewModel {

    private Repository mRepository;
    private LiveData<List<Ent_Realizado>> listEntrenamientosRealizados;

    public EntRealizadoViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        listEntrenamientosRealizados = mRepository.getAllEntrenamientosRealizados();
    }

    public void insert(Ent_Realizado ent_realizado) {mRepository.insert(ent_realizado);}

    public LiveData<List<Ent_Realizado>> getAllEntrenamientosRealizados() { return listEntrenamientosRealizados; }

    public LiveData<List<Ent_Realizado>> getEntrenamientosRealizadosRange(String first, String second){
        return mRepository.getEntrenamientosRealizadosRange(first, second);
    }

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

    public void deleteTableEntrenamientosRealizados(){
        mRepository.deleteTableEntrenamientosRealizados();
    }

    public LiveData<String> getFechaMaxima(){
        return mRepository.getFechaMaxima();
    }

    public LiveData<String> getFechaMinima(){
        return mRepository.getFechaMinima();
    }

    public LiveData<Integer> getCountEntrenamientosRealizados(){ return mRepository.getCountEntrenamientosRealizados(); }

}