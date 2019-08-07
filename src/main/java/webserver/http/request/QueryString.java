package webserver.http.request;

/**
 * @author : yusik
 * @date : 2019-08-03
 */
public class QueryString {

    private static final String QUERY_SEPARATOR = "&";
    private static final String PARAMETER_SEPARATOR = "=";

    private final ParameterMap parameters;

    public static QueryString parse(String queryString) {

        ParameterMap map =  new ParameterMap();
        String[] paramPairs = queryString.split(QUERY_SEPARATOR);
        for (String paramPair : paramPairs) {
            String[] tokens = paramPair.split(PARAMETER_SEPARATOR);
            if (tokens.length == 2 && !"".equals(tokens[0])) {
                map.put(tokens[0], tokens[1]);
            } else {
                throw new IllegalArgumentException("유효하지 않은 문자열입니다.");
            }
        }

        return new QueryString(map);
    }

    private QueryString(ParameterMap map) {
        this.parameters = map;
    }

    public ParameterMap getParameters() {
        return parameters;
    }
}
