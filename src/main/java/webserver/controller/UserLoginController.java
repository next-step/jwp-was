package webserver.controller;

import webserver.exception.InvalidPasswordException;
import webserver.exception.NotFoundUserException;
import webserver.http.HttpCookies;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.service.UserService;

public class UserLoginController extends AbstractController {

	private final UserService userService;

	public UserLoginController() {
		userService = new UserService();
	}

	@Override
	protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
		if (httpRequest.isLogin()) {
			httpResponse.sendRedirect("/index.html");
			return;
		}
		doGet(httpRequest, httpResponse);
	}

	@Override
	protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		try {
			userService.login(httpRequest.getHttpBody());

			httpResponse.addHeader("SET-COOKIE", HttpCookies.of("logined=true; Path=/").getFlatCookies());
			httpResponse.sendRedirect("/index.html");
		} catch (InvalidPasswordException | NotFoundUserException e) {
			httpResponse.addHeader("SET-COOKIE", HttpCookies.of("logined=false; Path=/").getFlatCookies());
			httpResponse.sendRedirect("/user/login_failed.html");
		}
	}
}
