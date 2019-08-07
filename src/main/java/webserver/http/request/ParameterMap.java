package webserver.http.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : yusik
 * @date : 2019-08-04
 */
public class ParameterMap {

    private Map<String, Object> parameters;

    public ParameterMap() {
        this.parameters = new HashMap<>();
    }

    public Object put(String key, String newValue) {
        if (parameters.containsKey(key)) {
            Object oldValue = parameters.get(key);
            if (oldValue instanceof List) {
                ((List) oldValue).add(key);
            } else {
                List<String> values = new ArrayList<>();
                values.add((String) oldValue);
                values.add(newValue);
                parameters.put(key, values);
            }
        } else {
            parameters.put(key, newValue);
        }
        return parameters.put("", "");
    }

    public Object get(String key) {
        return parameters.get(key);
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
    }
}
