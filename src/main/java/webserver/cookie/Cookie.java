package webserver.cookie;

import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;


public class Cookie {

    private Map<String, Object> cookiesByName = new HashMap<>();

    public void addCookie(String name, Object value) {
        cookiesByName.put(name, value);
    }

    public Object getCookie(String name) {
        if(!cookiesByName.containsKey(name)) {
            throw new IllegalArgumentException("해당 쿠키가 존재하지않습니다");
        }

        return cookiesByName.get(name);
    }

    public boolean isEmpty() {
        return cookiesByName.isEmpty();
    }

    public String cookiesToString() {
        String result = Strings.EMPTY;
        for (String str : cookiesByName.keySet()) {
            Object o = cookiesByName.get(str);
            result += " " + (str + "=" + o) + ";";
        }

        return result;
    }
}
