package com.example.rest;

import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.example.models.*;


import com.example.controls.dao.services.ConexionService;
import com.example.controls.dao.services.EstacionService;
import com.example.controls.dao.services.GraphService;


@Path("Conexion")
public class ConexionApi {
    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOperations() throws Exception {
        HashMap map = new HashMap<>();
        ConexionService ps = new ConexionService();

        try {
            map.put("msg", "OK");
            map.put("data", ps.listAll().toArray());

            if (ps.listAll().isEmpty()) {
                map.put("data", new Object[]{});
            }
 
            return Response.ok(map).build();
        } catch (Exception e) {
            HashMap<String, String> error = new HashMap<>();
            error.put("msg", "Error");
            error.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveConexion(HashMap map) {
        HashMap res = new HashMap<>();
        try {
            ConexionService ps = new ConexionService();
            EstacionService cd = new EstacionService();
            GraphService gr = new GraphService();
            Estacion estacionOrigen = cd.get(Integer.parseInt(map.get("estacionOrigen").toString()));
            Estacion estacionDestino = cd.get(Integer.parseInt(map.get("estacionDestino").toString()));

            if (estacionOrigen== null||estacionDestino==null) {
                res.put("msg", "Error");
                res.put("data", "NO existen Estacion");

                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                
            }

            ps.getConexion().setEstacionOrigen(Integer.parseInt(map.get("estacionOrigen").toString()));
            ps.getConexion().setEstacionDestino(Integer.parseInt(map.get("estacionDestino").toString()));
            ps.getConexion().setDistancia(Double.parseDouble(map.get("distancia").toString()));
            ps.getConexion().setTiempo(Double.parseDouble(map.get("tiempo").toString()));

            ps.save();
            res.put("msg", "OK");
            res.put("data", "Conexion actualizada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");

            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConexion(@PathParam("id") Integer id) throws Exception {
        HashMap map = new HashMap<>();
        ConexionService ps = new ConexionService();
        Conexion conexion = ps.get(id);

        if (conexion == null || conexion.getId() == null) {
            map.put("msg", "Error");
            map.put("data", "No existe esa conexion");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", conexion);

        return Response.ok(map).build();
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateConexion(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            ConexionService ps = new ConexionService();
            EstacionService cd = new EstacionService();
            GraphService gr = new GraphService();

            if (cd.get(Integer.parseInt(map.get("estacionOrigen").toString()))== null||cd.get(Integer.parseInt(map.get("estacionDestino").toString()))==null) {
                res.put("msg", "Error");
                res.put("data", "NO existen Estacion");

                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                
            }

            ps.setConexion(ps.get(Integer.parseInt(map.get("id").toString())));
            ps.getConexion().setEstacionOrigen(Integer.parseInt(map.get("estacionOrigen").toString()));
            ps.getConexion().setEstacionDestino(Integer.parseInt(map.get("estacionDestino").toString()));
            ps.getConexion().setDistancia(Double.parseDouble(map.get("distancia").toString()));
            ps.getConexion().setTiempo(Double.parseDouble(map.get("tiempo").toString()));

            Estacion estacionOrigen = cd.get(Integer.parseInt(map.get("estacionOrigen").toString()));
            Estacion estacionDestino = cd.get(Integer.parseInt(map.get("estacionDestino").toString()));

            gr.saveEstacionGraph(estacionOrigen, estacionDestino, ps.getConexion().getTiempo());


            ps.update();

            res.put("msg", "OK");
            res.put("data", "Conexion actualizada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePersons(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            ConexionService ps = new ConexionService();
            
            Conexion conexion = ps.get(Integer.parseInt(map.get("id").toString()));

            if (conexion == null || conexion.getId() == null) {
                res.put("msg", "Error");
                res.put("data", "No existe esa conexion");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            ps.setConexion(conexion);
            ps.delete();

            res.put("msg", "OK");
            res.put("data", "Conexion eliminada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }


}
