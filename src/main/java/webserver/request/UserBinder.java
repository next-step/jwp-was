package webserver.request;

import model.User;

public class UserBinder {

    private UserBinder() {
        throw new AssertionError();
    }

    public static User from(final QueryParameters queryParameters) {
        final String userId = queryParameters.get("userId");
        final String password = queryParameters.get("password");
        final String name = queryParameters.get("name");
        final String email = queryParameters.get("email");

        return new User(userId, password, name, email);
    }

}
