package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;

import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Pattern;

public class QueryParams {

    private static final Logger logger = LoggerFactory.getLogger(QueryParams.class);

    private static final int QUERY_KEY_VALUE_SPLIT_LIMIT = 2;

    private static final String QUERY_KEY_VALUE_SPLIT_SIGN = "=";
    
    private static final String PATH_QUERY_STRING_IGNORE_REGEX = "^.*\\?";

    private static final String QUERY_STRING_SPLIT_SIGN = "&";

    private static final String EMPTY_STRING = "";

    private static final Pattern QUERY_PARAM_PATTERN = Pattern.compile("^[^=\\s]+(=(\\S+)?)?");

    private final Map<String, List<String>> parameterValuesByName;

    private QueryParams(String[] queryParamKeyVaues){
        this.parameterValuesByName = new HashMap<>();

        for(String queryKeyValue : queryParamKeyVaues) {
            setQueryParam(this.parameterValuesByName, queryKeyValue);
        }
    }

    public static QueryParams parseByPath(String path) {

        String[] queryParamKeyVaues = splitPathToQueryParamKeyValues(path);

        return new QueryParams(queryParamKeyVaues);
    };

    private static String[] splitPathToQueryParamKeyValues(String path) {
        return path.replaceAll(PATH_QUERY_STRING_IGNORE_REGEX, EMPTY_STRING)
                .split(QUERY_STRING_SPLIT_SIGN);
    }

    private void setQueryParam(Map<String, List<String>> parameterMap, String queryKeyValue) {

        if(!isValidQueryParamPattern(queryKeyValue)) {
            throw new IllegalArgumentException("queryString 형식이아닙니다.");
        }

        String[] keyAndValue = splitQueryKeyValue(queryKeyValue);

        if(keyAndValue.length != QUERY_KEY_VALUE_SPLIT_LIMIT) {
            return;
        }

        String key = keyAndValue[0];
        String value = urlDecodeValue(keyAndValue[1]);
        this.parameterValuesByName.computeIfAbsent(key, (k) -> new ArrayList<>()).add(value);
    }

    private String urlDecodeValue(String value){
        try {
            return URLDecoder.decode(value, System.getProperty("file.encoding"));
        } catch (Exception e) {
            logger.error("{}" , e);
        }

        return value;
    }

    private boolean isValidQueryParamPattern(String queryKeyValue) {

        if(StringUtils.isEmpty(queryKeyValue)) {
            return false;
        }

        if(!QUERY_PARAM_PATTERN.matcher(queryKeyValue).find()) {
            return false;
        }
        return true;
    }

    private String[] splitQueryKeyValue(String queryKeyValue) {
        return queryKeyValue.split(QUERY_KEY_VALUE_SPLIT_SIGN, QUERY_KEY_VALUE_SPLIT_LIMIT);
    }

    public Map<String, List<String>> getParameters(){
        return Collections.unmodifiableMap(this.parameterValuesByName);
    }


    public String getParameter(String name) {
        return Optional.ofNullable(getParameterValues(name))
                .filter(values -> values.length > 0)
                .map(values -> values[0])
                .orElse(null);
    }

    public String[] getParameterValues(String name) {
        return Optional.ofNullable(this.parameterValuesByName.get(name))
                .map(values -> values.toArray(new String[values.size()]))
                .orElse(null);
    }

}
