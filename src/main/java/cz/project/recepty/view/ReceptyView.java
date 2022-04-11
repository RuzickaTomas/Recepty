/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.view;

import cz.project.recepty.beans.Recept;
import cz.project.recepty.dto.ReceptDTO;
import cz.project.recepty.service.ReceptService;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * Zobrazíme recepty nebo pouze jejich části.
 */
@ManagedBean(name = "recepty")
@SessionScoped
public class ReceptyView implements Serializable{

    private Part pictures;

    private ReceptDTO recept;

    private ReceptDTO receptDetail;

    private boolean smazat = false;

    private static final Logger logger = Logger.getLogger(ReceptService.class.getName());

    private final ReceptService service;

    public ReceptyView() {
        this.recept = new ReceptDTO();
        this.service = new ReceptService();
    }

    public ReceptDTO getRecept() {
        return recept;
    }

    public void setRecept(ReceptDTO recept) {
        this.recept = recept;
    }

    public void setPictures(Part pictures) {
        this.pictures = pictures;
    }
    
    public Part getPictures() {
        return null;
    }

    public ReceptDTO getReceptDetail() {
        return receptDetail;
    }

    public void setReceptDetail(ReceptDTO receptDetail) {
        this.receptDetail = receptDetail;
    }

    public boolean isSmazat() {
        return smazat;
    }

    public void setSmazat(boolean smazat) {
        this.smazat = smazat;
    }

    public void smaz() {
        this.smazat = !this.smazat;
    }

    public void storno() {
        pictures = null;
        this.recept = new ReceptDTO();
    }

    public String save() {
        long result = this.service.save(recept);
        if (result > 0) {
            uploadFiles(result);
            logger.log(Level.INFO, "Recept " + this.recept.getName() + " by úspěšně uložen!" );
        }
        recept = new ReceptDTO();
        return "index?faces-redirect=true";
    }

    public List<Recept> getRecepty() {
        return this.service.getRecepty();
    }

    public void remove(Recept recept) {
        if (recept != null) {
            this.service.remove(recept);
        }
    }

    private void uploadFiles(Long receptId) {
        if (pictures.getSize() > 0) {
            try {
                for (Part f : getAllParts(pictures)) {
                    this.service.uploadFile(f, receptId);

                }
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
        recept.setFiles(null);
    }
    
    public String getPicture(ReceptDTO r) {
        return this.service.getPicture(r);
    }
    
    public String getPicture(Recept r) {
        return this.service.getPicture(r);
    }

    public void openDetail() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, String> params = ctx.getExternalContext().getRequestParameterMap();
        Long receptId = Long.parseLong(params.get("receptId"));
        logger.log(Level.INFO, "redirect to recept id " + receptId + " detail");
        receptDetail = this.service.getRecept(receptId);
    }

    public static Collection<Part> getAllParts(Part part) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
    }
}
