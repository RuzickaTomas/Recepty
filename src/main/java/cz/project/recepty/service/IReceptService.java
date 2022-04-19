/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cz.project.recepty.service;

import cz.project.recepty.beans.Recept;
import cz.project.recepty.dto.ReceptDTO;
import java.io.IOException;
import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.Part;

/**
 *
 */
@Local
public interface IReceptService {

    public long save(ReceptDTO recept);

    public List<Recept> getRecepty();

    public ReceptDTO getRecept(long receptId);

    public void uploadFile(Part part, long receptId) throws IOException;

    public String getPicture(ReceptDTO r);

    public String getPicture(Recept r);
    
    public void remove(Recept recept);
}
