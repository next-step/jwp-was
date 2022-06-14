package webserver.response;

public class ResponseFactory {
    private static final String HTML_CONTENT_TYPE = "text/html;charset=utf-8";

    private ResponseFactory() {}

    public static Response createOK(String body) {
        ResponseHeader responseHeader = new ResponseHeader(HttpStatus.OK)
                .setContentType(HTML_CONTENT_TYPE);
        ResponseBody responseBody = ResponseBody.from(body);
        return new Response(responseHeader, responseBody);
    }

    public static Response createRedirect(String location) {
        ResponseHeader responseHeader = new ResponseHeader(HttpStatus.FOUND)
                .setLocation(location);
        return new Response(responseHeader);
    }

    public static Response createUnauthorized(String message) {
        ResponseHeader responseHeader = new ResponseHeader(HttpStatus.UNAUTHORIZED)
                .setContentType(HTML_CONTENT_TYPE);
        ResponseBody responseBody = ResponseBody.from(message);
        return new Response(responseHeader, responseBody);
    }

    public static Response createNotFound(String message) {
        ResponseHeader responseHeader = new ResponseHeader(HttpStatus.NOT_FOUND)
                .setContentType(HTML_CONTENT_TYPE);
        ResponseBody responseBody = ResponseBody.from(message);
        return new Response(responseHeader, responseBody);
    }

    public static Response createNotImplemented() {
        ResponseHeader responseHeader = new ResponseHeader(HttpStatus.NOT_IMPLEMENTED)
                .setContentType(HTML_CONTENT_TYPE);
        ResponseBody responseBody = ResponseBody.from("Not Implemented Yet");
        return new Response(responseHeader, responseBody);
    }
}
