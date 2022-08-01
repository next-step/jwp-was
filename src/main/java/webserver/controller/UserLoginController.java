package webserver.controller;

import model.User;
import webserver.exception.InvalidPasswordException;
import webserver.exception.NotFoundUserException;
import webserver.http.HttpCookie;
import webserver.http.HttpSession;
import webserver.http.HttpSessionStorage;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.service.AuthService;

public class UserLoginController extends AbstractController {

	private final AuthService authService;

	public UserLoginController() {
		authService = new AuthService();
	}

	@Override
	protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		try {
			User loginUser = authService.doLogin(httpRequest.getHttpBody());

			HttpSession httpSession = httpRequest.getHttpSession();
			httpSession.setAttribute("user", loginUser);
			HttpSessionStorage.getInstance().save(httpSession.getId(), httpSession);

			httpResponse.addCookie(new HttpCookie(HttpSessionStorage.SESSION_ID, httpSession.getId()));
			httpResponse.addCookie(new HttpCookie(HttpSessionStorage.SESSION_LOGIN, "true"));

			httpResponse.sendRedirect("/index.html");
		} catch (InvalidPasswordException | NotFoundUserException e) {
			httpResponse.addCookie(new HttpCookie(HttpSessionStorage.SESSION_LOGIN, "false"));
			httpResponse.sendRedirect("/user/login_failed.html");
		}
	}
}
