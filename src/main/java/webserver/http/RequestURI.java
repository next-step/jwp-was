package webserver.http;

import java.util.Objects;

class RequestURI {

    private static final int LENGTH_CONTAINING_QUERY_STR = 2;

    private static final int PATH_IDX = 0;

    private static final int QUERY_STRING_IDX = 1;

    private static final String FIRST_CHAR_OF_PATH = "/";

    private static final String QUERY_STR_DELIMITER = "\\?";

    private final String path;

    private final RequestParameters parameters;

    RequestURI(String path) {
        validatePath(path);
        String[] pathAndParameters = path.split(QUERY_STR_DELIMITER);
        this.path = pathAndParameters[PATH_IDX];
        this.parameters = pathAndParameters.length >= LENGTH_CONTAINING_QUERY_STR ?
                RequestParameters.parseOf(pathAndParameters[QUERY_STRING_IDX]) :
                RequestParameters.EMPTY;
    }

    String getPath() {
        return path;
    }

    String getParameter(String name) {
        return parameters.getValue(name);
    }

    private void validatePath(String path) {
        if (path != null && !path.startsWith(FIRST_CHAR_OF_PATH)) {
            throw new IllegalArgumentException(String.format("[%s] 유효한 RequestPath 가 아님", path));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestURI that = (RequestURI) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    @Override
    public String toString() {
        return path;
    }
}
