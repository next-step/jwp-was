package webserver.http;

class RequestLine {
    private final HttpMethod method;
    private final String path;
    private final HttpVersion httpVersion;
    private final QueryString queryString;

    private RequestLine(HttpMethod method, String path, HttpVersion httpVersion, QueryString queryString) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
        this.queryString = queryString;
    }

    static RequestLine parse(String requestString) {
        return RequestLineParser.parse(requestString);
    }

    HttpMethod getMethod() {
        return method;
    }

    String getPath() {
        return path;
    }

    QueryString getQueryString() {
        return queryString;
    }

    HttpVersion getHttpVersion() {
        return httpVersion;
    }

    private static class RequestLineParser {
        static RequestLine parse(String requestString) {
            String[] lineSplit = requestString.split(" ");
            if (!RequestLineParser.validate(lineSplit)) {
                throw new IllegalArgumentException("요청 헤더에서 RequestLine 항목을 얻지 못했습니다.");
            }
            HttpMethod method = HttpMethod.valueOf(lineSplit[0]);
            String path = lineSplit[1];
            HttpVersion httpVersion = HttpVersion.of(lineSplit[2]);
            QueryString queryString = RequestLineParser.parseQueryString(path);
            return new RequestLine(method, path, httpVersion, queryString);
        }

        private static boolean validate(String[] lineSplit) {
            return lineSplit != null && lineSplit.length > 2;
        }

        private static QueryString parseQueryString(String path) {
            int queryStringStartIndex = path.indexOf("?");
            String queryStringSource = queryStringStartIndex > -1 ? path.substring(queryStringStartIndex + 1) : "";
            return QueryString.parse(queryStringSource);
        }
    }
}
