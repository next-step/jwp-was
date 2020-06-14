package http;

import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

public class RequestLine {
    private static final int SPLIT_BY_SPACE_COUNT = 3;
    private final Method method;
    private final String path;
    // TODO HttpProtocol로 고쳐야함
    private final String protocol;
    private final String version;
    private final QueryMap queryMap;

    private RequestLine(Method method, String path, String protocol, String version, QueryMap queryMap) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
        this.queryMap = queryMap;
    }

    @Nonnull
    public static RequestLine from(@Nullable String requestLine) {
        if (StringUtils.isEmpty(requestLine)) {
            return makeEmptyRequestLine();
        }

        String[] splitBySpace = requestLine.split(" ");
        if (splitBySpace.length < SPLIT_BY_SPACE_COUNT) {
            return makeEmptyRequestLine();
        }
        String method = splitBySpace[0];
        String pathWithQueryString = splitBySpace[1];
        String protocolAndVersion = splitBySpace[2];

        if (protocolAndVersion.length() < 2) {
            return makeEmptyRequestLine();
        }
        String[] splitBySlash = protocolAndVersion.replaceAll("\n", "").split("/");
        String protocol = splitBySlash[0];
        String version = splitBySlash[1];

        return new RequestLine(Method.find(method), makePath(pathWithQueryString), protocol, version, makeQueryMap(pathWithQueryString));
    }

    @Nonnull
    public static RequestLine makeEmptyRequestLine() {
        return new EmptyRequsetLine();
    }

    @Nonnull
    private static String makePath(@Nonnull String pathWithQueryString) {
        String[] splitByQuestionMark = pathWithQueryString.split("\\?");
        if (splitByQuestionMark.length < 1) {
            return pathWithQueryString;
        }

        return splitByQuestionMark[0];
    }

    @Nullable
    private static QueryMap makeQueryMap(@Nonnull String pathWithQueryString) {
        String[] splitByQuestionMark = pathWithQueryString.split("\\?");
        if (splitByQuestionMark.length < 2) {
            return null;
        }


        String[] splitByAmpersand = splitByQuestionMark[1].split("&");
        QueryMap queryMap = new QueryMap();
        Arrays.stream(splitByAmpersand)
                .filter(Objects::nonNull)
                .forEach(query -> {
                    String[] splitByEqualSign = query.split("=");
                    if (splitByEqualSign.length < 2) {
                        return;
                    }

                    // 같은 파라미터가 여러개 넘어오면 리스트로 넘겨줘야 하나?
                    queryMap.put(splitByEqualSign[0], splitByEqualSign[1]);
                });

        return queryMap;
    }

    static class EmptyRequsetLine extends RequestLine {
        public EmptyRequsetLine() {
            super(null, "", "", "", null);
        }
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public QueryMap getQueryMap() {
        return queryMap;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
