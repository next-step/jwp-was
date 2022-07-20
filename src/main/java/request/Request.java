package request;

import model.User;
import utils.FileIoUtils;
import utils.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;

public class Request {
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    public Request(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static Request parsing(BufferedReader br) throws IOException {
        RequestLine requestLine = RequestLine.getInstance().parsing(br);
        RequestHeader requestHeader = RequestHeader.getInstance().parsing(br);
        RequestBody requestBody = RequestBody.getInstance().parsing(br, requestHeader.getContentLength());

        return new Request(requestLine, requestHeader, requestBody);
    }

    public User convertUserOfQueryParam() {
        if (isPostMethod()) {
            return requestBody.bodyToUser();
        }

        return requestLine.queryStringToUser();
    }

    public boolean requestPathCheck(String path) {
        return getRequestPath().startsWith(path) && !FileIoUtils.isLastEndWithHtml(getRequestPath());
    }

    public boolean isPostMethod() {
        return HttpMethod.POST.equals(getMethod());
    }

    public String getRequestPath() {
        return requestLine.getPath();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
