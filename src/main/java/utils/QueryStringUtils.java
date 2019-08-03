package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class QueryStringUtils {


    private static final Logger logger = LoggerFactory.getLogger(QueryStringUtils.class);

    private static final int QUERY_KEY_VALUE_SPLIT_LIMIT = 2;

    private static final String QUERY_KEY_VALUE_SPLIT_SIGN = "=";

    private static final String PATH_QUERY_STRING_IGNORE_REGEX = "^.*\\?";

    private static final String QUERY_STRING_SPLIT_SIGN = "&";

    private static final String EMPTY_STRING = "";

    private static final Pattern QUERY_PARAM_PATTERN = Pattern.compile("^[^=\\s]+(=(\\S+)?)?");


    public static MultiValueMap<String, String> parseToParameters(String queryStringLine) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        for(String queryKeyValue : queryStringLine.split(QUERY_STRING_SPLIT_SIGN)) {
            setQueryParam(parameters, queryKeyValue);
        }

        return parameters;
    }

    private static void setQueryParam(MultiValueMap<String, String>parameters, String queryKeyValue) {

        if(!isValidQueryParamPattern(queryKeyValue)) {
            throw new IllegalArgumentException("queryString 형식이아닙니다.");
        }

        String[] keyAndValue = splitQueryKeyValue(queryKeyValue);

        if(keyAndValue.length != QUERY_KEY_VALUE_SPLIT_LIMIT) {
            return;
        }

        String key = keyAndValue[0];
        String value = urlDecodeValue(keyAndValue[1]);
        parameters.computeIfAbsent(key, (k) -> new ArrayList<>()).add(value);
    }

    private static String urlDecodeValue(String value){
        try {
            return URLDecoder.decode(value, System.getProperty("file.encoding"));
        } catch (Exception e) {
            logger.error("{}" , e);
        }

        return value;
    }

    private static boolean isValidQueryParamPattern(String queryKeyValue) {

        if(StringUtils.isEmpty(queryKeyValue)) {
            return false;
        }

        if(!QUERY_PARAM_PATTERN.matcher(queryKeyValue).find()) {
            return false;
        }
        return true;
    }

    private static String[] splitQueryKeyValue(String queryKeyValue) {
        return queryKeyValue.split(QUERY_KEY_VALUE_SPLIT_SIGN, QUERY_KEY_VALUE_SPLIT_LIMIT);
    }
}
