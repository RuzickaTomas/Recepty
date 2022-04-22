/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cz.project.recepty.dao;

import cz.project.recepty.beans.Kategorie;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author tomas
 */
@Local
public interface KategorieDAO {

    /**
     * Ziskame kategorii podle jejiho id
     * @param id - cislo kategorie
     * @return Kategorie nebo null
     */
    public Kategorie getCategory(long id);

    
    /**
     * Ziskame seznam kategorii
     * @return List kategorii nebo null
     */
    public List<Kategorie> getCategories();

    /**
     * Ulozime kategorii a vratime id
     * @param kategorie - kategorie, ktera se ma ulozit
     * @return id &gt; 0 pokud je ulozeni uspesne pokud ne tak 0 
     */
    public long save(Kategorie kategorie);

    /**
     * Odstranime kategorii podle id
     * @param id - id kategorie, kterou chceme  odstranit
     * @return true pokud se povedlo jinak false
     */
    public boolean remove(long id);

}
