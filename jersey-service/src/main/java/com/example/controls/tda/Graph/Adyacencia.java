package com.example.controls.tda.Graph;
public class Adyacencia {
    private Integer destino;
    private Float weight;

    public Adyacencia(Integer destino, Float weight) {
        this.destino = destino;
        this.weight = weight;
    }

    public Adyacencia(){}

    public Integer getDestino() {
        return this.destino;
    }

    public void setDestino(Integer destino) {
        this.destino = destino;
    }

    public Float getWeight() {
        return this.weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

}