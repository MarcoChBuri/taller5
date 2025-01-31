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


import com.example.controls.dao.services.EstacionService;
import com.example.models.Estacion;

@Path("Estacion")
public class EstacionApi {
    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOperations() throws Exception {
        HashMap map = new HashMap<>();
        EstacionService ps = new EstacionService();

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
    public Response saveEstacion(HashMap map) {
        HashMap res = new HashMap<>();
        
        try {
            EstacionService ps = new EstacionService();

            ps.getEstacion().setNombre(map.get("nombre").toString());
            ps.getEstacion().setLatitud(map.get("latitud").toString());
            ps.getEstacion().setLongitud(map.get("longitud").toString());
            ps.save();

            res.put("msg", "OK");
            res.put("data", "Estacion registrada correctamente");

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
    public Response getPerson(@PathParam("id") Integer id) throws Exception {
        HashMap map = new HashMap<>();
        EstacionService ps = new EstacionService();
        Estacion estacion = ps.get(id);

        if (estacion == null || estacion.getId() == null) {
            map.put("msg", "Error");
            map.put("data", "No existe esa Estacion");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", estacion);

        return Response.ok(map).build();
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEstacion(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            EstacionService ps = new EstacionService();

            ps.setEstacion(ps.get(Integer.parseInt(map.get("id").toString())));
            ps.getEstacion().setNombre(map.get("nombre").toString());
            ps.getEstacion().setLatitud(map.get("latitud").toString());
            ps.getEstacion().setLongitud(map.get("longitud").toString());



      
            ps.update();

            res.put("msg", "OK");
            res.put("data", "Estacion actualizada correctamente");

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
    public Response deleteEstacion(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            EstacionService ps = new EstacionService();
            
            Estacion Estacion = ps.get(Integer.parseInt(map.get("id").toString()));

            if (Estacion == null || Estacion.getId() == null) {
                res.put("msg", "Error");
                res.put("data", "No existe esa Estacion");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            ps.setEstacion(Estacion);
            ps.delete();

            res.put("msg", "OK");
            res.put("data", "Estacion eliminada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }


}
