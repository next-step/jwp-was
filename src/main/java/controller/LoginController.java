package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class LoginController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Override
	void doPost(HttpRequest request, HttpResponse response) throws Exception {
		User loginUser = DataBase.findUserById(request.getParameter("userId"));
		logger.debug("로그인 사용자: {}", loginUser);

		boolean logined = false;
		String location = "/user/login_failed.html";

		if (loginUser != null && loginUser.getPassword().equals(request.getParameter("password"))) {
			logger.debug("로그인 성공: {}", loginUser);
			logined = true;
			location = "/index.html";
		}

		response.response302LoginedHeader(logined, location);
	}
}
