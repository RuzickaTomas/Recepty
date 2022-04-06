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
 * Jednoduchá rest služba pro získávání obrázků
 */
public class ObrazkyServlet extends HttpServlet {

    private final ObrazkyDAO obrazkyDao = new ObrazkyDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //získáme informace o path
        String path = req.getPathInfo();
        String[] pathSplit = path.split("/");
        //dostaneme číselnou hodnotu a vytvoříme long
        Long id = Long.parseLong(pathSplit[1]);
        //získáme obrázek z databáze
        Obrazek obr = obrazkyDao.getPictureByReceptId(id);
        byte[] imageData = null;
        try {
            //načteme soubor obrázku z konkrétní adresy kde je uložen
            BufferedImage image = ImageIO.read(new File(obr.getPath()));
            //vytvoříme stream, tak abychom mohli zapsat změny
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //zapíšeme obrázek image do streamu baos
            ImageIO.write(image, "png", baos);
            //vrátíme obrázek jako pole bytů
            imageData = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //výsledek zapíšeme do odpovědi
        resp.getOutputStream().write(imageData);
    }

}
