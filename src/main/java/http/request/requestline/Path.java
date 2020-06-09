package http.request.requestline;

import lombok.Getter;
import utils.MapUtil;
import utils.StringUtils;

import java.util.Collections;
import java.util.Map;

@Getter
public class Path {
    static final String QUERY_STRING_SPLITTER = "\\?";

    private final String path;
    private final Map<String, String> queryString;

    public Path(String path, Map<String, String> queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static Path of(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException();
        }

        String[] split = path.split(QUERY_STRING_SPLITTER, 2);

        if (split.length == 1) {
            return new Path(
                split[0],
                Collections.emptyMap()
            );
        }

        return new Path(
            split[0],
            MapUtil.buildParameters(split[1])
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("path는 ").append(path).append("\r\n");

        return sb.toString();
    }

}
