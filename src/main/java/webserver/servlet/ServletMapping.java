package webserver.servlet;

import java.util.Map;

public class ServletMapping {
	private ServletMapping() {
		throw new IllegalStateException("This is utility class for servlet mapping");
	}

	private static final Map<String, Servlet> servlets = Map.of(
		"/user/create", new UserCreateServlet(),
		"/user/login", new UserLoginServlet(),
		"/user/list", new UserListServlet()
	);

	public static Servlet match(String servletPath) {
		return servlets.getOrDefault(servletPath, new DefaultServlet());
	}
}
