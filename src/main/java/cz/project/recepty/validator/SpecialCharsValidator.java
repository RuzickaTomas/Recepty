package cz.project.recepty.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "specialCharsValidator")
public class SpecialCharsValidator implements Validator {

	private static final String SPECIAL_CHARS_REGEX = "^[^'&lt;&gt;]+$";
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		 String text = (String) value;
		 if (!text.matches(SPECIAL_CHARS_REGEX) || text.contains("\\")) {
			 throw new ValidatorException(new FacesMessage("Text obsahuje nepovolené znaky!"));
		 }		 
	}

}
