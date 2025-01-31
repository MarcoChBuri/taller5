package com.example.models;
import java.util.Objects;
public class Conexion {
    public Integer id;
    private Integer estacionOrigen;
    private Integer estacionDestino;
    private double distancia;
    private double tiempo;

   

    public Conexion() {
        this.id = 0;
        this.estacionOrigen = 0;
        this.estacionDestino = 0;
        this.distancia = 0;
        this.tiempo = 0;
    
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEstacionOrigen() {
        return estacionOrigen;
    }

    public void setEstacionOrigen(Integer estacionOrigen) {
        this.estacionOrigen = estacionOrigen;
    }

    public Integer getEstacionDestino() {
        return estacionDestino;
    }

    public void setEstacionDestino(Integer estacionDestino) {
        this.estacionDestino = estacionDestino;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Conexion Conexion = (Conexion) obj;
        return id.equals(Conexion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
} 
