/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.rest;

import cz.project.recepty.beans.Obrazek;
import cz.project.recepty.dao.ObrazkyDAO;
import cz.project.recepty.dao.ObrazkyDAOImpl;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class ObrazkyServlet extends HttpServlet {

    private final ObrazkyDAO obrazkyDao = new ObrazkyDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        String[] pathSplit = path.split("/");
        Long id = Long.parseLong(pathSplit[1]);
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
        resp.getOutputStream().write(imageData);
    }

}
