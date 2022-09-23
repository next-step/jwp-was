package webserver.servlet;

import java.io.IOException;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpVersion;
public class UserLoginServlet implements Servlet {
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String location = "/index.html";
		String hasAuthenticated = "true";

		User user = DataBase.findUserById(userId);
		if (user == null || !user.getPassword().equals(password)) {
			location = "/user/login_failed.html";
			hasAuthenticated = "false";
		}

		response.setHttpVersion(HttpVersion.HTTP_1_1);
		response.addHeader("Set-Cookie", "logined=" + hasAuthenticated);
		response.sendRedirect(location);
	}
}
