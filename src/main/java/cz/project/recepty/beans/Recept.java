package cz.project.recepty.beans;

import java.io.Serializable;
import java.util.Objects;

/*
* Třída reprezentující tabulku recept
 */
public class Recept implements Serializable {

    private Long id;
    //jméno/název receptu
    private String name;
    //popis postupu
    private String description;
    //kategorie
    private Long kategorieId;

    public Recept(Long id, String name, String description, Long kategorieId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.kategorieId = kategorieId;
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

    public Long getKategorieId() {
        return kategorieId;
    }

    public void setKategorieId(Long kategorieId) {
        this.kategorieId = kategorieId;
    }
    
    

    @Override
    public int hashCode() {
        return Objects.hash(description, id, name, kategorieId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Recept other = (Recept) obj;
        return Objects.equals(description, other.description) && Objects.equals(id, other.id)
                && Objects.equals(name, other.name) && Objects.equals(kategorieId, other.kategorieId);
    }

    @Override
    public String toString() {
        return "Recept [id=" + id + ", name=" + name + ", description=" + description +", kategorieId=" + kategorieId + "]";
    }

}
