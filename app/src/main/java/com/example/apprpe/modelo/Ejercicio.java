package com.example.apprpe.modelo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName= "Ejercicio_table")
public class Ejercicio {

    @PrimaryKey(autoGenerate = true)
    private int Id_Ejercicio;
    private String Nombre;
    private int Repeticiones;
    private int Sets;
    private int Rpe;
    @NonNull private int entrenamiento_Id;

    public Ejercicio(String nombre, int repeticiones, int sets, int rpe, int entrenamientoId) {
        Nombre = nombre;
        Repeticiones = repeticiones;
        Sets = sets;
        Rpe = rpe;
        entrenamiento_Id = entrenamientoId;
    }
    public Ejercicio(int id, String nombre, int repeticiones, int sets, int rpe, int entrenamientoId) {
        Id_Ejercicio = id;
        Nombre = nombre;
        Repeticiones = repeticiones;
        Sets = sets;
        Rpe = rpe;
        entrenamiento_Id = entrenamientoId;
    }

    public Ejercicio(){}

    public Ejercicio(Ejercicio ejercicio){
        Id_Ejercicio = ejercicio.getId_Ejercicio();
        Nombre = ejercicio.getNombre();
        Repeticiones = ejercicio.getRepeticiones();
        Sets = ejercicio.getSets();
        Rpe = ejercicio.getRpe();
        entrenamiento_Id = ejercicio.getEntrenamiento_Id();
    }

    public int getId_Ejercicio() { return Id_Ejercicio; }
    public void setId_Ejercicio(int id_ejercicio) { Id_Ejercicio = id_ejercicio; }

    public int getEntrenamiento_Id() { return entrenamiento_Id; }
    public void setEntrenamiento_Id(int entrenamiento_Id) { this.entrenamiento_Id = entrenamiento_Id; }

    public String getNombre() { return Nombre; }
    public void setNombre(String nombre) { Nombre = nombre; }

    public int getRepeticiones() { return Repeticiones; }
    public void setRepeticiones(int repeticiones) { Repeticiones = repeticiones; }

    public int getSets() { return Sets; }
    public void setSets(int sets) { Sets = sets; }

    public int getRpe() { return Rpe; }
    public void setRpe(int rpe) { Rpe = rpe; }
}
