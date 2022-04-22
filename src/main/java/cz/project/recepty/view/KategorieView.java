/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.view;

import cz.project.recepty.beans.Kategorie;
import cz.project.recepty.service.IKategorieService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author tomas
 */
@ManagedBean(name = "kategorie")
@ViewScoped
public class KategorieView implements Serializable {

    private static final long serialVersionUID = 5441857524587L;

    private Kategorie kategorie;

    @EJB
    private IKategorieService service;
    
    @PostConstruct
    public void init() {

    }
    
    public void storno() {
        kategorie = new Kategorie();
    }
    
    public void save() {
        if (kategorie != null) {
            service.save(kategorie);
        }
    }

    public Kategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

}
