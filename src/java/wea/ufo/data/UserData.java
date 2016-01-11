package wea.ufo.data;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import wea.ufo.util.ServiceLocator;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@ManagedBean(name = "userData")
@SessionScoped
public class UserData implements Serializable {

    private static final Logger logger = Logger.getAnonymousLogger();
    private String email;
    private String loginEmail;
    private String loginPassword;

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
        return loginEmail != null;
    }

    public String login() {
        logger.log(Level.INFO, "User <{0}> tries to log in", loginEmail);
        boolean result = ServiceLocator.getInstance().getUFOBusinessDelegate().Login(loginEmail, loginPassword);
        if (!result) {
            logger.log(Level.WARNING, "Invaild credentials for user <{0}>", loginEmail);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("username", new FacesMessage("Invalid UserName and Password"));
            return "login";
        }

        logger.log(Level.INFO, "User <{0}> logged in successfully", loginEmail);
        email = loginEmail;
        return "home";
    }

    public void logout() {
        loginEmail = null;
    }

}
