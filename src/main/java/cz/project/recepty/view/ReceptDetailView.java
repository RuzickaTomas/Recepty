/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.view;

import cz.project.recepty.beans.Kategorie;
import cz.project.recepty.beans.Komentar;
import cz.project.recepty.beans.Recept;
import cz.project.recepty.dto.ReceptDTO;
import cz.project.recepty.service.IKategorieService;
import cz.project.recepty.service.IKomentarService;
import cz.project.recepty.service.IReceptService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@ManagedBean(name = "detail")
@ViewScoped
public class ReceptDetailView implements Serializable {

	private static final long serialVersionUID = 6598496854978L;

	private ReceptDTO receptDetail;

	private Komentar komentar;

	@EJB
	private IReceptService service;

	@EJB
	private IKategorieService kategorieService;

	@EJB
	private IKomentarService komentarService;

	private boolean upravit;

	private String receptId;

	private List<Komentar> komentare;


	@PostConstruct
	public void init() {
		// vyhledani parametru
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String parameter = req.getParameter("id");
		// pokud parametr neexistuje
		if (parameter == null) {
			// podivame se pokud nebyl predan v mape
			parameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		}
		// zapiseme hodnotu do promenne
		this.receptId = parameter;
		Long _receptId = Long.parseLong(this.receptId);
		// prazdny komentar
		this.komentar = new Komentar();
		// nacteni receptu
		this.receptDetail = this.service.getRecept(_receptId);
		// nacteni komentaru
		loadComments();
	}

	public ReceptDTO getReceptDetail() {
		return receptDetail;
	}

	public void setReceptDetail(ReceptDTO receptDetail) {
		this.receptDetail = receptDetail;
	}

	public String getPicture(ReceptDTO r) {
		return this.service.getPicture(r);
	}

	public String getPicture(Recept r) {
		return this.service.getPicture(r);
	}

	public String getReceptId() {
		return receptId;
	}

	public void setReceptId(String receptId) {
		this.receptId = receptId;
	}

	public List<Komentar> getKomentare() {
		return komentare;
	}

	public void setKomentare(List<Komentar> komentare) {
		this.komentare = komentare;
	}

	public Komentar getKomentar() {
		return komentar;
	}

	public void setKomentar(Komentar komentar) {
		this.komentar = komentar;
	}

	public boolean isUpravit() {
		return upravit;
	}

	public void setUpravit(boolean upravit) {
		this.upravit = upravit;
	}

	public void uprav() {
		this.upravit = !this.upravit;
	}

	public void update() {
		this.service.save(receptDetail);
	}

	public void stornoComment() {
		this.komentar = new Komentar();
	}

	public void comment() {
		komentar.setReceptId(this.receptDetail.getId());
		this.komentarService.save(komentar);
		loadComments();
	}

	// ujisti se ze je prvne nacteny recept
	private void loadComments() {
		this.komentare = komentarService.getCommentsByRecept(this.receptDetail.getId());
	}

	public String getNazevKategorie(long id) {
		Kategorie result = this.kategorieService.getCategory(id);
		return result != null ? result.getName() : "Nezaøazeno";
	}

	public void report(Komentar kom) {
			kom.setReported(new Date());
			this.komentarService.save(kom);
			loadComments();
	}

}
