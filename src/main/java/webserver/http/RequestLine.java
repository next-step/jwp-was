package webserver.http;

class RequestLine {
    private final String method;
    private final String path;
    private final String httpVersion;
    private final QueryString queryString;

    private RequestLine(String method, String path, String httpVersion, QueryString queryString) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
        this.queryString = queryString;
    }

    static RequestLine parse(String requestString) {
        String[] lineSplit = requestString.split(" ");
        if (!RequestLine.validate(lineSplit)) {
            throw new IllegalArgumentException("요청 헤더에서 RequestLine 항목을 얻지 못했습니다.");
        }
        String method = lineSplit[0];
        String path = lineSplit[1];
        String httpVersion = lineSplit[2];

        int queryStringStartIndex = path.indexOf("?");
        String queryStringSource = queryStringStartIndex > -1 ? path.substring(queryStringStartIndex + 1) : "";
        QueryString queryString = QueryString.parse(queryStringSource);
        return new RequestLine(method, path, httpVersion, queryString);
    }

    private static boolean validate(String[] lineSplit) {
        return lineSplit != null && lineSplit.length > 2;
    }

    String getMethod() {
        return method;
    }

    String getPath() {
        return path;
    }

    QueryString getQueryString() {
        return queryString;
    }

    String getHttpVersion() {
        return httpVersion;
    }
}
