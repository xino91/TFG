
package com.example.apprpe.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName= "EntRealizado_table")
public class Ent_Realizado {

    @PrimaryKey(autoGenerate = true)
    private int Id;
    private Date Fecha;
    private int Duracion;
    private int Carga;
    private int hora_inicio;
    private int hora_finalizacion;
    private int ind_monotonia;
    private int ind_fatiga;

    public Ent_Realizado(Date fecha, int duracion, int carga, int hora_inicio, int hora_finalizacion, int ind_monotonia, int ind_fatiga) {
        Fecha = fecha;
        Duracion = duracion;
        Carga = carga;
        this.hora_inicio = hora_inicio;
        this.hora_finalizacion = hora_finalizacion;
        this.ind_monotonia = ind_monotonia;
        this.ind_fatiga = ind_fatiga;
    }

    public Ent_Realizado(){};

    public int getId() { return Id; }
    public Date getFecha() { return Fecha; }
    public int getDuracion() { return Duracion; }
    public int getCarga() { return Carga; }
    public int getHora_inicio() { return hora_inicio; }
    public int getHora_finalizacion() { return hora_finalizacion; }
    public int getInd_monotonia() { return ind_monotonia; }
    public int getInd_fatiga() { return ind_fatiga; }

    public void setId(int id) { Id = id; }
    public void setFecha(Date fecha) { Fecha = fecha; }
    public void setDuracion(int duracion) { Duracion = duracion; }
    public void setCarga(int carga) { Carga = carga; }
    public void setHora_inicio(int hora_inicio) { this.hora_inicio = hora_inicio; }
    public void setHora_finalizacion(int hora_finalizacion) { this.hora_finalizacion = hora_finalizacion; }
    public void setInd_monotonia(int ind_monotonia) { this.ind_monotonia = ind_monotonia; }
    public void setInd_fatiga(int ind_fatiga) { this.ind_fatiga = ind_fatiga; }
}
