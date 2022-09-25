package webserver.servlet;

import java.io.IOException;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import db.DataBase;
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

		TemplateLoader loader = new ClassPathTemplateLoader();
		loader.setPrefix("/templates");
		loader.setSuffix(".html");

		Handlebars handlebars = new Handlebars(loader);

		Template template  = handlebars.registerHelper("inc", (context, options) -> {
			return Integer.parseInt(context.toString()) + 3;
		}).compile("/user/list");


		byte[] content = template.apply(DataBase.findAll()).getBytes();

		response.setHttpVersion(HttpVersion.HTTP_1_1);
		response.setHttpStatus(HttpStatus.OK);
		response.addHeader("Content-Type", "text/html;charset=utf-8");
		response.addHeader("Content-Length", String.valueOf(content.length));

		String message = response.toEncoded();
		response.getWriter().writeBytes(message);
		response.getWriter().write(content, 0, content.length);
		response.getWriter().flush();
	}
}
