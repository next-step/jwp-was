package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessions {

  private Map<String,HttpSession> httpSessions = new HashMap<>();

  public String createNewSession() {
    String sessionKey = UUID.randomUUID().toString();
    httpSessions.put(sessionKey,new HttpSession(sessionKey));
    return sessionKey;
  }

  public HttpSession get(String sessionKey) {
    return httpSessions.get(sessionKey);
  }

  public boolean containsKey(String sessionKey) {
    return httpSessions.containsKey(sessionKey);
  }
}
