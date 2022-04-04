/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author tomas
 */
@Path("/recepty")
class ReceptyResource {

    @POST
    public Response createRecept() {
        return null;
    }

    @PUT
    public Response updateRecept() {
        return null;
    }

    @GET
    @Path("{id}")
    public Response getRecept() {
        return null;
    }

    @DELETE
    @Path("{id}")
    public Response deleteRecept() {
        return null;
    }
}
