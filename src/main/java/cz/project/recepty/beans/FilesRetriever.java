/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.beans;

import jakarta.faces.context.FacesContext;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * 
 */
public class FilesRetriever {

    public static Collection<Part> getAllParts(Part part) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
    }

}
