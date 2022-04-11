/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 * Validujeme soubor, který chceme nahrát
 */
@FacesValidator(value = "fileValidator")
public class FileValidator implements Validator {

    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        Part file = (Part) arg2; 
       
        int maxSize = 2000000; //max velikost souboru v našem případě je 2MB
        
        FacesMessage message = null;
        // vytvoříme výchozí zprávu pro případ že soubor není obrázek
        FacesMessage defaultMsg = new FacesMessage("Vyber obrázkový soubor!");

        try {
            if (file == null || file.getSize() <= 0 || file.getContentType().isEmpty()) {
                //v případě že nebyl nahrán žádný soubor vypíšme tuto zprávu
                message = new FacesMessage("Vyber validní soubor!");
            } else if (!file.getContentType().endsWith("jpg")  
            		&& !file.getContentType().endsWith("jpeg")
            		&& !file.getContentType().endsWith("png")
            		&& !file.getContentType().endsWith("gif")) {
                //pokud se nejedná o soubor typu jpg, jpeg, png nebo gif, vypíšeme výchozí zprávu
            	message = defaultMsg;
            } else if (file.getSize() > maxSize) {
                //pokud se pokusíme nahrát příliš velký soubor, budeme varováni touto zprávou
                message = new FacesMessage("Soubor je příliš velký! Soubor musím mít velikost menší nebo rovno 2 MB.");
            }
            if (message != null && !message.getDetail().isEmpty()) {
                //nastavíme úroveň vážnosti zprávy
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //v případě že nastala některá předchozích událostí, vyhodíme vyjímku
                throw new ValidatorException(message);
            }

        } catch (Exception ex) {
            //pokud nastane neočekávaný stav při validaci zachytíme i tuto vyjímku
            throw new ValidatorException(new FacesMessage(ex.getMessage()));
        }

    }

}
