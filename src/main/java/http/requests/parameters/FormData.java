package http.requests.parameters;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class FormData {

    private final Map<String, String> formData;

    public FormData(Map<String, String> parsedFormDataMap) {
        formData = Optional.ofNullable(parsedFormDataMap).orElse(Collections.emptyMap());
    }

    public String get(String key) {
        return formData.get(key);
    }
}
