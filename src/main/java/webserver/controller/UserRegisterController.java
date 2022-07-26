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
	protected HttpResponse doPost(HttpRequest httpRequest) {
		userService.register(httpRequest.getHttpBody());
		return doRedirect(httpRequest, "/index.html");
	}
}
