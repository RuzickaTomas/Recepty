/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.Part;

/**
 *
 */
public class ReceptDTO {

    private Long id;
    private String name;
    private String description;
    private Part files;
    private final List<String> pictures = new ArrayList<>();
    
    public ReceptDTO() {}

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
    
    public List<String> getPictures() {
        return pictures;
    }

    public Part getFiles() {
        return this.files;
    }

    public void setFiles(Part files) {
        this.files = files;
    }
   
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.description);
        hash = 73 * hash + Objects.hashCode(this.pictures);
        return hash;
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
        final ReceptDTO other = (ReceptDTO) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.pictures, other.pictures);
    }

    @Override
    public String toString() {
        return "ReceptDTO{" + "id=" + id + ", name=" + name + ", description=" + description + ", pictures=" + pictures + '}';
    }
   
    
    

    
    
}
