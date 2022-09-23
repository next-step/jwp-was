package webserver.servlet;

import java.io.IOException;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpVersion;

public class UserCreateServlet implements Servlet {
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		User user = new User(userId, password, name, email);
		DataBase.addUser(user);

		response.setHttpVersion(HttpVersion.HTTP_1_1);
		response.sendRedirect("/");
	}
}
