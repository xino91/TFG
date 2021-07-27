package com.example.apprpe;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.EjercicioDao;

import java.util.List;

public class EjercicioRepository {
/**
    private EjercicioDao ejercicioDao;
    private LiveData<List<Ejercicio>> listEjercicios;

    public EjercicioRepository(Application application) {
        RPERoomDatabase db = RPERoomDatabase.getInstanceDatabase(application);
        ejercicioDao = db.ejercicioDao();
        listEjercicios = ejercicioDao.getAlphabetizedEjercicios();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Ejercicio>> getAllEjercicios() {
        return listEjercicios;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(final Ejercicio ejercicio) {
        RPERoomDatabase.databaseWriteExecutor.execute(() -> {
            ejercicioDao.insert(ejercicio);
        });
    }

    public void deleteAll() {
        RPERoomDatabase.databaseWriteExecutor.execute(() -> {
            ejercicioDao.deleteAll();
        });
    }**/
}
