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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 *
 */
@RequestScoped
@ManagedBean(name = "recepty")
public class ReceptyService implements Serializable {

    private final ReceptyDAO receptyDao;

    private final ObrazkyDAO obrazkyDao;

    private Part pictures;

    private ReceptDTO recept;

    private final Logger logger = Logger.getLogger(ReceptyService.class.getName());

    public ReceptyService() {
        this.recept = new ReceptDTO();
        this.receptyDao = new ReceptyDAOImpl();
        this.obrazkyDao = new ObrazkyDAOImpl();
    }

    public ReceptDTO getRecept() {
        return recept;
    }

    public void setRecept(ReceptDTO recept) {
        this.recept = recept;
    }

    public Part getPictures() {
        return null;
    }

    public void setPictures(Part pictures) {
        this.pictures = pictures;
    }

    public void save() {
        long result = receptyDao.save(transform(recept));
        if (result > 0) {
            uploadFiles(result);
            logger.log(Level.INFO, ">>OK<<");
        }
        recept = new ReceptDTO();
    }

    public List<Recept> getRecepty() {
        return receptyDao.getRecepts();
    }

    private void uploadFiles(Long receptId) {
        if (pictures.getSize() > 0) {
            try {
                for (Part f : getAllParts(pictures)) {
                    final String fileName = f.getSubmittedFileName();
                    var picture = new Obrazek();
                    picture.setId(null);
                    picture.setPath("http://localhost:8080/Recepty/pictures/" + fileName);
                    picture.setRecept_id(receptId);
                    InputStream input = f.getInputStream();
                    final String savePath = System.getProperty("user.home") + File.separator + "pictures";
                    File theDir = new File(savePath);
                    if (!theDir.exists()) {
                        theDir.mkdirs();
                    }
                    String toFile =  savePath + File.separator + fileName;
                    FileOutputStream fos = new FileOutputStream(new File(toFile));
                    fos.write(input.readAllBytes());
                    fos.close();
                    obrazkyDao.save(picture);
                }
            } catch ( ServletException | IOException e) {
                e.printStackTrace();
            }
        }
        recept.setFiles(null);
    }

    public static Collection<Part> getAllParts(Part part) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
    }

    public Recept transform(ReceptDTO from) {
        Recept to = new Recept(from.getId(), from.getName(), from.getDescription());
        return to;
    }

}
