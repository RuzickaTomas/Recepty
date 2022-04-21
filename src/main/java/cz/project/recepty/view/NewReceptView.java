/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.view;

import cz.project.recepty.dto.ReceptDTO;
import cz.project.recepty.service.IReceptService;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * View pro zalozeni noveho receptu
 */
@ManagedBean(name = "createRecept")
@ViewScoped
public class NewReceptView implements Serializable {

    private static final long serialVersionUID = 1645461651L;

    private ReceptDTO recept;

    @EJB
    private IReceptService service;

    private Part pictures;

    private static final Logger logger = Logger.getLogger(ReceptyView.class.getName());

    @PostConstruct
    public void init() {
        this.recept = new ReceptDTO();
    }

    /**
     * Storno noveho receptu
     */
    public void storno() {
        pictures = null;
        this.recept = new ReceptDTO();
    }

    /**
     * Ulozime nove vytvoreny recept
     * @return cesta kam budeme prestmerovani - (index.xhtml) 
     */
    public String save() {
        long result = this.service.save(recept);    //zavolame funkci pro ulozeni
        if (result > 0) {   // pokud se vrati cislo rozdilne od nuly
            uploadFiles(result);    // nahrajeme obrazky, jakon parametr je id receptu
            logger.log(Level.INFO, "Recept " + this.recept.getName() + " by úspěšně uložen!");
        }
        recept = new ReceptDTO();   // vytvorime novou instanci a pridame ji do promenne
        return "index?faces-redirect=true"; // vratime cil kam chceme prejit
    }

    /**
     * Nahrajeme soubory a zaradime je pod id
     */
    private void uploadFiles(Long receptId) {
        if (pictures.getSize() > 0) { // zkontrolujeme zda nahraveme neprazdny soubor
            try {
                for (Part f : getAllParts(pictures)) {  // kazdy obrazek projdeme ve smycce
                    this.service.uploadFile(f, receptId); // zavolame metodu pro nahrani obrazku
                }
            } catch (ServletException | IOException e) {
                e.printStackTrace();    // v pripade chyby pri nahravani vyhodime vyjimku
            }
        }
        recept.setFiles(null);  // nastavime soubory na null
    }

    public void setPictures(Part pictures) {
        this.pictures = pictures;
    }

    public Part getPictures() {
        return null;
    }

    public ReceptDTO getRecept() {
        return recept;
    }

    public void setRecept(ReceptDTO recept) {
        this.recept = recept;
    }

    private static Collection<Part> getAllParts(Part part) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
    }
}
