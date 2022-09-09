package webserver.http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SessionAttributes {

    private final Map<String, Object> attributes = new HashMap<>();

    public Object getAttribute(String name) {
        if (!attributes.containsKey(name)) {
            throw new NoSuchElementException();
        }
        return attributes.get(name);
    }

    public void setAttribute(String name, Object value){
        attributes.put(name, value);
    }

    public void removeAttribute(String name){
        attributes.remove(name);
    }

    public void invalidate(){
        attributes.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionAttributes that = (SessionAttributes) o;
        return Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes);
    }
}
