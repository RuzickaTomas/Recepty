package cz.project.recepty.view;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import cz.project.recepty.beans.Komentar;
import cz.project.recepty.service.IKomentarService;

@Singleton
@ManagedBean(name = "komentare")
@ViewScoped
public class KomentareView {

	private Komentar detail;

	private List<Komentar> komentare;

	private Set<String> emails;

	private Long kategorieId;

	private volatile Long receptId;

	private volatile String email;

	@EJB
	private IKomentarService service;

	@PostConstruct
	public void init() {
		load();
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

	public Long getKategorieId() {
		return kategorieId;
	}

	public void setKategorieId(Long kategorieId) {
		this.kategorieId = kategorieId;
	}

	public Long getReceptId() {
		return receptId;
	}

	public void setReceptId(Long receptId) {
		this.receptId = receptId;
	}

	public Set<String> getEmails() {
		return emails;
	}

	public void setEmails(Set<String> emails) {
		this.emails = emails;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void edit(Komentar kom) {
		this.detail = kom;
		kom.setShow(true);
	}

	public void revoke() {
		detail.setReported(null);
		detail.setValidTo(null);
		this.detail.setShow(false);
		save();
	}

	public void retain() {
		detail.setValidTo(new Date());
		this.detail.setShow(false);
		save();
	}

	public void storno() {
		this.detail.setShow(false);
	}

	public void save() {
		service.save(detail);
	}

	public void filter() {
		load();
		List<Komentar> filter = getKomentare().stream()
				.filter(k -> k.getReceptId().equals(this.receptId) && k.getEmail().equals(this.email))
				.collect(Collectors.toList());
		if (!filter.isEmpty()) {
			setKomentare(filter);
		} 
	}

	public void load() {
		komentare = service.getComments();
		emails = komentare.stream().map(k -> k.getEmail()).collect(Collectors.toSet());
	}

}
