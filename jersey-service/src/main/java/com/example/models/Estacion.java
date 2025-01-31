package com.example.models;
import java.util.Objects;

public class Estacion {
    private Integer id;
    private String nombre;
    private String latitud;
    private String longitud;

 

    public Estacion() {
        this.id = 0;
        this.nombre = "";
        this.latitud= "";
        this.longitud = "";
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }
    public String getLongitud() {
        return this.longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Estacion Estacion = (Estacion) obj;
        return id.equals(Estacion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
