package webserver;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Parameters {

    private static final String AMPERSAND = "&";
    private static final String EQUAL_SIGN = "=";
    private static final int INDEX_ZERO = 0;
    private static final int INDEX_ONE = 1;

    private final String queryString;
    private final MultiValueMap<String, String> parameters;

    public Parameters(String queryString) {
        this.queryString = queryString;
        this.parameters = parseQueryString(queryString);
    }

    private MultiValueMap<String, String> parseQueryString(String queryString) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Arrays.stream(queryString.split(AMPERSAND)).forEach(parameter -> {
            String[] nameValuePair = parameter.split(EQUAL_SIGN);
            params.put(nameValuePair[INDEX_ZERO], Collections.singletonList(nameValuePair[INDEX_ONE]));
        });
        return params;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getParameter(String name) {
        List<String> values = this.parameters.get(name);
        if (values.isEmpty()) {
            return null;
        }
        return values.get(INDEX_ZERO);
    }
}
