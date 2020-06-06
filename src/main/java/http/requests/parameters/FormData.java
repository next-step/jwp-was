package http.requests.parameters;

import java.util.Collections;
import java.util.Map;

public class FormData {

    private final Map<String, String> formData;

    public FormData(Map<String, String> parsedFormDataMap) {
        formData = parsedFormDataMap;
    }

    public FormData() {
        formData = Collections.emptyMap();
    }

    public String get(String key) {
        return formData.get(key);
    }
}
