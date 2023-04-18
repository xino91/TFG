package com.example.apprpe.modelo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.MapInfo;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
import java.util.Map;

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

    @Query("SELECT * FROM entrealizado_table WHERE Id=:id")
    Ent_Realizado getEntRealizado(int id);

    @Query("SELECT * from Entrenamiento_table ORDER BY Id DESC")
    LiveData<List<Entrenamiento>> getAllSesiones();
    @Query("SELECT * from Entrenamiento_table ORDER BY Id ASC")
    LiveData<List<Entrenamiento>> getAllSesionesASC();

    @Query("SELECT * FROM entrenamiento_table WHERE Tipo='Fuerza' ORDER BY Id DESC")
    LiveData<List<Entrenamiento>> getEntrenamientosFuerza();
    @Query("SELECT * FROM Entrenamiento_table WHERE Tipo='Aeróbico' ORDER BY Id DESC")
    LiveData<List<Entrenamiento>> getEntrenamientosAerobico();

    @Query("SELECT * FROM entrealizado_table ORDER BY Fecha DESC")
    LiveData<List<Ent_Realizado>> getAllEntrenamientosRealizados();

    @Query("SELECT * FROM entrealizado_table ORDER BY Fecha ASC")
    LiveData<List<Ent_Realizado>> getAllEntrenamientosRealizadosOrderAsc();

    @Query("SELECT * FROM entrealizado_table ORDER BY Fecha DESC LIMIT :dias")
    LiveData<List<Ent_Realizado>> getEntrenamientosRealizadosFiltro(int dias);

    @Query("SELECT * FROM entrealizado_table WHERE tipo='Fuerza' ORDER BY Fecha DESC")
    LiveData<List<Ent_Realizado>> getEntrenamientosFuerzaRealizados();

    @Query("SELECT * FROM entrealizado_table WHERE tipo='Aeróbico' ORDER BY Fecha DESC")
    LiveData<List<Ent_Realizado>> getEntrenamientosAerobicosRealizados();

    @MapInfo(keyColumn = "nombre", valueColumn = "cantidad")
    @Query("SELECT nombre_entrenamiento AS nombre, COUNT(nombre_entrenamiento) AS cantidad FROM Entrealizado_table GROUP BY nombre_entrenamiento LIMIT 7")
    LiveData<Map<String, Integer>> getEntrenamientosCount();

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

    @Delete
    void deleteEnt_Realizado(Ent_Realizado ent_realizado);

    @Query("DELETE FROM Entrenamiento_table")
    void deleteAll();

    @Query("DELETE FROM ejercicio_table WHERE entrenamiento_Id = :Id_sesion")
    void deleteAllEjerciciosSesion(int Id_sesion);

    @Query("UPDATE entrenamiento_table SET Id=:posterior WHERE Id=:anterior")
    void intercambiarId(int anterior, int posterior);
}