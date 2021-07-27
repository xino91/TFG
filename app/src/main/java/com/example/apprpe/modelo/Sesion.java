package com.example.apprpe.modelo;

import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity(tableName= "Sesion_table")
public class Sesion {

    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String Nombre_Sesion;
    private int Num_ejercicios;
    private int Rpe_Sesion;
    private String Tipo_Dato;
    private int Duracion;

    public Sesion(int id, String nombre_Sesion, int num_ejercicios, int rpe_Sesion, String tipo_duracion, int duracion) {
        Id = id;
        Nombre_Sesion = nombre_Sesion;
        Num_ejercicios = num_ejercicios;
        Rpe_Sesion = rpe_Sesion;
        Tipo_Dato = tipo_duracion;
        Duracion = duracion;
    }
    public Sesion(int id, String nombre_Sesion, int num_ejercicios, int rpe_Sesion) {
        Id = id;
        Nombre_Sesion = nombre_Sesion;
        Num_ejercicios = num_ejercicios;
        Rpe_Sesion = rpe_Sesion;
    }
    public Sesion(String nombre_Sesion, int num_ejercicios, int rpe_Sesion) {
        Nombre_Sesion = nombre_Sesion;
        Num_ejercicios = num_ejercicios;
        Rpe_Sesion = rpe_Sesion;
    }
    public Sesion(String nombre_Sesion, int rpe_Sesion) {
        Nombre_Sesion = nombre_Sesion;
        Num_ejercicios = 0;
        Rpe_Sesion = rpe_Sesion;
    }
    public Sesion() {
        Nombre_Sesion = "";
        Num_ejercicios = 0;
        Rpe_Sesion = 0;
    }

    /**public Sesion(String nombre_sesion, int num_ejercicios, Ejercicio ejercicio, int rpe_sesion) {
        Nombre_Sesion = nombre_sesion;
        Num_ejercicios = num_ejercicios;
        list_Sesion.add(ejercicio);
        Rpe_Sesion = rpe_sesion;
    }

    public Sesion(String nombre_sesion, List<Ejercicio> list_sesion, int rpe_sesion) {
        Nombre_Sesion = nombre_sesion;
        list_Sesion = list_sesion;
        Num_ejercicios = list_sesion.size();
        Rpe_Sesion = rpe_sesion;
    }**/

    //Getter
    public int getId() { return Id; }
    public String getNombre_Sesion() { return Nombre_Sesion; }
    public int getNum_ejercicios() { return Num_ejercicios; }
    //public List<Ejercicio> getList_Sesion() { return list_Sesion; }
    public int getRpe_Sesion() { return Rpe_Sesion; }
    public String getTipo_Dato() { return Tipo_Dato; }
    public int getDuracion() { return Duracion; }

    //Setter
    public void setId(int id) { Id = id; }
    public void setNombre_Sesion(String nombre_Sesion) { Nombre_Sesion = nombre_Sesion; }
    public void setNum_ejercicios(int num_ejercicios) { Num_ejercicios = num_ejercicios; }
    //public void setList_Sesion(List<Ejercicio> list_Sesion) { this.list_Sesion = list_Sesion; }
    //public void setList_Sesion(Ejercicio ejercicio) { this.list_Sesion.add(ejercicio); }
    public void setRpe_Sesion(int rpe_Sesion) { Rpe_Sesion = rpe_Sesion; }
    public void setTipo_Dato(String tipo_Dato) { Tipo_Dato = tipo_Dato; }
    public void setDuracion(int duracion) { Duracion = duracion; }
}
