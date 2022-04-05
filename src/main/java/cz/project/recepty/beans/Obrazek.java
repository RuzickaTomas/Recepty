/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.beans;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author tomas
 */
public class Obrazek implements Serializable {
    
    private Long id;
    private String path;
    private long recept_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getRecept_id() {
        return recept_id;
    }

    public void setRecept_id(long recept_id) {
        this.recept_id = recept_id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 67 * hash + Objects.hashCode(this.path);
        hash = 67 * hash + (int) (this.recept_id ^ (this.recept_id >>> 32));
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
        final Obrazek other = (Obrazek) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.recept_id != other.recept_id) {
            return false;
        }
        return Objects.equals(this.path, other.path);
    }

    @Override
    public String toString() {
        return "Obrazek{" + "id=" + id + ", path=" + path + ", recept_id=" + recept_id + '}';
    }
    
    
    
    

}
