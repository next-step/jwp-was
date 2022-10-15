package webserver.servlet;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.http.Cookie;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;
import webserver.http.HttpStatus;

public abstract class AbstractServlet implements Servlet {
	public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {

		HttpSession session = request.getSession();
		if (session.hasChanged() && session.setChanged(false)) {
			Cookie cookie = new Cookie(HttpSession.SESSION_ID, session.getSessionId());
			cookie.setCookieMaxAge(60);
			response.addCookie(cookie);
		}

		response.setHttpVersion(request.getHttpVersion());
		response.setHttpStatus(HttpStatus.OK);

		doService(request, response);
	}

	protected abstract void doService(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}