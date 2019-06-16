package com.example.ejemploroomexamen;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Medida implements Serializable{
    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo(name = "fecha")
    private String fecha;

    @ColumnInfo(name = "grasa")
    private int grasa;

    @ColumnInfo(name = "masaMuscular")
    private int masaMuscular;

    @ColumnInfo(name = "peso")
    private int peso;

    @ColumnInfo(name = "edadMetabolica")
    private int edadMetabolica;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getGrasa() {
        return grasa;
    }

    public void setGrasa(int grasa) {
        this.grasa = grasa;
    }

    public int getMasaMuscular() {
        return masaMuscular;
    }

    public void setMasaMuscular(int masaMuscular) {
        this.masaMuscular = masaMuscular;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getEdadMetabolica() {
        return edadMetabolica;
    }

    public void setEdadMetabolica(int edadMetabolica) {
        this.edadMetabolica = edadMetabolica;
    }
}

