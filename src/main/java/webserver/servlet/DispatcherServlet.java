package webserver.servlet;

import java.util.HashMap;
import java.util.Map;

import webserver.controller.BaseController;
import webserver.controller.Controller;
import webserver.controller.UserListController;
import webserver.controller.UserLoginController;
import webserver.controller.UserRegisterController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class DispatcherServlet implements Servlet {

	private final Map<String, Controller> controllerMap = new HashMap<>();

	public DispatcherServlet() {
		controllerMap.put("/user/create", new UserRegisterController());
		controllerMap.put("/user/login", new UserLoginController());
		controllerMap.put("/user/list", new UserListController());
	}

	@Override
	public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
		Controller controller = controllerMap.getOrDefault(httpRequest.getPath(), new BaseController());
		controller.process(httpRequest, httpResponse);
	}
}
