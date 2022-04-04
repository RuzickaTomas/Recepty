/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.service;

import cz.project.recepty.beans.Recept;
import cz.project.recepty.dao.ReceptyDAO;
import java.io.Serializable;
import java.util.List;
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
    
    public List<Recept> getRecepty() {
        return receptyDao.getRecepts();
    }
    
    
    
}
