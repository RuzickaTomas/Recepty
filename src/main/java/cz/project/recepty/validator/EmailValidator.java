package cz.project.recepty.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "emailValidator")
public class EmailValidator implements Validator {

	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		 String email = (String) value;
		 if (!email.matches(EMAIL_REGEX)) {
			 throw new ValidatorException(new FacesMessage("Email není validní!"));
		 }		 
	}

}
