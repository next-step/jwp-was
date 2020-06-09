package http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

    private final String id;

    private final Map<String, Object> map = new HashMap<>();

    public HttpSession(String id) {
        this.id = id;
    }

    /**
     * @return 현재 세션 ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param name  현재 세션에 할당할 객체 이름
     * @param value 현재 세션에 할당할 객체
     */
    public void setAttribute(String name, Object value) {
        map.put(name, value);
    }

    /**
     * @param name 현제 세션에 할당된 객체 이름
     * @return 객체
     */
    public Object getAttribute(String name) {
        return map.get(name);
    }

    /**
     * @param name 지울 객체의 이름
     */
    public void removeAttribute(String name) {
        map.remove(name);
    }

    /**
     * 세션 제거
     */
    public void invalidate() {
        SessionManager.removeSession(id);
    }
}
