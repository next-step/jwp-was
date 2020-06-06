package http;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.util.List;

public class QueryString {

    private static final String AMPERSAND_DELIMITER = "&";
    private static final String EQUALS_SIGN = "=";

    private MultiValueMap<String, String> parameters;

    private QueryString(MultiValueMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public static QueryString from(String fullQueryString) {
        String[] pairs = fullQueryString.split(AMPERSAND_DELIMITER);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        for (String pair:pairs) {
            int indexOfEqualsSign = pair.indexOf(EQUALS_SIGN);

            String key = pair.substring(0, indexOfEqualsSign);
            String value = pair.substring(indexOfEqualsSign+1);

            parameters.add(key, value);
        }
        return new QueryString(parameters);
    }

    public List<String> getParameters(String key) {
        return parameters.get(key);
    }
}
