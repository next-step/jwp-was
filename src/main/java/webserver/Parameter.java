package webserver;

import utils.MapUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;
import static utils.StringUtils.endSplit;
import static utils.StringUtils.frontSplitWithOrigin;

public class Parameter {

    private static final String QUERY_FIELD_DELIMITER = "&";
    private static final char KEY_VALUE_DELIMITER = '=';

    private Map<String, String> parameterMap;

    public Parameter(Map<String, String> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public static Parameter parseParameter(String query, String queryDelimiter) {
        String[] parameters = query.split(queryDelimiter);

        if (parameters.length == 0) {
            return new Parameter(emptyMap());
        }

        Map<String, String> queryMap = new HashMap<>(parameters.length);
        for (String parameter : parameters) {
            String key = frontSplitWithOrigin(parameter, KEY_VALUE_DELIMITER);
            MapUtils.putIfKeyNotBlank(queryMap, key, endSplit(parameter, KEY_VALUE_DELIMITER));
        }
        return new Parameter(queryMap);
    }

    /**
     * @param query is key=value string split '&'
     * @return key/value query map
     */
    public static Parameter parseParameter(String query) {
        return parseParameter(query, QUERY_FIELD_DELIMITER);
    }

    /**
     * parameter merge policy is sequential
     * duplicate key is overwritten
     */
    public static Parameter of(List<Parameter> parameters) {
        return new Parameter(parameters.stream()
                .filter(Objects::nonNull)
                .map(Parameter::getParameterMap)
                .flatMap(map -> map.entrySet().stream())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (p1, p2) -> p2)));
    }

    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    /**
     * @return if parameter is empty or decoding fail, return null
     */
    public String getParameter(String key) {
        if (! parameterMap.containsKey(key)) {
            return null;
        }

        try {
            return URLDecoder.decode(parameterMap.get(key), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
