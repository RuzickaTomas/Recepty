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
 * Validujeme soubor, kterĂ˝ chceme nahrĂˇt
 */
@FacesValidator(value = "fileValidator")
public class FileValidator implements Validator {

    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        Part file = (Part) arg2; 
       
        int maxSize = 2000000; //max velikost souboru v naĹˇem pĹ™Ă­padÄ› je 2MB
        
        FacesMessage message = null;
        // vytvoĹ™Ă­me vĂ˝chozĂ­ zprĂˇvu pro pĹ™Ă­pad Ĺľe soubor nenĂ­ obrĂˇzek
        FacesMessage defaultMsg = new FacesMessage("Vyber obrázkový soubor!");

        try {
            if (file == null || file.getSize() <= 0 || file.getContentType().isEmpty()) {
                //v pĹ™Ă­padÄ› Ĺľe nebyl nahrĂˇn ĹľĂˇdnĂ˝ soubor vypĂ­Ĺˇme tuto zprĂˇvu
                message = new FacesMessage("Vyber validní soubor!");
            } else if (!file.getContentType().endsWith("jpg")  
            		&& !file.getContentType().endsWith("jpeg")
            		&& !file.getContentType().endsWith("png")
            		&& !file.getContentType().endsWith("gif")) {
                //pokud se nejednĂˇ o soubor typu jpg, jpeg, png nebo gif, vypĂ­Ĺˇeme vĂ˝chozĂ­ zprĂˇvu
            	message = defaultMsg;
            } else if (file.getSize() > maxSize) {
                //pokud se pokusĂ­me nahrĂˇt pĹ™Ă­liĹˇ velkĂ˝ soubor, budeme varovĂˇni touto zprĂˇvou
                message = new FacesMessage("Soubor je příliš velký! Soubor musí mít velikost menší nebo rovno 2 MB.");
            }
            if (message != null && !message.getDetail().isEmpty()) {
                //nastavĂ­me ĂşroveĹ� vĂˇĹľnosti zprĂˇvy
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //v pĹ™Ă­padÄ› Ĺľe nastala nÄ›kterĂˇ pĹ™edchozĂ­ch udĂˇlostĂ­, vyhodĂ­me vyjĂ­mku
                throw new ValidatorException(message);
            }

        } catch (Exception ex) {
            //pokud nastane neoÄŤekĂˇvanĂ˝ stav pĹ™i validaci zachytĂ­me i tuto vyjĂ­mku
            throw new ValidatorException(new FacesMessage(ex.getMessage()));
        }

    }

}
