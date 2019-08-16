package http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

  private Map<String, Object> attributes = new HashMap<>();
  private String id;

  public HttpSession(String id) {
    this.id = id;
  }

  public void setAttribute(String key, Object value) {
    attributes.put(key, value);
  }

  public Object getAttribute(String key) {
    return attributes.get(key);
  }

  public void removeAttribute(String key) {
    attributes.remove(key);
  }

  public void invalidate() {
    attributes.clear();
  }

  public String getId() {
    return id;
  }
}
