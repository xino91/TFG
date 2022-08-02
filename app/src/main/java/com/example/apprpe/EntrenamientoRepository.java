package com.example.apprpe;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.modelo.Entrenamiento;
import com.example.apprpe.modelo.EntrenamientoConEjercicios;
import com.example.apprpe.modelo.EntrenamientoDao;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class EntrenamientoRepository {

        private EntrenamientoDao entrenamientoDao;
        private LiveData<List<Entrenamiento>> listSesion;
        private LiveData<List<Ejercicio>> listEntrenamientoConEjercicios;
        private List<Ejercicio> listEntrenamientoConEjercicios2;
        private Ejercicio _ejercicio;
        private Entrenamiento _entrenamiento;
        private LiveData<List<EntrenamientoConEjercicios>> sesionConEjercicios;

        public EntrenamientoRepository(Application application) {
            RPERoomDatabase db = RPERoomDatabase.getInstanceDatabase(application);
            entrenamientoDao = db.entrenamientoDao();
            listSesion = entrenamientoDao.getAllSesiones();
            _ejercicio = new Ejercicio();
            _entrenamiento = new Entrenamiento();
        }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        public LiveData<List<Entrenamiento>> getAllEntrenamientos() { return listSesion; }

        public LiveData<List<Ejercicio>> getEntrenamientoConEjercicios(int id) {
            listEntrenamientoConEjercicios = entrenamientoDao.getAllEjercicios_SesionId(id);
            return listEntrenamientoConEjercicios;
        }
    public List<Ejercicio> getListEntrenamientoConEjercicios(int id) throws InterruptedException {
        RPERoomDatabase.databaseWriteExecutor.execute(() ->{
            listEntrenamientoConEjercicios2 = entrenamientoDao.getAllEjerciciosList_SesionId(id);
        });
        RPERoomDatabase.databaseWriteExecutor.awaitTermination(50000, TimeUnit.MICROSECONDS);
        return listEntrenamientoConEjercicios2;
    }

        public Ejercicio getEjercicio(int id) throws InterruptedException {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                _ejercicio = entrenamientoDao.getEjercicio(id);
                //Log.e("REPOSITORY_NOMBRE", String.valueOf(_ejercicio.getNombre()));
                //Log.e("REPOSITORY_SETS", String.valueOf(_ejercicio.getSets()));
                //Log.e("REPOSITORY_REPES", String.valueOf(_ejercicio.getRepeticiones()));
                //Log.e("REPOSITORY_RPE", String.valueOf(_ejercicio.getRpe()));
            });
            RPERoomDatabase.databaseWriteExecutor.awaitTermination(50000, TimeUnit.MICROSECONDS);
            //Log.e("REPOSITORY_", String.valueOf(_ejercicio.getNombre()));
            return _ejercicio;
        }

        public Entrenamiento getEntrenamiento(int id) throws InterruptedException {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                _entrenamiento = entrenamientoDao.getEntrenamiento(id);
                //Log.e("REPOSITORY_NOMBRE", String.valueOf(_sesion.getNombre()));
                //Log.e("REPOSITORY_SETS", String.valueOf(_sesion.getSets()));
                //Log.e("REPOSITORY_REPES", String.valueOf(_sesion.getRepeticiones()));
                //Log.e("REPOSITORY_RPE", String.valueOf(_sesion.getRpe()));
            });
            RPERoomDatabase.databaseWriteExecutor.awaitTermination(50000, TimeUnit.MICROSECONDS);
            //Log.e("REPOSITORY_", String.valueOf(_sesion.getNombre()));
            return _entrenamiento;
        }

        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        public void insert(final Entrenamiento entrenamiento) {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                entrenamientoDao.insert(entrenamiento);
            });
        }

        public void insert(final Ejercicio ejercicio) {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                entrenamientoDao.insert(ejercicio);
            });
        }

        public void insert(Ent_Realizado ent_realizado) {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                entrenamientoDao.insert(ent_realizado);
            });
        }

        public void update_NumEjerciciosMas(final int id){
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                entrenamientoDao.update_NumEjerciciosMas(id);
            });
        }

        public void update_NumEjerciciosMenos(final int id){
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                entrenamientoDao.update_NumEjerciciosMenos(id);
            });
        }

        public void updateEjercicio(Ejercicio ejercicio) {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                entrenamientoDao.updateEjercicio(ejercicio);
            });
        }

        public void deleteAll() {
            RPERoomDatabase.databaseWriteExecutor.execute(() -> {
                entrenamientoDao.deleteAll();
            });
        }

        public void deleteEjercicio(Ejercicio ejercicio) {
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                entrenamientoDao.deleteEjercicio(ejercicio);
            });
        }

        public void deleteAllEjerciciosSesion(Entrenamiento entrenamiento) {
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                entrenamientoDao.deleteAllEjerciciosSesion(entrenamiento.getId());
            });
        }

        public void deleteSesion(Entrenamiento entrenamiento) {
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                entrenamientoDao.deleteSesion(entrenamiento);
            });
        }

        public void deleteEnt_Realizado(){
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                entrenamientoDao.deleteAllEnt_Realizados();
            });
        }
}
