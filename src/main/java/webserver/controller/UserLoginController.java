package webserver.controller;

import model.User;
import webserver.exception.InvalidPasswordException;
import webserver.exception.NotFoundUserException;
import webserver.http.HttpCookies;
import webserver.http.HttpSession;
import webserver.http.HttpSessionStorage;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.service.LoginService;
import webserver.service.UserService;

public class UserLoginController extends AbstractController {

	private final UserService userService;
	private final LoginService loginService;

	public UserLoginController() {
		userService = new UserService();
		loginService = new LoginService();
	}

	@Override
	protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		try {
			User loginUser = userService.login(httpRequest.getHttpBody());

			HttpSession httpSession = httpRequest.getHttpSession();
			httpSession.setAttribute("user", loginUser);
			HttpSessionStorage.getInstance().save(httpSession.getId(), httpSession);

			httpResponse.addHeader("Set-Cookie", HttpCookies.of("SessionId=" + httpSession.getId() + "; Path=/;").getFlatCookies());
			httpResponse.sendRedirect("/index.html");
		} catch (InvalidPasswordException | NotFoundUserException e) {
			httpResponse.addHeader("Set-Cookie", HttpCookies.of("logined=false; Path=/").getFlatCookies());
			httpResponse.sendRedirect("/user/login_failed.html");
		}
	}
}
