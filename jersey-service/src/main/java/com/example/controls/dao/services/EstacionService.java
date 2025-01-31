package com.example.controls.dao.services;

import com.example.controls.dao.EstacionDao;
import com.example.controls.tda.list.LinkedList;
import com.example.models.Estacion;

public class EstacionService {
    private EstacionDao estacion;

    public EstacionService() {
        this.estacion = new EstacionDao();
    }

    public Boolean save() throws Exception {
        return estacion.save();
    }

    public Boolean update() throws Exception {
        return estacion.update();
    }

    public Boolean delete() throws Exception {
        return estacion.delete();
    }

    public Estacion get(Integer id) throws Exception{
        return this.estacion.get(id);
    }

    public LinkedList listAll() {
        return estacion.getListAll();
    }

    public Estacion getEstacion() {
        return estacion.getEstacion();
    }

    public void setEstacion(Estacion estacion) {
        this.estacion.setEstacion(estacion);
    }

    // public LinkedList order(Integer typeOrder, String atributo) {
    //     return estacion.order(typeOrder, atributo);
    // }
}
