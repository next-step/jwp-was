package http.request;

import utils.StringUtil;

import java.util.Map;

public class Path {
    private static final String PATH_AND_QUERY_STRING_DELIMITER = "\\?";
    private static final char DOT = '.';

    private final String path;
    private final QueryString queryString = QueryString.newInstance();

    public Path(final String pathStr) {
        validate(pathStr);

        String[] tokens = pathStr.split(PATH_AND_QUERY_STRING_DELIMITER);

        this.path = tokens[0];

        if (tokens.length > 1) {
            queryString.update(tokens[1]);
        }
    }

    private void validate(String pathStr) {
        if (StringUtil.isEmpty(pathStr)) {
            throw new IllegalArgumentException("Path string is null");
        }
    }

    public String getPath() {
        return path;
    }

    public String getExtension() {
        int lastIndexOfDot = path.lastIndexOf(DOT);

        if (lastIndexOfDot < 0) {
            return null;
        }

        return path.substring(lastIndexOfDot + 1);
    }

    public String getParameter(final String parameterName) {
        return queryString.get(parameterName);
    }

    public void addParameter(String token) {
        queryString.update(token);
    }

    public Map<String, String> getParameters() {
        return queryString.getAll();
    }
}
