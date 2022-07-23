package webserver.service;

import org.springframework.util.ObjectUtils;

import db.DataBase;
import model.User;
import webserver.exception.RegisterDuplicateException;
import webserver.http.request.QueryParameter;

public class UserService {
	private static final String USERID = "userId";
	private static final String PASSWORD = "password";
	private static final String NAME = "name";
	private static final String EMAIL = "email";

	public void register(QueryParameter parameter) {
		User user = createUser(parameter);
		DataBase.addUser(user);
	}

	private User createUser(QueryParameter parameter) {
		String userId = parameter.getValue(USERID);
		String password = parameter.getValue(PASSWORD);
		String name = parameter.getValue(NAME);
		String email = parameter.getValue(EMAIL);

		validate(userId);

		return new User(userId, password, name, email);
	}

	private void validate(String userId) {
		User userById = DataBase.findUserById(userId);
		if (!ObjectUtils.isEmpty(userById)) {
			throw new RegisterDuplicateException();
		}
	}

}
