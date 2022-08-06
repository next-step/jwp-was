package model.http;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestBody {
    private Map<String, String> requestBodyMap;

    public RequestBody(String requestBodyText) {
        initial(requestBodyText);
    }

    private void initial(String requestBodyText) {
        String[] requestBodyTexts = requestBodyText.split("&");
        requestBodyMap = Arrays.stream(requestBodyTexts)
                .collect(Collectors.toMap(text -> text.split("=")[0].trim(), text -> text.split("=")[1].trim()));
    }

    public Map<String, String> getRequestBodyMap() {
        return requestBodyMap;
    }
}
