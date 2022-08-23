package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class CreateUserController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

	@Override
	void doPost(HttpRequest request, HttpResponse response) throws Exception {
		User newUser = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
		logger.debug("회원가입 사용자 : {}", newUser);
		DataBase.addUser(newUser);
		response.response302Header();
	}
}
