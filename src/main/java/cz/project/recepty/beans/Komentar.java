package cz.project.recepty.beans;

import java.util.Date;
import java.util.Objects;

public class Komentar {

	private Long id;
	//email majitele komentáøe
	private String email;
	//text komentáøe
	private String text;
	//id receptu který komentujeme
	private Long receptId;
	//datum schvaleni kometare
	private Date validFrom;
	//datum od kdy je neplatny
	private Date validTo;
	//datum nahlaseni komentare
	private Date reported;
	
	private boolean show;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Long getReceptId() {
		return receptId;
	}
	public void setReceptId(Long receptId) {
		this.receptId = receptId;
	}
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public Date getValidTo() {
		return validTo;
	}
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	public Date getReported() {
		return reported;
	}
	public void setReported(Date reported) {
		this.reported = reported;
	}
	
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public boolean isValidComment() {
		return validTo == null || validTo.after(new Date());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email, id, receptId, reported, text, validFrom, validTo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Komentar other = (Komentar) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(receptId, other.receptId) && Objects.equals(reported, other.reported)
				&& Objects.equals(text, other.text) && Objects.equals(validFrom, other.validFrom)
				&& Objects.equals(validTo, other.validTo);
	}
	@Override
	public String toString() {
		return "Komentar [id=" + id + ", email=" + email + ", text=" + text + ", receptId=" + receptId + ", validFrom="
				+ validFrom + ", validTo=" + validTo + ", reported=" + reported + "]";
	}
	
	
	

}
