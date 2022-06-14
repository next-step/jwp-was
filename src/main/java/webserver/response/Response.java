package webserver.response;

public class Response {
    private final ResponseHeader responseHeader;
    private final ResponseBody responseBody;

    public Response(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
        this.responseBody = new ResponseBody(new byte[0]);
    }

    public Response(ResponseHeader responseHeader, ResponseBody responseBody) {
        this.responseBody = responseBody;
        this.responseHeader = responseHeader
                .setContentLength(responseBody.getContentLength());
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
