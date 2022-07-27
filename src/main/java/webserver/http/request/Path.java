package webserver.http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import exception.InvalidRequestException;

import java.util.Objects;

import static model.Constant.PATH_AND_QUERY_STRING_SPERATOR;
import static model.Constant.ROOT_PATH;

public class Path {
    private final String path;
    private final QueryString queryString;

    public Path(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public Path(String path) {
        String[] pathAndQueryString = validatePathAndQueryString(path);

        this.path = pathAndQueryString[0];
        this.queryString = new QueryString(pathAndQueryString.length > 1 ? pathAndQueryString[1] : "");
    }

    public Path(String path, String queryString) {
        this(path, new QueryString(queryString));
    }

    private String[] validatePathAndQueryString(String value) {
        if (StringUtils.isEmpty(value) || !StringUtils.startsWith(value, ROOT_PATH)) {
            throw new InvalidRequestException("Path");
        }
        return StringUtils.split(value, PATH_AND_QUERY_STRING_SPERATOR);
    }

    public String getPath() {
        return path;
    }

    public QueryString getQueryString() {
        return queryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path1 = (Path) o;
        return Objects.equals(path, path1.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
