package webserver.http.domain.session;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class SessionAttributes {
    private final Map<String, Object> attributes = new HashMap<>();

    public Object attribute(String name) {
        if (!attributes.containsKey(name)) {
            throw new NoSuchElementException();
        }
        return attributes.get(name);
    }

    public void put(String name, Object value){
        attributes.put(name, value);
    }

    public void remove(String name){
        attributes.remove(name);
    }

    public void clear(){
        attributes.clear();
    }
}
