  package com.example.controls.dao;

import com.example.controls.dao.implement.AdapterDao;
import com.example.controls.tda.list.LinkedList;
import com.example.models.Conexion;

public class ConexionDao extends AdapterDao<Conexion> {
    private Conexion conexion;
    private LinkedList listAll;

    public ConexionDao() {
        super(Conexion.class);
    }
    
    public Conexion getConexion() {
        if(this.conexion == null) {
            this.conexion = new Conexion();
        }

        return this.conexion;
    }

    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
    }

    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return this.listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() +1;
        try {
            conexion.setId(id);
            this.persist(this.conexion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            this.merge(this.conexion, this.conexion.getId()-1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete() throws Exception {
        try {
            this.delete(this.conexion.getId()-1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
