package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class FrontController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(FrontController.class);

	private static final Map<String, Controller> controllers;
	static {
		controllers = new HashMap<>();
		controllers.put("/user/create", new CreateUserController());
		controllers.put("/user/login", new LoginController());
		controllers.put("/user/list", new ListUserController());
	}

	@Override
	public void service(HttpRequest request, HttpResponse response) throws Exception {
		mapped(request.getPath()).service(request, response);
	}

	private Controller mapped(String path) {
		Controller controller = controllers.get(path);
		logger.debug("path : {}", controller);
		logger.debug("mapped : {}", controller);

		if (controller == null) {
			return new StaticController();
		}
		return controller;
	}
}
