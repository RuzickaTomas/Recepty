/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.view;

import cz.project.recepty.beans.Recept;
import cz.project.recepty.dto.ReceptDTO;
import cz.project.recepty.service.IReceptService;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Zobrazíme recepty nebo pouze jejich části.
 */
@ManagedBean(name = "recepty")
@ViewScoped
public class ReceptyView implements Serializable {

    private static final long serialVersionUID = 64516531564487651L;

    private boolean smazat = false; //nastavime promennou pro prepinatko

    private Long kategorieId;

    private IReceptService service;

    private List<Recept> recepty;
    
    @PostConstruct
    public void init() {
        recepty = this.service.getRecepty();
    }

    public boolean isSmazat() {
        return smazat;
    }

    public void setSmazat(boolean smazat) {
        this.smazat = smazat;
    }

    //nacteme vsechny recepty
    public List<Recept> getRecepty() {
        return this.recepty;
    }

    public String getPicture(ReceptDTO r) {
        return this.service.getPicture(r);
    }

    public String getPicture(Recept r) {
        return this.service.getPicture(r);
    }

    @EJB
    public void setService(IReceptService service) {
        this.service = service;
    }

    public Long getKategorieId() {
        return kategorieId;
    }

    public void setKategorieId(Long kategorieId) {
        this.kategorieId = kategorieId;
    }

    public void smaz() {
        this.smazat = !this.smazat;
    }

    //smazeme recept
    public void remove(Recept recept) {
        if (recept != null) { //overime jestli recept neni null
            this.service.remove(recept);    //zavolame metodu pro odstraneni
        }
    }
    
    public void filterRecepts() {
        this.recepty = service.getRecepty();
        if (recepty != null && kategorieId != null) {
            List<Recept> result = recepty.stream().filter(r -> r.getKategorieId().equals(this.kategorieId)).collect(Collectors.toList());
            recepty = result.isEmpty() ? recepty : result;
        }
    }

}
