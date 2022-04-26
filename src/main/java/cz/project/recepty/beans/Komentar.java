package cz.project.recepty.beans;

import java.util.Objects;

public class Komentar {

	private Long id;
	//email majitele komentáøe
	private String email;
	//text komentáøe
	private String text;
	//id receptu který komentujeme
	private Long receptId;
	
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
	@Override
	public int hashCode() {
		return Objects.hash(email, id, receptId, text);
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
				&& Objects.equals(receptId, other.receptId) && Objects.equals(text, other.text);
	}
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", email=" + email + ", text=" + text + ", receptId=" + receptId + "]";
	}
	
	
	

}
