package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.service.UserService;

public class UserRegisterController extends AbstractController {

	private final UserService userService;

	public UserRegisterController() {
		userService = new UserService();
	}

	@Override
	protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		userService.register(httpRequest.getHttpBody());
		httpResponse.sendRedirect("/index.html");
	}
}
