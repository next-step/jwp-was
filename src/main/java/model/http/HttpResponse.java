package model.http;

public class HttpResponse {
    private HttpResponseHeader httpResponseHeader;
    private byte[] body;

    private HttpResponse(HttpResponseHeader header) {
        httpResponseHeader = header;
    }

    private HttpResponse(HttpResponseHeader header, byte[] body) {
        this.httpResponseHeader = header;
        this.body = body;
    }

    public static HttpResponse of(HttpResponseHeader header) {
        return new HttpResponse(header);
    }

    public static HttpResponse of(HttpResponseHeader header, byte[] body) {
        return new HttpResponse(header, body);
    }

    public String print() {
        return httpResponseHeader.print() + "\r\n" + body;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "httpRequestHeader=" + httpResponseHeader +
                ", body='" + body + '\'' +
                '}';
    }
}
