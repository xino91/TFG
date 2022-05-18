package com.example.apprpe.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName= "Ejercicio_table")
public class Ejercicio {

    @PrimaryKey(autoGenerate = true)
    private int Id_Ejercicio;
    private String Nombre;
    private int Repeticiones;
    private int Sets;
    private int Rpe;
    private int Sesion_Id;

    public Ejercicio(String nombre, int repeticiones, int sets, int rpe, int sesion_id) {
        Nombre = nombre;
        Repeticiones = repeticiones;
        Sets = sets;
        Rpe = rpe;
        Sesion_Id = sesion_id;
    }

    public Ejercicio(){}

    public Ejercicio(Ejercicio ejercicio){
        Id_Ejercicio = ejercicio.getId_Ejercicio();
        Nombre = ejercicio.getNombre();
        Repeticiones = ejercicio.getRepeticiones();
        Sets = ejercicio.getSets();
        Rpe = ejercicio.getRpe();
        Sesion_Id = ejercicio.getSesion_Id();
    }

    public int getId_Ejercicio() { return Id_Ejercicio; }
    public void setId_Ejercicio(int id_ejercicio) { Id_Ejercicio = id_ejercicio; }

    public int getSesion_Id() { return Sesion_Id; }
    public void setSesion_Id(int sesion_Id) { Sesion_Id = sesion_Id; }

    public String getNombre() { return Nombre; }
    public void setNombre(String nombre) { Nombre = nombre; }

    public int getRepeticiones() { return Repeticiones; }
    public void setRepeticiones(int repeticiones) { Repeticiones = repeticiones; }

    public int getSets() { return Sets; }
    public void setSets(int sets) { Sets = sets; }

    public int getRpe() { return Rpe; }
    public void setRpe(int rpe) { Rpe = rpe; }
}
