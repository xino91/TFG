package com.example.apprpe.modelo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EntrenamientoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Entrenamiento entrenamiento);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Ejercicio ejercicio);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Ent_Realizado ent_realizado);

    @Query("UPDATE Entrenamiento_table SET Num_ejercicios = Num_ejercicios +1 WHERE Id = :id")
    void update_NumEjerciciosMas(int id);

    @Query("UPDATE Entrenamiento_table SET Num_ejercicios = Num_ejercicios -1 WHERE Id = :id")
    void update_NumEjerciciosMenos(int id);

    @Update
    void updateEjercicio(Ejercicio ejercicio);

    @Query("SELECT * FROM Entrenamiento_table WHERE Id=:id")
    Entrenamiento getEntrenamiento(int id);

    @Query("SELECT * FROM Ejercicio_table WHERE Id_Ejercicio=:id")
    Ejercicio getEjercicio(int id);

    @Query("SELECT * from Entrenamiento_table")
    LiveData<List<Entrenamiento>> getAllSesiones();
    @Query("SELECT * from Entrenamiento_table")
    List<Entrenamiento> getAllSesiones2();

    @Query("SELECT * FROM Entrenamiento_table WHERE Tipo='Fuerza'")
    LiveData<List<Entrenamiento>> getEntrenamientosFuerza();
    @Query("SELECT * FROM Entrenamiento_table WHERE Tipo='Aeróbico'")
    LiveData<List<Entrenamiento>> getEntrenamientosAerobico();

    @Query("SELECT * FROM entrealizado_table")
    LiveData<List<Ent_Realizado>> getAllEntrenamientosRealizados();

    @Transaction
    @Query("SELECT * FROM ejercicio_table WHERE entrenamiento_Id=:id")
    LiveData<List<Ejercicio>> getAllEjercicios_SesionId(int id);
    @Transaction
    @Query("SELECT * FROM ejercicio_table WHERE entrenamiento_Id=:id")
    List<Ejercicio> getAllEjerciciosList_SesionId(int id);

    @Transaction
    @Query("SELECT * FROM Entrenamiento_table")
    LiveData<List<EntrenamientoConEjercicios>> getAllSesionesConEjercicios();

    @Transaction
    @Query("SELECT * FROM Entrenamiento_table WHERE Id = :id")
    LiveData<List<EntrenamientoConEjercicios>> getAllSesionesConEjerciciosId(int id);

    @Delete
    void deleteEjercicio(Ejercicio ejercicio);

    @Delete
    void deleteSesion(Entrenamiento entrenamiento);

    @Query("DELETE FROM Entrenamiento_table")
    void deleteAll();

    @Query("DELETE FROM ejercicio_table WHERE entrenamiento_Id = :Id_sesion")
    void deleteAllEjerciciosSesion(int Id_sesion);

    @Query("DELETE FROM entrealizado_table")
    void deleteAllEnt_Realizados();

    @Delete
    void deleteAllEnt_Realizados(Ent_Realizado ent_realizado);

}