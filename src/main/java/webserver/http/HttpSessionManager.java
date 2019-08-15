package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionManager {

    public static Map<String, HttpSession> httpSessionMap = new HashMap<>();

    public static HttpSession getSession(String sessionId){
        if(httpSessionMap.containsKey(sessionId)){
            return httpSessionMap.get(sessionId);
        }

        return newSession(sessionId);
    }

    public static HttpSession newSession(){
        HttpSession session = new HttpSession();
        httpSessionMap.put(session.getId(), session);
        return session;
    }

    private static HttpSession newSession(String sessionId){
        HttpSession session = new HttpSession(sessionId);
        httpSessionMap.put(sessionId, session);
        return session;
    }

}
