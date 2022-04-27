/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import cz.project.recepty.beans.Komentar;
import cz.project.recepty.dao.KomentarDAO;

/**
 * Zpracování komentáøù
 * @author tomas
 */
@Stateless
public class KomentarService implements IKomentarService {

	private KomentarDAO komentarDAO;

	@EJB
	public void setKomentarDao(KomentarDAO komentarDAO) {
		this.komentarDAO = komentarDAO;
	}

	@Override
	public Komentar getComment(long id) {
		return komentarDAO.getComment(id);
	}

	@Override
	public List<Komentar> getComments() {
		return komentarDAO.getComments();
	}

	@Override
	public long save(Komentar komentar) {
		return komentarDAO.save(komentar);
	}

	@Override
	public boolean remove(long id) {
		return komentarDAO.remove(id);
	}

	@Override
	public List<Komentar> getCommentsByRecept(long id) {
		final List<Komentar> komentare = komentarDAO.getCommentsByRecept(id);
		return komentare;
	}

}
