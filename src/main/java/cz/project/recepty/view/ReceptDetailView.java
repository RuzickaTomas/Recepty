/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.view;

import cz.project.recepty.beans.Kategorie;
import cz.project.recepty.beans.Recept;
import cz.project.recepty.dto.ReceptDTO;
import cz.project.recepty.service.IKategorieService;
import cz.project.recepty.service.IReceptService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@ManagedBean(name = "detail")
@ViewScoped
public class ReceptDetailView implements Serializable {

    private static final long serialVersionUID = 6598496854978L;

    private ReceptDTO receptDetail;

    @EJB
    private IReceptService service;
    
    @EJB
    private IKategorieService kategorieService;

    private boolean upravit;

    private String receptId;

    @PostConstruct
    public void init() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String parameter = req.getParameter("id");
        if (parameter == null) {
            parameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        }
        this.receptId = parameter;
        Long _receptId = Long.parseLong(this.receptId);
        this.receptDetail = this.service.getRecept(_receptId);
    }

    public ReceptDTO getReceptDetail() {
        return receptDetail;
    }

    public void setReceptDetail(ReceptDTO receptDetail) {
        this.receptDetail = receptDetail;
    }

    public String getPicture(ReceptDTO r) {
        return this.service.getPicture(r);
    }

    public String getPicture(Recept r) {
        return this.service.getPicture(r);
    }

    public String getReceptId() {
        return receptId;
    }

    public void setReceptId(String receptId) {
        this.receptId = receptId;
    }

    public boolean isUpravit() {
        return upravit;
    }

    public void setUpravit(boolean upravit) {
        this.upravit = upravit;
    }

    public void uprav() {
        this.upravit = !this.upravit;
    }

    public void update() {
        this.service.save(receptDetail);
    }
    
    public String getNazevKategorie(long id) {
    	Kategorie result = this.kategorieService.getCategory(id);
    	return result != null ? result.getName() : "Nezaøazeno";
    }

}
