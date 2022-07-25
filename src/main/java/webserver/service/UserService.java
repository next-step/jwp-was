package webserver.service;

import java.util.Collection;

import org.springframework.util.ObjectUtils;

import db.DataBase;
import model.User;
import webserver.exception.InvalidPasswordException;
import webserver.exception.NotFoundUserException;
import webserver.exception.RegisterDuplicateException;
import webserver.http.request.HttpRequestBody;

public class UserService {
	private static final String USERID = "userId";
	private static final String PASSWORD = "password";
	private static final String NAME = "name";
	private static final String EMAIL = "email";

	public void register(HttpRequestBody parameter) {
		User user = createUser(parameter);
		DataBase.addUser(user);
	}

	private User createUser(HttpRequestBody parameter) {
		String userId = parameter.getAttribute(USERID);
		String password = parameter.getAttribute(PASSWORD);
		String name = parameter.getAttribute(NAME);
		String email = parameter.getAttribute(EMAIL);

		duplicateValidate(userId);

		return new User(userId, password, name, email);
	}

	private void duplicateValidate(String userId) {
		User userById = DataBase.findUserById(userId);
		if (!ObjectUtils.isEmpty(userById)) {
			throw new RegisterDuplicateException();
		}
	}

	public void login(HttpRequestBody httpRequestBody) {
		String userId = httpRequestBody.getAttribute(USERID);
		String password = httpRequestBody.getAttribute(PASSWORD);

		User user = DataBase.findUserById(userId);
		if (ObjectUtils.isEmpty(user)) {
			throw new NotFoundUserException();
		}

		if (!user.getPassword().equals(password)) {
			throw new InvalidPasswordException();
		}
	}

	public Collection<User> findAll() {
		return DataBase.findAll();
	}
}
