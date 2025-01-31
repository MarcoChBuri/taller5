package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.controls.tda.Graph.*;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        HashMap mapa = new HashMap<>();
        GraphLabelDirect graph = new GraphLabelDirect<>(5, String.class);
        
        try {
            graph.labelVertex(1, "marco");
            graph.labelVertex(2, "juan");
            graph.labelVertex(3, "luis");
            graph.labelVertex(4, "ana");
            graph.labelVertex(5, "angela");

            graph.addEdgeLabel("marco", "juan" , 2.0f);
            graph.drawGraph();
       

        } catch (Exception e) {
            mapa.put("msg", "OK");
            mapa.put("data", graph.toString());
            return Response.status(Status.BAD_REQUEST).entity(mapa).build();
 
        } 
        System.out.println(graph.toString());

        mapa.put("msg", "OK");
        mapa.put("data", graph.toString());


        return Response.ok(mapa).build();
    }
}
