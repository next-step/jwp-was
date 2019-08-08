package webserver.http.request;

import java.util.*;

/**
 * @author : yusik
 * @date : 2019-08-04
 */
public class ParameterMap {

    private Map<String, List<String>> parameters;

    public ParameterMap() {
        this.parameters = new HashMap<>();
    }

    public void put(String field, String value) {
        List<String> valueList = Optional.ofNullable(parameters.get(field)).orElseGet(ArrayList::new);
        valueList.add(value);
        parameters.put(field, valueList);
    }

    public String get(String key) {
        return String.join(",", parameters.get(key));
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
    }
}
