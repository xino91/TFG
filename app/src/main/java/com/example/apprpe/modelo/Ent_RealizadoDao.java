package com.example.apprpe.modelo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Ent_RealizadoDao {
    @Query("SELECT * FROM entrealizado_table ORDER BY Id DESC")
    LiveData<List<Ent_Realizado>> getAllEntrenamientosRealizados();
}
