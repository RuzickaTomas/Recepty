/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.rest;

import cz.project.recepty.beans.Obrazek;
import cz.project.recepty.dao.ObrazkyDAO;
import cz.project.recepty.dao.ObrazkyDAOImpl;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.enterprise.inject.Produces;
import javax.imageio.ImageIO;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author tomas
 */
@Path("/obrazky")
class ObrazkyResource {

    private final ObrazkyDAO obrazkyDao = new ObrazkyDAOImpl();

    @GET
    @Path("/{id}")
    public Response geObrazek(@PathParam("id") Long id) {
        Obrazek obr = obrazkyDao.getPictureByReceptId(id);
       byte[] imageData = null;
        try {
            BufferedImage image = ImageIO.read(new File(obr.getPath()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            imageData = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok(new ByteArrayInputStream(imageData)).build();
    }

}
