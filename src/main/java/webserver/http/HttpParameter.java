package webserver.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toMap;

public class HttpParameter {

    private Map<String, String> parameters;

    public HttpParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    /**
     * parameter merge policy is sequential
     * duplicate key is overwritten
     */
    public static HttpParameter of(List<HttpParameter> httpParameters) {
        return new HttpParameter(httpParameters.stream()
                .filter(Objects::nonNull)
                .map(HttpParameter::getParameters)
                .flatMap(map -> map.entrySet().stream())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (p1, p2) -> p2)));
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * @return if parameter is empty or decoding fail, return null
     */
    public String getParameter(String key) {
        if (! parameters.containsKey(key)) {
            return null;
        }

        try {
            return URLDecoder.decode(parameters.get(key), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
