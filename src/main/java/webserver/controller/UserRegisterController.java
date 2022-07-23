package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.service.UserService;

public class UserRegisterController extends AbstractController {

	private UserService userService;

	public UserRegisterController() {
		userService = new UserService();
	}

	@Override
	HttpResponse doPost(HttpRequest httpRequest) {
		userService.register(httpRequest.getParameter());
		return doRedirect(httpRequest, "/index.html");
	}
}
