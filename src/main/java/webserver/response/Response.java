package webserver.response;

public class Response {
    private final ResponseHeader responseHeader;
    private final ResponseBody responseBody;

    public Response(ResponseHeader responseHeader, ResponseBody responseBody) {
        this.responseBody = responseBody;
        this.responseHeader = responseHeader
                .setContentLength(responseBody.getContentLength());
    }

    public static Response createNotImplemented() {
        ResponseHeader responseHeader = new ResponseHeader(HttpStatus.NOT_IMPLEMENTED)
                .setContentType("text/html;charset=utf-8");
        ResponseBody responseBody = new ResponseBody("Not Implemented Yet".getBytes());
        return new Response(responseHeader, responseBody);
    }

    public byte[] toBytes() {
        byte[] headerBytes = responseHeader.toBytes();
        byte[] bodyBytes = responseBody.toBytes();
        byte[] responseBytes = new byte[headerBytes.length + bodyBytes.length];
        System.arraycopy(headerBytes, 0, responseBytes, 0, headerBytes.length);
        System.arraycopy(bodyBytes, 0, responseBytes, headerBytes.length, bodyBytes.length);
        return responseBytes;
    }
}
