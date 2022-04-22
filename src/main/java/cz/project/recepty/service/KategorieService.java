/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.service;

import cz.project.recepty.beans.Kategorie;
import cz.project.recepty.dao.KategorieDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author tomas
 */
@Stateless
public class KategorieService implements IKategorieService {
   
    private KategorieDAO kategorieDAO;
    
    @EJB
    public void setKategorieDao(KategorieDAO kategorieDAO) {
        this.kategorieDAO = kategorieDAO;
    }

    @Override
    public Kategorie getCategory(long id) {
        return kategorieDAO.getCategory(id);
    }

    @Override
    public List<Kategorie> getCategories() {
       return kategorieDAO.getCategories();
    }

    @Override
    public long save(Kategorie kategorie) {
        return kategorieDAO.save(kategorie);
    }

    @Override
    public boolean remove(long id) {
        return kategorieDAO.remove(id);
    }
    
}
