package com.example.controls.dao;

import com.example.controls.dao.implement.AdapterDao;
import com.example.controls.tda.list.LinkedList;
import com.example.models.Estacion;

public class EstacionDao extends AdapterDao<Estacion> {
    private Estacion estacion;
    private LinkedList listAll;

    public EstacionDao() {
        super(Estacion.class);
    }
    
    public Estacion getEstacion() {
        if(this.estacion == null) {
            this.estacion = new Estacion();
        }

        return this.estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
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
            estacion.setId(id);
            this.persist(this.estacion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            this.merge(this.estacion, this.estacion.getId()-1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete() throws Exception {
        try {
            this.delete(this.estacion.getId()-1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
