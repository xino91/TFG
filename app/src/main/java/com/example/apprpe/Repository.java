package com.example.apprpe;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.apprpe.modelo.DB.RPERoomDatabase;
import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.modelo.Entrenamiento;
import com.example.apprpe.modelo.EntrenamientoConEjercicios;
import com.example.apprpe.modelo.RpeDao;
import com.example.apprpe.modelo.Peso;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Repository {

        private RpeDao rpeDao;
        private LiveData<List<Entrenamiento>> listEntrenamientos;
        private LiveData<List<Ent_Realizado>> listEntrenamietosRealizados;
        private LiveData<List<Ejercicio>> listEntrenamientoConEjercicios;
        private List<Ejercicio> listEntrenamientoConEjercicios2;
        private Ejercicio _ejercicio;
        private Entrenamiento _entrenamiento;
        private Ent_Realizado _entrealizado;
        private LiveData<List<EntrenamientoConEjercicios>> sesionConEjercicios;

        public Repository(Application application) {
            RPERoomDatabase db = RPERoomDatabase.getInstanceDatabase(application);
            rpeDao = db.entrenamientoDao();
            listEntrenamientos = rpeDao.getAllSesiones();
            listEntrenamietosRealizados = rpeDao.getAllEntrenamientosRealizados();
            _ejercicio = new Ejercicio();
            _entrenamiento = new Entrenamiento();
        }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        public LiveData<List<Entrenamiento>> getAllEntrenamientos() { return listEntrenamientos; }
        public LiveData<List<Entrenamiento>> getEntrenamientosFuerza() {
            return rpeDao.getEntrenamientosFuerza();
        }
        public LiveData<List<Entrenamiento>> getEntrenamientosAerobico() {
            return rpeDao.getEntrenamientosAerobico();
        }

        public LiveData<List<Ent_Realizado>> getAllEntrenamientosRealizados() { return listEntrenamietosRealizados; }
        public LiveData<List<Ent_Realizado>> getEntrenamientosFuerzaRealizados() {
            return rpeDao.getEntrenamientosFuerzaRealizados();
        }
        public LiveData<List<Ent_Realizado>> getAllEntrenamientosRealizadosOrderAsc() {
            return rpeDao.getAllEntrenamientosRealizadosOrderAsc();
        }
        public LiveData<List<Ent_Realizado>> getEntrenamientosRealizadosFiltro(int dias) {
            return rpeDao.getEntrenamientosRealizadosFiltro(dias);
        }
        public LiveData<Map<String, Integer>> getEntrenamientosCount() {
            return rpeDao.getEntrenamientosCount();
        }
        public LiveData<List<Ent_Realizado>> getEntrenamientosAerobicosRealizados() {
            return rpeDao.getEntrenamientosAerobicosRealizados();
        }

        public LiveData<List<Ejercicio>> getEntrenamientoConEjercicios(int id) {
            listEntrenamientoConEjercicios = rpeDao.getAllEjercicios_SesionId(id);
            return listEntrenamientoConEjercicios;
        }
        public List<Ejercicio> getListEntrenamientoConEjercicios(int id) throws InterruptedException {
        RPERoomDatabase.databaseWriteExecutor.execute(() ->{
            listEntrenamientoConEjercicios2 = rpeDao.getAllEjerciciosList_SesionId(id);
        });
        RPERoomDatabase.databaseWriteExecutor.awaitTermination(50000, TimeUnit.MICROSECONDS);
        return listEntrenamientoConEjercicios2;
        }

        public Ejercicio getEjercicio(int id) throws InterruptedException {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                _ejercicio = rpeDao.getEjercicio(id);
            });
            RPERoomDatabase.databaseWriteExecutor.awaitTermination(50000, TimeUnit.MICROSECONDS);
            return _ejercicio;
        }

        public Entrenamiento getEntrenamiento(int id) throws InterruptedException {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                _entrenamiento = rpeDao.getEntrenamiento(id);
            });
            RPERoomDatabase.databaseWriteExecutor.awaitTermination(50000, TimeUnit.MICROSECONDS);
            return _entrenamiento;
        }

    public Ent_Realizado getEntRealizado(int id) throws InterruptedException {
        RPERoomDatabase.databaseWriteExecutor.execute(() ->{
            _entrealizado = rpeDao.getEntRealizado(id);
        });
        RPERoomDatabase.databaseWriteExecutor.awaitTermination(50000, TimeUnit.MICROSECONDS);
        return _entrealizado;
    }

        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        public void insert(final Entrenamiento entrenamiento) {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                rpeDao.insert(entrenamiento);
            });
        }

        public void insert(final Ejercicio ejercicio) {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                rpeDao.insert(ejercicio);
                rpeDao.update_NumEjerciciosMas(ejercicio.getEntrenamiento_Id()); //ACTUALIZAMOS Numero Ejercicios del entrenamiento
            });
        }

        public void insert(Ent_Realizado ent_realizado) {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                rpeDao.insert(ent_realizado);
            });
        }

    public void insert(Peso peso) {
        RPERoomDatabase.databaseWriteExecutor.execute(() ->{
            rpeDao.insert(peso);
        });
    }

        public void updateEjercicio(Ejercicio ejercicio) {
            RPERoomDatabase.databaseWriteExecutor.execute(() ->{
                rpeDao.updateEjercicio(ejercicio);
            });
        }

        public void deleteAll() {
            RPERoomDatabase.databaseWriteExecutor.execute(() -> {
                rpeDao.deleteAll();
            });
        }

    public void deleteAllEntrenamientosRealizados() {
        RPERoomDatabase.databaseWriteExecutor.execute(() -> {
            rpeDao.deleteAllEntrenamientosRealizados();
        });
    }

        public void deleteEjercicio(Ejercicio ejercicio) {
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                rpeDao.deleteEjercicio(ejercicio);
                rpeDao.update_NumEjerciciosMenos(ejercicio.getEntrenamiento_Id()); //Actualizamos numero ejercicios del entrenamiento
            });
        }

        public void deleteAllEjerciciosSesion(Entrenamiento entrenamiento){
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                rpeDao.deleteAllEjerciciosSesion(entrenamiento.getId());
            });

        }

        public void deleteEntrenamiento(Entrenamiento entrenamiento) {
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                rpeDao.deleteSesion(entrenamiento);
            });
        }

        public void deleteEnt_Realizado(Ent_Realizado ent_realizado){
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                rpeDao.deleteEnt_Realizado(ent_realizado);
            });
        }

        public void intercambiarId(int id_anterior, int id_posterior) {
            RPERoomDatabase.databaseWriteExecutor.execute(()-> {
                rpeDao.intercambiarId(id_posterior+1, 2147483647);
                rpeDao.intercambiarId(id_anterior+1, id_posterior+1);
                rpeDao.intercambiarId(id_posterior+1, id_anterior+1);
        });

    }

    public LiveData<List<Peso>> getHistorialPesos() {
            return rpeDao.getHistorialPesos();
    }

    public LiveData<Float> getPesoMaximo(){
            return rpeDao.getPesoMax();
    }

    public LiveData<Float> getPesoMinimo(){
            return rpeDao.getPesoMin();
    }

    public LiveData<Float> getPesoActual(){
        return rpeDao.getPesoActual();
    }

    public LiveData<String> getFechaMaxima(){return rpeDao.getFechaMaxima();}
    public LiveData<String> getFechaMinima(){ return rpeDao.getFechaMinima();}

    public LiveData<Integer> getCountEntrenamientosRealizados(){ return rpeDao.getCountEntrenamientosRealizados();}
}
