package webserver.servlet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;
import webserver.http.HttpStatus;
import webserver.http.SessionManager;

public abstract class AbstractServlet implements Servlet {
	private static final String SESSION_ID_PREFIX = "JSESSIONID=";
	public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
		SessionManager sessionManager = SessionManager.getInstance();
		UUID sessionId = request.getCookieSessionId();

		if(!sessionManager.isSessionIdValid(sessionId)) {
			sessionManager.createSession(sessionId);
			response.addHeader("Set-Cookie", SESSION_ID_PREFIX + sessionId + "; Path=/; Max-Age=60");
		}

		HttpSession session = sessionManager.findSession(sessionId);
		request.setSession(session);

		response.setHttpVersion(request.getHttpVersion());
		response.setHttpStatus(HttpStatus.OK);

		doService(request, response);
	}

	protected abstract void doService(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}