package com.example.apprpe;

import static com.google.common.truth.Truth.assertThat;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import com.example.apprpe.modelo.DB.RPERoomDatabase;
import com.example.apprpe.modelo.Entrenamiento;
import com.example.apprpe.modelo.RpeDao;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RpeDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private RpeDao rpeDao;
    private RPERoomDatabase appDatabase;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, RPERoomDatabase.class).allowMainThreadQueries().build();
        rpeDao = appDatabase.entrenamientoDao();
    }
    @After
    public void closeDB(){
        appDatabase.close();
    }

    @Test
    public void insertAndGetListEntrenamiento() throws InterruptedException {
        Entrenamiento entrenamiento = new Entrenamiento(1,"Prueba", 0,5, "Fuerza");
        rpeDao.insert(entrenamiento);
        List<Entrenamiento> listentrenamientos = LiveDataTestUtil.getOrAwaitValue(rpeDao.getAllSesiones());
        assertThat(listentrenamientos.get(0).getId()).isEqualTo(entrenamiento.getId());
        assertThat(listentrenamientos.get(0).getNombre_Entrenamiento()).isEqualTo(entrenamiento.getNombre_Entrenamiento());
        assertThat(listentrenamientos.get(0).getNum_ejercicios()).isEqualTo(entrenamiento.getNum_ejercicios());
        assertThat(listentrenamientos.get(0).getRpe_Objetivo()).isEqualTo(entrenamiento.getRpe_Objetivo());
        assertThat(listentrenamientos.get(0).getTipo()).isEqualTo(entrenamiento.getTipo());
    }
    @Test
    public void insertAndGetEntrenamiento() throws InterruptedException {
        Entrenamiento entrenamiento = new Entrenamiento(1,"Prueba", 0,5, "Fuerza");
        rpeDao.insert(entrenamiento);
        Entrenamiento entrenamientoObtenido = rpeDao.getEntrenamiento(1);
        assertThat(entrenamientoObtenido.getId()).isEqualTo(entrenamiento.getId());
        assertThat(entrenamientoObtenido.getNombre_Entrenamiento()).isEqualTo(entrenamiento.getNombre_Entrenamiento());
        assertThat(entrenamientoObtenido.getNum_ejercicios()).isEqualTo(entrenamiento.getNum_ejercicios());
        assertThat(entrenamientoObtenido.getRpe_Objetivo()).isEqualTo(entrenamiento.getRpe_Objetivo());
        assertThat(entrenamientoObtenido.getTipo()).isEqualTo(entrenamiento.getTipo());
    }
    @Test
    public void insertIdRepeatedAndCheckConflictIgnore() throws InterruptedException {
        Entrenamiento entrenamiento = new Entrenamiento(1,"Prueba", 0,5, "Fuerza");
        Entrenamiento entrenamiento2 = new Entrenamiento(1,"Entrenamiento", 0,6, "Fuerza");
        rpeDao.insert(entrenamiento);
        rpeDao.insert(entrenamiento2);
        List<Entrenamiento> listEntrenamientos = LiveDataTestUtil.getOrAwaitValue(rpeDao.getAllSesiones());
        assertThat(listEntrenamientos.size()).isEqualTo(1);
        assertThat(listEntrenamientos.get(0).getNombre_Entrenamiento()).isEqualTo("Prueba");
    }
    @Test
    public void deleteEntrenamiento() throws InterruptedException {
        Entrenamiento entrenamiento = new Entrenamiento(1,"Prueba", 0,5, "Fuerza");
        rpeDao.insert(entrenamiento);
        List<Entrenamiento> lisEntrenamientos = LiveDataTestUtil.getOrAwaitValue(rpeDao.getAllSesiones());
        assertThat(lisEntrenamientos.size()).isEqualTo(1);
        rpeDao.deleteSesion(entrenamiento);
        lisEntrenamientos = LiveDataTestUtil.getOrAwaitValue(rpeDao.getAllSesiones());
        assertThat(lisEntrenamientos.size()).isEqualTo(0);
    }
    @Test
    public void deleteAll() throws InterruptedException {
        Entrenamiento entrenamiento = new Entrenamiento(1,"Prueba", 0,5, "Fuerza");
        Entrenamiento entrenamiento2 = new Entrenamiento(2,"Prueba2", 0,6, "Fuerza");
        rpeDao.insert(entrenamiento);
        rpeDao.insert(entrenamiento2);
        List<Entrenamiento> lisEntrenamientos = LiveDataTestUtil.getOrAwaitValue(rpeDao.getAllSesiones());
        assertThat(lisEntrenamientos.size()).isEqualTo(2);
        rpeDao.deleteAll();
        lisEntrenamientos = LiveDataTestUtil.getOrAwaitValue(rpeDao.getAllSesiones());
        assertThat(lisEntrenamientos.size()).isEqualTo(0);
    }
}