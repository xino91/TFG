package com.AntArDev.MyRpe_Assistant.modelo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "Pesos_table")
public class Peso {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double peso;
    private Date fecha_registro;

    @Ignore
    public Peso(int id, double peso, Date fecha_registro) {
        this.id = id;
        this.peso = peso;
        this.fecha_registro = fecha_registro;
    }

    public Peso(double peso, Date fecha_registro) {
        this.peso = peso;
        this.fecha_registro = fecha_registro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
}
