package webserver.response;

public class ResponseFactory {
    private ResponseFactory() {}

    public static Response createUnauthorized(String message) {
        ResponseHeader responseHeader = new ResponseHeader(HttpStatus.UNAUTHORIZED)
                .setContentType("text/html;charset=utf-8");
        ResponseBody responseBody = new ResponseBody(message.getBytes());
        return new Response(responseHeader, responseBody);
    }

    public static Response createNotFound(String message) {
        ResponseHeader responseHeader = new ResponseHeader(HttpStatus.NOT_FOUND)
                .setContentType("text/html;charset=utf-8");
        ResponseBody responseBody = new ResponseBody(message.getBytes());
        return new Response(responseHeader, responseBody);
    }

    public static Response createNotImplemented() {
        ResponseHeader responseHeader = new ResponseHeader(HttpStatus.NOT_IMPLEMENTED)
                .setContentType("text/html;charset=utf-8");
        ResponseBody responseBody = new ResponseBody("Not Implemented Yet".getBytes());
        return new Response(responseHeader, responseBody);
    }
}
