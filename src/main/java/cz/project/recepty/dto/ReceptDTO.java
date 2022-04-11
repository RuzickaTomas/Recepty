/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.dto;

import java.util.Objects;
import javax.servlet.http.Part;

/**
 * Jednoduchá přepravka, pro uložení dat jmenovitě z formuláře pro vytvoření
 * receptu
 */
public class ReceptDTO {

    private Long id;
    private String name; // název receptu
    private String description; // popis receptu resp. postupu
    private Part files; // soubory (obrázky) které chceme nahrát

    public ReceptDTO() {
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

    public Part getFiles() {
        return this.files;
    }

    public void setFiles(Part files) {
        this.files = files;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, files, id, name);
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
        ReceptDTO other = (ReceptDTO) obj;
        return Objects.equals(description, other.description) && Objects.equals(files, other.files)
                && Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "ReceptDTO{" + "id=" + id + ", name=" + name + ", description=" + description + '}';
    }

}
