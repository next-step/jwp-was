package handler;


import http.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class HandlerRegister {

    private Map<HandlerMatcher, Handler> map = new LinkedHashMap<>();

    public void add(HandlerMatcher matcher, Handler handler) {
        this.map.put(matcher, handler);
    }

    public Handler find(final Method method , final String path) {
        Set<HandlerMatcher> handlerMatchers = this.map.keySet();
        return handlerMatchers.stream()
            .filter(matcher -> matcher.isMatch(method, path))
            .map(matcher -> map.get(matcher)).findFirst()
            .orElse(null);
    }

}
