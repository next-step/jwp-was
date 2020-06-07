package handler;

import http.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandlerMatcher {

    private final Method method;
    private final Pattern pattern;

    public HandlerMatcher(Method method, Pattern pattern) {
        this.method = method;
        this.pattern = pattern;
    }

    public HandlerMatcher(Method method, String pattern) {
        this(method, Pattern.compile(pattern));
    }

    public boolean isMatch(Method method, String path) {
        if (!this.method.equals(method)) {
            return false;
        }

        Matcher matcher = pattern.matcher(path);

        return matcher.find();
    }
}
