package http;

import java.util.HashMap;
import java.util.Map;

public class FormData {
    private Map<String, String> data = new HashMap<>();

    public FormData(String value) {
        for (String  v : value.split("&")) {
            String[] v1 = v.split("=");
            if (v1.length != 2) {
                throw new RuntimeException();
            }
            data.put(v1[0], v1[1]);
        }
    }

    public String getValue(String name) {
        String defaultValue = "";
        return data.getOrDefault(name, defaultValue);
    }

}
