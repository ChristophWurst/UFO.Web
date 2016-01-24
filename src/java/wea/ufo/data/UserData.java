package wea.ufo.data;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import wea.ufo.util.ServiceLocator;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@Named
@SessionScoped
public class UserData implements Serializable {

	private static final Logger LOG = Logger.getLogger(UserData.class.getName());
	private String email;
	private String loginEmail;
	private String loginPassword;
	@Inject
	transient private ServiceLocator serviceLocator;

	/**
	 * Set the value of serviceLocator
	 *
	 * @param serviceLocator new value of serviceLocator
	 */
	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	/**
	 * Get the value of email
	 *
	 * @return the value of email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the value of email
	 *
	 * @param email new value of email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get the value of loginEmail
	 *
	 * @return the value of loginEmail
	 */
	public String getLoginEmail() {
		return loginEmail;
	}

	/**
	 * Set the value of loginEmail
	 *
	 * @param loginEmail new value of loginEmail
	 */
	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	/**
	 * Get the value of loginPassword
	 *
	 * @return the value of loginPassword
	 */
	public String getLoginPassword() {
		return loginPassword;
	}

	/**
	 * Set the value of loginPassword
	 *
	 * @param loginPassword new value of loginPassword
	 */
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public boolean isLoggedIn() {
		return email != null;
	}

	public String login() {
		LOG.log(Level.INFO, "User <{0}> tries to log in", loginEmail);
		boolean result = serviceLocator.getUFOBusinessDelegate().Login(loginEmail, loginPassword);
		if (!result) {
			LOG.log(Level.WARNING, "Invaild credentials for user <{0}>", loginEmail);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("username", new FacesMessage("Invalid email or password"));
			return "login?faces-redirect=true";
		}

		LOG.log(Level.INFO, "User <{0}> logged in successfully", loginEmail);
		email = loginEmail;
		return "home";
	}

	public String logout() {
		if (isLoggedIn()) {
			LOG.log(Level.INFO, "Logging out user <{0}>", loginEmail);
			email = null;
		} else {
			LOG.log(Level.WARNING, "Cannot log out user because he/she is not logged in");
		}
		return "home";
	}

}
