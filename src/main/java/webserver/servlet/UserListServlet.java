package webserver.servlet;

import java.io.IOException;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;

public class UserListServlet implements Servlet {
	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		String cookie = request.getHeader("Cookie");
		if (cookie == null || !cookie.contains("logined=true")) {
			response.setHttpVersion(HttpVersion.HTTP_1_1);
			response.sendRedirect("/user/login.html");
			return;
		}

		StringBuilder builder = new StringBuilder();
		builder.append("<table border=\"1\">");
		for (User user : DataBase.findAll()) {
			builder.append("<tr>");
			builder.append("<td>" + user.getUserId() + "</td>");
			builder.append("<td>" + user.getName() + "</td>");
			builder.append("<td>" + user.getEmail() + "</td>");
			builder.append("</tr>");
		}
		builder.append("</table>");
		byte[] body = builder.toString().getBytes();

		response.setHttpVersion(HttpVersion.HTTP_1_1);
		response.setHttpStatus(HttpStatus.OK);
		response.addHeader("Content-Type", "text/html;charset=utf-8");
		response.addHeader("Content-Length", String.valueOf(body.length));

		String message = response.toEncoded();
		response.getWriter().writeBytes(message);
		response.getWriter().write(body, 0, body.length);
		response.getWriter().flush();
	}
}
