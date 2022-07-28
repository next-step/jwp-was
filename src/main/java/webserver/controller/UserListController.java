package webserver.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import model.User;
import webserver.http.ContentType;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.service.UserService;

public class UserListController extends AbstractController {

	private final UserService userService;
	private final Handlebars handlebars;

	public UserListController() {
		userService = new UserService();
		TemplateLoader loader = new ClassPathTemplateLoader();
		loader.setPrefix("/templates");
		loader.setSuffix(".html");
		handlebars = new Handlebars(loader);
	}

	@Override
	protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
		if (httpRequest.isLogin()) {
			Collection<User> allUsers = userService.findAll();
			String page = createPage(allUsers);
			httpResponse.forwardBody(ContentType.HTML, page);
		}
		httpResponse.sendRedirect("/user/login.html");
	}

	private String createPage(Collection<User> users) {
		try {
			Template template = handlebars.compile("/user/list");
			return template.apply(Collections.singletonMap("users", users));
		} catch (IOException e) {
			throw new IllegalArgumentException("페이지 생성에 실패하였습니다.");
		}
	}
}
