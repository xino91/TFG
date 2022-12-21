package com.example.apprpe.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName= "Entrenamiento_table")
public class Entrenamiento {

    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String Nombre_Entrenamiento;
    private int Num_ejercicios;
    private int Rpe_Sesion;
    private String Tipo;

    public Entrenamiento(int id, String nombre_Entrenamiento, int num_ejercicios, int rpe_Sesion, String tipo, int duracion) {
        Id = id;
        Nombre_Entrenamiento = nombre_Entrenamiento;
        Num_ejercicios = num_ejercicios;
        Rpe_Sesion = rpe_Sesion;
        Tipo = tipo;
    }
    public Entrenamiento(int id, String nombre_Entrenamiento, int num_ejercicios, int rpe_Sesion) {
        Id = id;
        Nombre_Entrenamiento = nombre_Entrenamiento;
        Num_ejercicios = num_ejercicios;
        Rpe_Sesion = rpe_Sesion;
    }
    public Entrenamiento(String nombre_Entrenamiento, int num_ejercicios, int rpe_Sesion) {
        Nombre_Entrenamiento = nombre_Entrenamiento;
        Num_ejercicios = num_ejercicios;
        Rpe_Sesion = rpe_Sesion;
    }
    public Entrenamiento(String nombre_Entrenamiento, int rpe_Sesion) {
        Nombre_Entrenamiento = nombre_Entrenamiento;
        Num_ejercicios = 0;
        Rpe_Sesion = rpe_Sesion;
    }
    public Entrenamiento() {
        Nombre_Entrenamiento = "";
        Num_ejercicios = 0;
        Rpe_Sesion = 0;
    }

    //Getter
    public int getId() { return Id; }
    public String getNombre_Entrenamiento() { return Nombre_Entrenamiento; }
    public int getNum_ejercicios() { return Num_ejercicios; }
    //public List<Ejercicio> getList_Sesion() { return list_Sesion; }
    public int getRpe_Sesion() { return Rpe_Sesion; }
    public String getTipo() { return Tipo; }

    //Setter
    public void setId(int id) { Id = id; }
    public void setNombre_Entrenamiento(String nombre_Sesion) { Nombre_Entrenamiento = nombre_Sesion; }
    public void setNum_ejercicios(int num_ejercicios) { Num_ejercicios = num_ejercicios; }
    //public void setList_Sesion(List<Ejercicio> list_Sesion) { this.list_Sesion = list_Sesion; }
    //public void setList_Sesion(Ejercicio ejercicio) { this.list_Sesion.add(ejercicio); }
    public void setRpe_Sesion(int rpe_Sesion) { Rpe_Sesion = rpe_Sesion; }
    public void setTipo(String tipo) { Tipo = tipo; }
}
