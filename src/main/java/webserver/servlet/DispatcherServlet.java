package webserver.servlet;

import java.util.HashMap;
import java.util.Map;

import webserver.controller.UserRegisterController;
import webserver.controller.StaticController;
import webserver.http.request.HttpRequest;
import webserver.controller.Controller;
import webserver.controller.IndexController;
import webserver.http.response.HttpResponse;

public class DispatcherServlet implements Servlet {

	private Map<String, Controller> controllerMap = new HashMap<>();

	public DispatcherServlet() {
		controllerMap.put("/", new IndexController());
		controllerMap.put("/favicon.ico", new IndexController());
		controllerMap.put("/index.html", new IndexController());
		controllerMap.put("/user/form.html", new UserRegisterController());
		controllerMap.put("/user/create", new UserRegisterController());
	}

	@Override
	public HttpResponse service(HttpRequest httpRequest) {
		// Path 정보 추출
		Controller controller = controllerMap.getOrDefault(httpRequest.getPath(), new StaticController());

		return controller.process(httpRequest);
	}
}
