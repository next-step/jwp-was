package webserver.servlet;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.controller.Controller;
import webserver.controller.IndexController;
import webserver.controller.StaticController;
import webserver.controller.UserListController;
import webserver.controller.UserLoginController;
import webserver.controller.UserRegisterController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class DispatcherServlet implements Servlet {

	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	private Map<String, Controller> controllerMap = new HashMap<>();

	public DispatcherServlet() {
		controllerMap.put("/", new IndexController());
		controllerMap.put("/favicon.ico", new IndexController());
		controllerMap.put("/index.html", new IndexController());
		controllerMap.put("/user/form.html", new UserRegisterController());
		controllerMap.put("/user/create", new UserRegisterController());
		controllerMap.put("/user/login.html", new UserLoginController());
		controllerMap.put("/user/login_failed.html", new IndexController());
		controllerMap.put("/user/login", new UserLoginController());
		controllerMap.put("/user/list", new UserListController());

	}

	@Override
	public HttpResponse service(HttpRequest httpRequest) {
		logger.debug(httpRequest.getPath());
		Controller controller = controllerMap.getOrDefault(httpRequest.getPath(), new StaticController());
		return controller.process(httpRequest);
	}
}
