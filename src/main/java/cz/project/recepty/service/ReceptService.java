/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.service;

import cz.project.recepty.beans.Obrazek;
import cz.project.recepty.beans.Recept;
import cz.project.recepty.dao.ObrazkyDAO;
import cz.project.recepty.dao.ObrazkyDAOImpl;
import cz.project.recepty.dao.ReceptyDAO;
import cz.project.recepty.dao.ReceptyDAOImpl;
import cz.project.recepty.dto.ReceptDTO;
import cz.project.recepty.transform.TransformRecept;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.Part;

/**
 *
 *
 */
public class ReceptService {

    private final transient ReceptyDAO receptyDao;

    private final transient ObrazkyDAO obrazkyDao;

    public ReceptService() {
        this.receptyDao = new ReceptyDAOImpl();
        this.obrazkyDao = new ObrazkyDAOImpl();
    }

    public Part getPictures() {
        return null;
    }

    public long save(ReceptDTO recept) {
        long result = receptyDao.save(TransformRecept.transform(recept));
        return result;
    }

    public List<Recept> getRecepty() {
        return receptyDao.getRecepts();
    }
    
    public ReceptDTO getRecept(long receptId) {
        return TransformRecept.transform(receptyDao.getRecept(receptId));
    }

    public void uploadFile(Part part, long receptId) throws IOException {
                    final String fileName = part.getSubmittedFileName();
                    //cesta pro ulozeni obrazku
                    final String savePath = System.getProperty("user.home") + File.separator + "pictures";
                    final String filePath = savePath + File.separator + fileName;
                    Obrazek picture = new Obrazek();
                    picture.setId(null);
                    picture.setRecept_id(receptId);
                    InputStream input = part.getInputStream();
                    File theDir = new File(savePath);
                    if (!theDir.exists()) {
                        theDir.mkdirs();
                    }
                    picture.setPath(filePath);
                    picture.setSrc("http://localhost:8080/Recepty/rest/v1/obrazky/" + receptId);
                    FileOutputStream fos = new FileOutputStream(new File(filePath));
                    fos.write(input.readAllBytes());
                    fos.close();
                    obrazkyDao.save(picture);
    }

    public String getPicture(ReceptDTO r) {
        Recept transformed = TransformRecept.transform(r);
        return getPicture(transformed);
    }

    public String getPicture(Recept r) {
        Obrazek obr = obrazkyDao.getPictureByReceptId(r.getId());
        return obr == null ? "" : obr.getSrc();
    }

    public void remove(Recept recept) {
        if (recept != null) {
            receptyDao.remove(recept.getId());
        }
    }

}
