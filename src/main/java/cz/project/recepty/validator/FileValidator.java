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
 *
 *
 */
@FacesValidator(value = "fileValidator")
public class FileValidator implements Validator {

    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        Part file = (Part) arg2;

        int maxSize = 2000000;
        FacesMessage message = null;
        FacesMessage defaultMsg = new FacesMessage("Select image file");

        try {
            if (file == null || file.getSize() <= 0 || file.getContentType().isEmpty()) {
                message = new FacesMessage("Select a valid file");
            } else if (!file.getContentType().endsWith("jpg")) {
                message = defaultMsg;
            } else if (!file.getContentType().endsWith("jpeg")) {
                message = defaultMsg;
            } else if (!file.getContentType().endsWith("png")) {
                message = defaultMsg;
            } else if (!file.getContentType().endsWith("gif")) {
                message = defaultMsg;
            } else if (file.getSize() > maxSize) {
                message = new FacesMessage("File size too big. File size allowed  is less than or equal to 2 MB.");
            }
            if (message != null && !message.getDetail().isEmpty()) {
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }

        } catch (Exception ex) {
            throw new ValidatorException(new FacesMessage(ex.getMessage()));
        }

    }

}
