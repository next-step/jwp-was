package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

  private String id;
  private Map<String, Object> attributes = new HashMap<>();

  public HttpSession(UUID uuid) {
    this.id = uuid.toString();
  }

  public String getId() {
    return this.id;
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
}
