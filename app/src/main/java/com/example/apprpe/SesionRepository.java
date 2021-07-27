package com.example.apprpe;

import android.app.Application;

import android.util.Log;
import androidx.lifecycle.LiveData;

import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Sesion;
import com.example.apprpe.modelo.SesionDao;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SesionRepository {

        private SesionDao sesionDao;
        private LiveData<List<Sesion>> listSesion;
        private LiveData<List<Ejercicio>> listSesionConEjercicios;
        private Ejercicio _ejercicio;
        private Sesion _sesion;

        public SesionRepository(Application application) {
            RPERoomDatabase db = RPERoomDatabase.getInstanceDatabase(application);
            sesionDao = db.sesionDao();
            listSesion = sesionDao.getAllSesiones();
            _ejercicio = new Ejercicio();
            _sesion = new Sesion();
        }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        public LiveData<List<Sesion>> getAllSesiones() {
            return listSesion;
        }

        public LiveData<List<Ejercicio>> getSesionConEjercicios(int id) {
            listSesionConEjercicios = sesionDao.getAllEjercicios_SesionId(id);
            return listSesionConEjercicios;
        }

        public Ejercicio getEjercicio(int id) throws InterruptedException {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                _ejercicio = sesionDao.getEjercicio(id);
                //Log.e("REPOSITORY_NOMBRE", String.valueOf(_ejercicio.getNombre()));
                //Log.e("REPOSITORY_SETS", String.valueOf(_ejercicio.getSets()));
                //Log.e("REPOSITORY_REPES", String.valueOf(_ejercicio.getRepeticiones()));
                //Log.e("REPOSITORY_RPE", String.valueOf(_ejercicio.getRpe()));
            });
            RPERoomDatabase.databaseWriteExecutor.awaitTermination(50000, TimeUnit.MICROSECONDS);
            //Log.e("REPOSITORY_", String.valueOf(_ejercicio.getNombre()));
            return _ejercicio;
        }

        public Sesion getSesion(int id) throws InterruptedException {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                _sesion = sesionDao.getSesion(id);
                //Log.e("REPOSITORY_NOMBRE", String.valueOf(_sesion.getNombre()));
                //Log.e("REPOSITORY_SETS", String.valueOf(_sesion.getSets()));
                //Log.e("REPOSITORY_REPES", String.valueOf(_sesion.getRepeticiones()));
                //Log.e("REPOSITORY_RPE", String.valueOf(_sesion.getRpe()));
            });
            RPERoomDatabase.databaseWriteExecutor.awaitTermination(50000, TimeUnit.MICROSECONDS);
            //Log.e("REPOSITORY_", String.valueOf(_sesion.getNombre()));
            return _sesion;
        }

        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        public void insert(final Sesion sesion) {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                sesionDao.insert(sesion);
            });
        }

        public void insert(final Ejercicio ejercicio) {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                sesionDao.insert(ejercicio);
            });
        }

        public void update_NumEjerciciosMas(final int id){
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                sesionDao.update_NumEjerciciosMas(id);
            });
        }

        public void update_NumEjerciciosMenos(final int id){
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                sesionDao.update_NumEjerciciosMenos(id);
            });
        }

        public void updateEjercicio(Ejercicio ejercicio) {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                sesionDao.updateEjercicio(ejercicio);
            });
        }

        public void deleteAll() {
            RPERoomDatabase.databaseWriteExecutor.execute(() -> {
                sesionDao.deleteAll();
            });
        }

        public void deleteEjercicio(Ejercicio ejercicio) {
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                sesionDao.deleteEjercicio(ejercicio);
            });
        }

        public void deleteAllEjerciciosSesion(Sesion sesion) {
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                sesionDao.deleteAllEjerciciosSesion(sesion.getId());
            });
        }

        public void deleteSesion(Sesion sesion) {
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                sesionDao.deleteSesion(sesion);
            });
        }
}
