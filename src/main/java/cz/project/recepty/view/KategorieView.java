/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.view;

import cz.project.recepty.beans.Kategorie;
import cz.project.recepty.service.IKategorieService;
import java.io.Serializable;
import java.util.List;
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

    private Kategorie detail;

    private List<Kategorie> categories;

    @EJB
    private IKategorieService service;

    @PostConstruct
    public void init() {
        kategorie = new Kategorie();
        categories = service.getCategories();
    }

    public Kategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    public Kategorie getDetail() {
        return detail;
    }

    public void setDetail(Kategorie detail) {
        this.detail = detail;
    }

    public List<Kategorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Kategorie> categories) {
        this.categories = categories;
    }
    
    public String uprav(Kategorie k) {
        k.setUpravit(true);
        this.detail = k;
        return null;
    }

    public void storno() {
        kategorie = new Kategorie();
    }

    //ulozeni nove kategorie
    public String save() {
        if (kategorie != null) {
            service.save(kategorie);
        }
        return null;
    }

    //ulozeni radku v tabulce
    public String saveEdit() {
        if (detail != null) {
            service.save(detail);
        }
        detail.setUpravit(false);
        return null;
    }

    public String remove(Long id) {
        service.remove(id);
        return null;
    }

}
