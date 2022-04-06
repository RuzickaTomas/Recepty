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
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 *
 */
@SessionScoped
@ManagedBean(name = "recepty")
public class ReceptyService implements Serializable {

    private ReceptyDAO receptyDao;

    private ObrazkyDAO obrazkyDao;

    private Part pictures;

    private ReceptDTO recept;

    private ReceptDTO receptDetail;   
    
    private transient final Logger logger = Logger.getLogger(ReceptyService.class.getName());

    public ReceptyService() {
    }

    @PostConstruct
    public void init() {
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
        long result = receptyDao.save(TransformRecept.transform(recept));
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
                    //cesta pro ulozeni obrazku
                    final String savePath = System.getProperty("user.home") + File.separator + "pictures";
                    final String filePath = savePath + File.separator + fileName;
                    var picture = new Obrazek();
                    picture.setId(null);
                    picture.setRecept_id(receptId);
                    InputStream input = f.getInputStream();
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
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
        recept.setFiles(null);
    }

    public String getPicture(ReceptDTO r){
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

    public ReceptDTO getReceptDetail() {
        return receptDetail;
    }

    public void setReceptDetail(ReceptDTO receptDetail) {
        this.receptDetail = receptDetail;
    }
   
    public void openDetail() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, String> params = ctx.getExternalContext().getRequestParameterMap();
        Long receptId = Long.parseLong(params.get("receptId"));
        receptDetail = TransformRecept.transform(receptyDao.getRecept(receptId));
    }

    public static Collection<Part> getAllParts(Part part) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
    }

}
