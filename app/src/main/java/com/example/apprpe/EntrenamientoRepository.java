package com.example.apprpe;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.apprpe.modelo.DB.RPERoomDatabase;
import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.modelo.Entrenamiento;
import com.example.apprpe.modelo.EntrenamientoConEjercicios;
import com.example.apprpe.modelo.EntrenamientoDao;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class EntrenamientoRepository {

        private EntrenamientoDao entrenamientoDao;
        private LiveData<List<Entrenamiento>> listEntrenamientos;
        private LiveData<List<Ent_Realizado>> listEntrenamietosRealizados;
        private LiveData<List<Ejercicio>> listEntrenamientoConEjercicios;
        private List<Ejercicio> listEntrenamientoConEjercicios2;
        private Ejercicio _ejercicio;
        private Entrenamiento _entrenamiento;
        private Ent_Realizado _entrealizado;
        private LiveData<List<EntrenamientoConEjercicios>> sesionConEjercicios;

        public EntrenamientoRepository(Application application) {
            RPERoomDatabase db = RPERoomDatabase.getInstanceDatabase(application);
            entrenamientoDao = db.entrenamientoDao();
            listEntrenamientos = entrenamientoDao.getAllSesiones();
            listEntrenamietosRealizados = entrenamientoDao.getAllEntrenamientosRealizados();
            _ejercicio = new Ejercicio();
            _entrenamiento = new Entrenamiento();
        }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        public LiveData<List<Entrenamiento>> getAllEntrenamientos() { return listEntrenamientos; }
        public LiveData<List<Entrenamiento>> getEntrenamientosFuerza() {
            return entrenamientoDao.getEntrenamientosFuerza();
        }
        public LiveData<List<Entrenamiento>> getEntrenamientosAerobico() {
            return entrenamientoDao.getEntrenamientosAerobico();
        }

        public LiveData<List<Ent_Realizado>> getAllEntrenamientosRealizados() { return listEntrenamietosRealizados; }
        public LiveData<List<Ent_Realizado>> getEntrenamientosFuerzaRealizados() {
            return entrenamientoDao.getEntrenamientosFuerzaRealizados();
        }
        public LiveData<List<Ent_Realizado>> getEntrenamientosAerobicosRealizados() {
            return entrenamientoDao.getEntrenamientosAerobicosRealizados();
        }

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

    public Ent_Realizado getEntRealizado(int id) throws InterruptedException {
        RPERoomDatabase.databaseWriteExecutor.execute(() ->{
            _entrealizado = entrenamientoDao.getEntRealizado(id);
            //Log.e("REPOSITORY_NOMBRE", String.valueOf(_sesion.getNombre()));
            //Log.e("REPOSITORY_SETS", String.valueOf(_sesion.getSets()));
            //Log.e("REPOSITORY_REPES", String.valueOf(_sesion.getRepeticiones()));
            //Log.e("REPOSITORY_RPE", String.valueOf(_sesion.getRpe()));
        });
        RPERoomDatabase.databaseWriteExecutor.awaitTermination(50000, TimeUnit.MICROSECONDS);
        //Log.e("REPOSITORY_", String.valueOf(_sesion.getNombre()));
        return _entrealizado;
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
                entrenamientoDao.update_NumEjerciciosMas(ejercicio.getEntrenamiento_Id()); //ACTUALIZAMOS Numero Ejercicios del entrenamiento
            });
        }

        public void insert(Ent_Realizado ent_realizado) {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                entrenamientoDao.insert(ent_realizado);
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
                entrenamientoDao.update_NumEjerciciosMenos(ejercicio.getEntrenamiento_Id()); //Actualizamos numero ejercicios del entrenamiento
            });
        }

        public void deleteAllEjerciciosSesion(Entrenamiento entrenamiento){
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                entrenamientoDao.deleteAllEjerciciosSesion(entrenamiento.getId());
            });

        }

        public void deleteEntrenamiento(Entrenamiento entrenamiento) {
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                entrenamientoDao.deleteSesion(entrenamiento);
            });
        }

        public void deleteEnt_Realizado(Ent_Realizado ent_realizado){
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                entrenamientoDao.deleteEnt_Realizado(ent_realizado);
            });
        }

        public void intercambiarId(int id_anterior, int id_posterior) {
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                entrenamientoDao.intercambiarId(id_posterior+1, 2147483647);
                entrenamientoDao.intercambiarId(id_anterior+1, id_posterior+1);
                entrenamientoDao.intercambiarId(id_posterior+1, id_anterior+1);
        });

    }
}
