package webserver.servlet;

import java.util.HashMap;
import java.util.Map;

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
	}

	@Override
	public HttpResponse service(HttpRequest httpRequest) {
		// Path 정보 추출
		String uri = httpRequest.getPath();
		String path = uri.substring(uri.lastIndexOf("/"));

		Controller controller = controllerMap.getOrDefault(path, new StaticController());

		return controller.process(httpRequest);
	}
}
