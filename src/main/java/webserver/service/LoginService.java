package webserver.service;

import java.util.Objects;

import db.DataBase;
import model.User;
import webserver.http.HttpSession;
import webserver.http.HttpSessionStorage;
import webserver.http.request.HttpRequest;

public class LoginService {

	public Boolean isLogin(HttpRequest httpRequest) {
		HttpSession httpSession = httpRequest.getHttpSession();
		HttpSession session = HttpSessionStorage.getInstance().getSession(httpSession.getId());
		User user = (User) session.getAttribute("user");

		if (Objects.isNull(user)) {
			return false;
		}

		User findUser = DataBase.findUserById(user.getUserId());
		if (!user.equals(findUser)) {
			return false;
		}

		return true;
	}
}
