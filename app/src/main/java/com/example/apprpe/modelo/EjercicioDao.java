package com.example.apprpe.modelo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.apprpe.modelo.Ejercicio;

import java.util.List;
/*
@Dao
public interface EjercicioDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Ejercicio ejercicio);

    @Query("DELETE FROM ejercicio_table")
    void deleteAll();

    @Query("SELECT * from ejercicio_table")
    LiveData<List<Ejercicio>> getAlphabetizedEjercicios();

}
*/