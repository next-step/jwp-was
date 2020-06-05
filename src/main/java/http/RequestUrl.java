package http;

import java.util.Arrays;

public enum RequestUrl {

    USER_CREATE("/user/create", "createUser"),
    OTHER("", null);

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
                .orElse(OTHER);
    }

    public String getUrl() {
        return url;
    }

    public String getMethodName() {
        return methodName;
    }
}
