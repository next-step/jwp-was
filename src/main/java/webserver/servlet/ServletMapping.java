package webserver.servlet;

import java.util.Map;

public class ServletMapping {
	private static final Map<String, Servlet> servlets = Map.of(
		"/user/create", new UserCreateServlet(),
		"/user/login", new UserLoginServlet(),
		"/user/list", new UserListServlet()
	);

	public static Servlet match(String path){
		return servlets.keySet().stream()
			.filter(path::equals)
			.map(servlets::get)
			.findFirst()
			.orElse(new DefaultServlet());
	}
}
