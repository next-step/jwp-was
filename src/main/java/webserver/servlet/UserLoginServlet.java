package webserver.servlet;

import java.io.IOException;
import java.util.Objects;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;
import webserver.http.HttpVersion;
public class UserLoginServlet implements Servlet {
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String location = "/index.html";

		User user = DataBase.findUserById(userId);

		if (Objects.nonNull(user) && user.matchPassword(password)) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
		} else {
			location = "/user/login_failed.html";
		}

		response.setHttpVersion(HttpVersion.HTTP_1_1);
		response.sendRedirect(location);
	}
}
