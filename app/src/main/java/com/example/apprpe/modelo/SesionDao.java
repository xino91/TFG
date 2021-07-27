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
public interface SesionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Sesion sesion);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Ejercicio ejercicio);

    @Query("UPDATE Sesion_table SET Num_ejercicios = Num_ejercicios +1 WHERE Id = :id")
    void update_NumEjerciciosMas(int id);

    @Query("UPDATE Sesion_table SET Num_ejercicios = Num_ejercicios -1 WHERE Id = :id")
    void update_NumEjerciciosMenos(int id);

    @Update
    void updateEjercicio(Ejercicio ejercicio);

    @Query("SELECT * FROM Sesion_table WHERE Id=:id")
    Sesion getSesion(int id);

    @Query("SELECT * FROM Ejercicio_table WHERE Id_Ejercicio=:id")
    Ejercicio getEjercicio(int id);

    @Query("SELECT * from sesion_table")
    LiveData<List<Sesion>> getAllSesiones();

    @Transaction
    @Query("SELECT * FROM ejercicio_table WHERE Sesion_Id=:id")
    LiveData<List<Ejercicio>> getAllEjercicios_SesionId(int id);

    @Transaction
    @Query("SELECT * FROM sesion_table")
    List<SesionConEjercicios> getAllSesionesConEjercicios();

    @Transaction
    @Query("SELECT * FROM sesion_table WHERE Id = :id")
    LiveData<List<SesionConEjercicios>> getAllSesionesConEjerciciosId(int id);

    @Delete
    void deleteEjercicio(Ejercicio ejercicio);

    @Delete
    void deleteSesion(Sesion sesion);

    @Query("DELETE FROM sesion_table")
    void deleteAll();

    @Query("DELETE FROM ejercicio_table WHERE Sesion_Id = :Id_sesion")
    void deleteAllEjerciciosSesion(int Id_sesion);
}