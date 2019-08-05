package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import utils.QueryStringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class QueryParams {

    private static final Logger logger = LoggerFactory.getLogger(QueryParams.class);

    private static final int QUERY_KEY_VALUE_SPLIT_LIMIT = 2;

    private static final String QUERY_KEY_VALUE_SPLIT_SIGN = "=";
    
    private static final String PATH_QUERY_STRING_IGNORE_REGEX = "^.*\\?";

    private static final String QUERY_STRING_SPLIT_SIGN = "&";

    private static final String EMPTY_STRING = "";

    private static final Pattern QUERY_PARAM_PATTERN = Pattern.compile("^[^=\\s]+(=(\\S+)?)?");

    private final MultiValueMap<String, String> parameterValuesByName;

    private QueryParams(MultiValueMap<String, String> parameterValuesByName){
        this.parameterValuesByName = parameterValuesByName;
    }

    public static QueryParams parseByPath(String path) {

        String queryStringLine= pathToQueryStringLine(path);
        return new QueryParams(QueryStringUtils.parseToParameters(queryStringLine));
    };

    private static String pathToQueryStringLine(String path) {
        return path.replaceAll(PATH_QUERY_STRING_IGNORE_REGEX, EMPTY_STRING);
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
