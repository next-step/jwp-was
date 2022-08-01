package webserver.service;

import java.util.Objects;

import org.springframework.util.ObjectUtils;

import db.DataBase;
import model.User;
import webserver.exception.InvalidPasswordException;
import webserver.exception.NotFoundUserException;
import webserver.http.HttpCookie;
import webserver.http.HttpCookies;
import webserver.http.HttpSession;
import webserver.http.HttpSessionStorage;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestBody;

public class AuthService {

	private static final String USERID = "userId";
	private static final String PASSWORD = "password";

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

		HttpCookies cookies = httpRequest.getHeaders().getCookies();
		HttpCookie cookie = cookies.find(HttpSessionStorage.SESSION_LOGIN);
		if (!Boolean.valueOf(cookie.getValue())) {
			return false;
		}

		return true;
	}

	public User doLogin(HttpRequestBody httpRequestBody) {
		String userId = httpRequestBody.getAttribute(USERID);
		String password = httpRequestBody.getAttribute(PASSWORD);

		User user = DataBase.findUserById(userId);
		if (ObjectUtils.isEmpty(user)) {
			throw new NotFoundUserException();
		}

		if (!user.getPassword().equals(password)) {
			throw new InvalidPasswordException();
		}

		return user;
	}
}
