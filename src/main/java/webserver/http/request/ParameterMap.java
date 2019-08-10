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

    public void putAll(ParameterMap otherParameters) {
        parameters.putAll(otherParameters.getOriginalMap());
    }

    public String get(String key) {
        List<String> parameter = parameters.get(key);
        if (parameter == null) {
            return "";
        }
        return String.join(",", parameters.get(key));
    }

    public Map<String, List<String>> getOriginalMap() {
        return parameters;
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
    }
}
