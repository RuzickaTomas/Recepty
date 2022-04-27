package cz.project.recepty.view;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import cz.project.recepty.beans.Komentar;
import cz.project.recepty.service.IKomentarService;

@ManagedBean(name = "komentare")
@ViewScoped
public class KomentareView {

	private Komentar detail;
	
	private List<Komentar> komentare;
	
	@EJB
	private IKomentarService service;
	
	private boolean show;

	@PostConstruct
	public void init() {
		komentare = service.getComments();
	}

	public Komentar getDetail() {
		return detail;
	}

	public void setDetail(Komentar detail) {
		this.detail = detail;
	}

	public List<Komentar> getKomentare() {
		return komentare;
	}

	public void setKomentare(List<Komentar> komentare) {
		this.komentare = komentare;
	}
	
	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public void edit(Komentar kom) {
		this.detail = kom;
		this.show = true;
	}
	
	public void revoke() {
		detail.setReported(null);
		this.show = false;
		save();
	}
	
	public void retain() {
		detail.setValidTo(new Date());
		this.show = false;
		save();
	}
	
	public void storno() {
		this.show = false;
	}
	
	public void save() {
		service.save(detail);
	}
	
}
