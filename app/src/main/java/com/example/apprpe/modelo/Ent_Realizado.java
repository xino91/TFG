
package com.example.apprpe.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName= "EntRealizado_table")
public class Ent_Realizado {

    @PrimaryKey(autoGenerate = true)
    private int Id;
    private Date Fecha;
    private String FechaString;
    private long Duracion;
    private int Carga;
    private String hora_inicio;
    private String hora_finalizacion;
    private String tipo;
    private int ind_fatiga;
    private int rpe_objetivo;
    private int rpe_subjetivo;
    private int satisfacción;
    private int dolor;

    public Ent_Realizado(Date fecha, long duracion, String tipo, int carga, String hora_inicio, String hora_finalizacion, int ind_fatiga) {
        Fecha = fecha;
        FechaString = fecha.toString();
        Duracion = duracion;
        Carga = carga;
        this.hora_inicio = hora_inicio;
        this.hora_finalizacion = hora_finalizacion;
        this.tipo = tipo;
        this.ind_fatiga = ind_fatiga;
    }

    public Ent_Realizado(){};

    public int getId() { return Id; }
    public Date getFecha() { return Fecha; }
    public String getFechaString() {return FechaString;}
    public long getDuracion() { return Duracion; }
    public int getCarga() { return Carga; }
    public String getHora_inicio() { return hora_inicio; }
    public String getHora_finalizacion() { return hora_finalizacion; }
    public String getTipo() {return tipo;}
    public int getInd_fatiga() { return ind_fatiga; }
    public int getRpe_objetivo() {return rpe_objetivo;}
    public int getRpe_subjetivo() {return rpe_subjetivo;}
    public int getSatisfacción() {return satisfacción;}
    public int getDolor() {return dolor;}


    public void setId(int id) { Id = id; }
    public void setFecha(Date fecha) { Fecha = fecha; }
    public void setFechaString(String fecha){FechaString = fecha; }
    public void setDuracion(long duracion) { Duracion = duracion; }
    public void setCarga(int carga) { Carga = carga; }
    public void setHora_inicio(String hora_inicio) { this.hora_inicio = hora_inicio; }
    public void setHora_finalizacion(String hora_finalizacion) { this.hora_finalizacion = hora_finalizacion; }
    public void setInd_fatiga(int ind_fatiga) { this.ind_fatiga = ind_fatiga; }
    public void setRpe_objetivo(int rpe_objetivo) {this.rpe_objetivo = rpe_objetivo;}
    public void setRpe_subjetivo(int rpe_subjetivo) {this.rpe_subjetivo = rpe_subjetivo;}
    public void setDolor(int dolor) {this.dolor = dolor;}
    public void setSatisfacción(int satisfacción) {this.satisfacción = satisfacción;}
    public void setTipo(String tipo) {this.tipo = tipo;}
}
