package webserver.response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hspark on 2019-08-05.
 */
public class Model {
    private Map<String, String> modelAttribute = new HashMap<>();

    public void addAttribute(String name, String value) {
        modelAttribute.put(name, value);
    }

}
