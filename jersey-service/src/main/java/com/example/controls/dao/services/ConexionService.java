package com.example.controls.dao.services;

import com.example.controls.dao.ConexionDao;
import com.example.controls.tda.list.LinkedList;
import com.example.models.Conexion;

public class ConexionService {
    private ConexionDao conexion;

    public ConexionService() {
        this.conexion = new ConexionDao();
    }

    public Boolean save() throws Exception {
        return conexion.save();
    }

    public Boolean update() throws Exception {
        return conexion.update();
    }

    public Boolean delete() throws Exception {
        return conexion.delete();
    }

    public Conexion get(Integer id) throws Exception{
        return this.get(id);
    }

    public LinkedList listAll() {
        return conexion.getListAll();
    }

    public Conexion getConexion() {
        return conexion.getConexion();
    }

    public void setConexion(Conexion conexion) {
        this.conexion.setConexion(conexion);
    }

    // public LinkedList order(Integer typeOrder, String atributo) {
    //     return conexion.order(typeOrder, atributo);
    // }
}
