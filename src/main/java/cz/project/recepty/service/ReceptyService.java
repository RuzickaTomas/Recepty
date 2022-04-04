/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.service;

import cz.project.recepty.beans.Recept;
import cz.project.recepty.dao.ReceptyDAO;
import cz.project.recepty.dto.ReceptDTO;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * 
*/

@SessionScoped
@ManagedBean(value = "recepty")
public class ReceptyService implements Serializable {
  
    @Inject
    private ReceptyDAO receptyDao;
    
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
    
    public void save() {
        boolean result  = receptyDao.save(transform(recept));
        if (result) {
            logger.log(Level.INFO, ">>OK<<");
        }
        recept = new ReceptDTO();
    }
    
    public List<Recept> getRecepty() {
        return receptyDao.getRecepts();
    }
    
    
    public Recept transform(ReceptDTO from){
         final List<URL> urls = new ArrayList<>();
         for (String pictureUrl  : from.getPictures()) {
             try {
                 urls.add(new URL(pictureUrl));
             } catch (MalformedURLException ex) {
                 logger.log(Level.SEVERE, null, ex);
             }
                     
         }
         Recept to = new Recept(from.getId(), from.getName(),from.getDescription(), urls);
         return to;
    }
    
    
}
