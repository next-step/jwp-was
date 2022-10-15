package webserver.servlet;

import java.io.IOException;
import java.util.Objects;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import db.DataBase;
import webserver.http.HttpMessage;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

public class UserListServlet extends AbstractServlet {
	@Override
	public void doService(HttpRequest request, HttpResponse response) throws IOException {
		if (!hasAuthorization(request)) {
			response.sendRedirect("/user/login.html");
			return;
		}

		TemplateLoader loader = new ClassPathTemplateLoader();
		loader.setPrefix("/templates");
		loader.setSuffix(".html");

		Handlebars handlebars = new Handlebars(loader);

		Template template = handlebars.registerHelper("inc",
				(context, options) -> Integer.parseInt(context.toString()) + 3)
			.compile("/user/list");

		byte[] content = template.apply(DataBase.findAll()).getBytes();
		int length = content.length;

		response.addHeader(HttpMessage.CONTENT_TYPE, "text/html;charset=utf-8");
		response.addHeader(HttpMessage.CONTENT_LENGTH, String.valueOf(length));
		response.getWriter().writeBytes(response.toEncoded());
		response.getWriter().write(content, 0, length);
		response.getWriter().flush();
		response.getWriter().close();
	}

	private boolean hasAuthorization(HttpRequest request) {
		HttpSession session = request.getSession();
		return Objects.nonNull(session.getAttribute("user"));
	}
}
