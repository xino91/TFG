package com.example.apprpe.modelo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

//Esta Clase sirve para especificar la relacion entre las dos tablas de la BD,
//ya que por motivos del funcionamiento de Android y UI no se recomienda hacerlo como en SQL (Clave Foránea)
public class SesionConEjercicios {
    @Embedded public Sesion sesion;
    @Relation(
            parentColumn = "Id",
            entityColumn = "Sesion_Id"
    )
    public List<Ejercicio> list_ejercicios;

    //Getter
    public int getId() { return sesion.getId(); }
    public String getNombre_Sesion() { return sesion.getNombre_Sesion(); }
    public int getNum_ejercicios() { return list_ejercicios.size(); }
}
