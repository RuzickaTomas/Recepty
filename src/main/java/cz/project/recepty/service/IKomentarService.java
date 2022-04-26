/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cz.project.recepty.service;

import cz.project.recepty.beans.Kategorie;
import cz.project.recepty.beans.Komentar;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author tomas
 */
@Local
public interface IKomentarService {
	 /**
     * Ziskame komentar podle  id
     * @param id - cislo komentare
     * @return Komentar nebo null
     */
    public Komentar getComment(long id);

    
    /**
     * Ziskame seznam komentaru
     * @return List komentaru nebo null
     */
    public List<Komentar> getComments();

	/**
	 * Ziskame seznam komentaru podle id receptu
	 * 
	 * @return List komentaru nebo null
	 */
	public List<Komentar> getCommentsByRecept(long id);
    
    /**
     * Ulozime komentar a vratime id
     * @param komentar - komentar, ktery se ma ulozit
     * @return id &gt; 0 pokud je ulozeni uspesne pokud ne tak 0 
     */
    public long save(Komentar komentar);

    /**
     * Odstranime komentar podle id
     * @param id - id komentare, ktery chceme  odstranit
     * @return true pokud se povedlo jinak false
     */
    public boolean remove(long id);
}
