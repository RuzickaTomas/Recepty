/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.transform;

import cz.project.recepty.beans.Recept;
import cz.project.recepty.dto.ReceptDTO;

/**
 *
 */
public class TransformRecept {
    
    
        public static Recept transform(ReceptDTO from) {
        Recept to = new Recept(from.getId(), from.getName(), from.getDescription());
        return to;
    }
        
        public static ReceptDTO transform(Recept from) {
          ReceptDTO to =  new ReceptDTO();
          to.setId(from.getId());
          to.setName(from.getName());
          to.setDescription(from.getDescription());
          return to;
        }
    
                
}
