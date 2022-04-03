package cz.project.recepty.beans;

import java.net.URL;
import java.util.List;
import java.util.Objects;

public class Recept {
	
	public Long id;
	public String name;
	public String description;
	public final List<URL> pictures;

	public Recept(Long id, String name, String description, final List<URL> pictures) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.pictures = pictures;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<URL> getPictures() {
		return pictures;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name, pictures);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recept other = (Recept) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(pictures, other.pictures);
	}

	@Override
	public String toString() {
		return "Recept [id=" + id + ", name=" + name + ", description=" + description + ", pictures=" + pictures + "]";
	}
	
	

	


	
	
	
	
	
	
}
