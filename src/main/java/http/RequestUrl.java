package http;

import java.util.Arrays;

public enum RequestUrl {

    USER_CREATE("/user/create", "createUser"),
    USER_LOGIN("/user/login", "login"),
    USER_LIST("/user/list", "userList"),
    NONE("", null);

    private final String url;
    private final String methodName;

    RequestUrl(String url, String methodName) {
        this.url = url;
        this.methodName = methodName;
    }

    public static RequestUrl findByPath(String path) {
        return Arrays.stream(RequestUrl.values())
                .filter(u -> path.equals(u.url))
                .findAny()
                .orElse(NONE);
    }

    public String getUrl() {
        return url;
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean isUserLogin() {
        return this == USER_LOGIN;
    }

    public boolean isUserList() {
        return this == USER_LIST;
    }

    public boolean isNone() {
        return this == NONE;
    }
}
