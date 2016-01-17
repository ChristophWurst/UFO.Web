package wea.ufo.filter;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wea.ufo.data.UserData;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@RequestScoped
@WebFilter(urlPatterns = "/faces/*")
public class LoginFilter implements Filter, Serializable {

	private static final Logger LOG = Logger.getLogger(LoginFilter.class.getName());
	private final static String BASE_URL = "/UFO.Web/faces/";
	private final static String[] NEED_AUTH = {
		"performances.xhtml"
	};
	private final static String REDIRECT_URL = "/faces/index.xhtml";

	@Inject
	private UserData userData;

	public void setUserData(UserData ud) {
		userData = ud;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.log(Level.INFO, "loginFilter init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String uri = req.getRequestURI();

		boolean block = false;
		for (String pattern : NEED_AUTH) {
			if (uri.startsWith(BASE_URL + pattern) && !userData.isLoggedIn()) {
				LOG.log(Level.WARNING, "blocking request to <{0}>", uri);
				block = true;
				break;
			}
		}

		if (block) {
			resp.sendRedirect(req.getContextPath() + REDIRECT_URL);
			//ec.redirect("index");
		} else {
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {
		LOG.log(Level.INFO, "loginFilter destroyed");
	}

}
