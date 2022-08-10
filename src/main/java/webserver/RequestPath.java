package webserver;

import org.springframework.web.util.UriComponentsBuilder;

public class RequestPath {

    public static final String REGEX_QUESTION_MARK = "\\?";
    public static final int PATH_INDEX = 0;

    private String path;
    private RequestParams params;

    public RequestPath(String request) {
        final String[] requests = request.split(REGEX_QUESTION_MARK);

        this.path = requests[PATH_INDEX];

        if (requests.length <= 1) {
            return;
        }
        this.params = new RequestParams(UriComponentsBuilder.fromUriString(request).build().getQueryParams());
    }

    public String getPath() {
        return path;
    }

    public RequestParams getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "RequestPath{" +
                "path='" + path + '\'' +
                ", params=" + params +
                '}';
    }
}
