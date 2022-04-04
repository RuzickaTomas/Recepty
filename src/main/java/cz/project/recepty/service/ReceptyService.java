/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.service;

import cz.project.recepty.beans.FilesRetriever;
import cz.project.recepty.beans.Obrazek;
import cz.project.recepty.beans.Recept;
import cz.project.recepty.dao.ObrazkyDAO;
import cz.project.recepty.dao.ReceptyDAO;
import cz.project.recepty.dto.ReceptDTO;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 *
 */
@SessionScoped
@ManagedBean(value = "recepty")
public class ReceptyService implements Serializable {

    @Inject
    private ReceptyDAO receptyDao;

    @Inject
    private ObrazkyDAO obrazkyDao;

    private Part pictures;

    private ReceptDTO recept;

    private final Logger logger = Logger.getLogger(ReceptyService.class.getName());

    public ReceptyService() {
        this.recept = new ReceptDTO();
    }

    public ReceptDTO getRecept() {
        return recept;
    }

    public void setRecept(ReceptDTO recept) {
        this.recept = recept;
    }

    public Part getPictures() {
        return pictures;
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
        try {
            for (Part f : FilesRetriever.getAllParts(pictures)) {
                final String fileName = f.getSubmittedFileName();
                var picture = new Obrazek();
                picture.setId(0);
                picture.setPath("http://localhost:8080/Recepty/pictures" + fileName);
                picture.setRecept_id(0);
                InputStream input = f.getInputStream();
                Files.copy(input, Paths.get(new URI("../pictures/" + fileName)), StandardCopyOption.REPLACE_EXISTING);
                obrazkyDao.save(picture);
            }
        } catch (URISyntaxException | ServletException | IOException e) {
            e.printStackTrace();
        }
        recept.setFiles(null);
    }

    public Recept transform(ReceptDTO from) {
        Recept to = new Recept(from.getId(), from.getName(), from.getDescription());
        return to;
    }

}
