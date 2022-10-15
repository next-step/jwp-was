package webserver.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.Cookie;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;
import webserver.http.HttpStatus;

public abstract class AbstractServlet implements Servlet {
	private static final Logger logger = LoggerFactory.getLogger(AbstractServlet.class);
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		HttpSession session = request.getSession();
		if (session.hasChanged() && session.setChanged(false)) {
			Cookie cookie = new Cookie(HttpSession.SESSION_ID, session.getSessionId());
			cookie.setCookieMaxAge(60);
			response.addCookie(cookie);
		}

		response.setHttpVersion(request.getHttpVersion());
		response.setHttpStatus(HttpStatus.OK);

		try {
			doService(request, response);
		} catch (FileNotFoundException exception) {
			response.sendError(HttpStatus.NOT_FOUND);
			logger.warn(exception.getMessage());
		}
	}
	protected abstract void doService(HttpRequest request, HttpResponse response) throws IOException;
}