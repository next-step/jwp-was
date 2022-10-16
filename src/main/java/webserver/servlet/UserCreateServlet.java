package webserver.servlet;

import java.io.IOException;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserCreateServlet extends AbstractServlet {
	public void doService(HttpRequest request, HttpResponse response) throws IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		User user = new User(userId, password, name, email);
		DataBase.addUser(user);

		response.sendRedirect("/");
	}
}
