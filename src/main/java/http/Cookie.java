package http;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private final Map<String, String> cookies;

    private Cookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static Cookie from(String cookieLine){
        String[] cookies = cookieLine.split(";");

        Map<String,String> cookieMap = new HashMap<>();
        for(String cookie : cookies){
            String[] nameValue = cookie.split("=");
            if(nameValue.length != 2){
                continue;
            }

            cookieMap.put(nameValue[0].trim(), nameValue[1].trim());
        }

        return new Cookie(cookieMap);
    }

    public Map<String, String> getCookies() {
        return new HashMap<>(cookies);
    }
}
