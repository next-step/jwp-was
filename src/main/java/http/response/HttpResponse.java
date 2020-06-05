package http.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import http.HttpStatus;
import http.request.HttpRequest;
import lombok.Getter;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

@Getter
public class HttpResponse {
    private ResponseHeader header;
    private byte[] body;

    private HttpResponse(ResponseHeader header, byte[] body) {
        this.header = header;
        this.body = body;
    }

    public static HttpResponse body(HttpStatus httpStatus, Object data, String contentType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String dataValue = mapper.writeValueAsString(data);
        byte[] dataByte = dataValue.getBytes();

        return new HttpResponse(ResponseHeader.of(httpStatus, contentType, dataByte.length), dataByte);
    }

    public static HttpResponse loadFile(HttpRequest request) throws IOException, URISyntaxException {
        byte[] dataByte = FileIoUtils.loadFileFromClasspath(request.getFilePath());

        return new HttpResponse(ResponseHeader.of(HttpStatus.OK, request.getContentType(), dataByte.length), dataByte);
    }

    public static HttpResponse redirect(String location) {
        return new HttpResponse(ResponseHeader.of(HttpStatus.FOUND, "text/html", 0, location), null);
    }

    public String getContentType() {
        return this.header.getContentType();
    }

    public int getContentLength() {
        return this.header.getContentLength();
    }

    public int getStatusCode() {
        return this.header.getStatusCode();
    }

    public String getStatusName() {
        return this.header.getStatusName();
    }

    public String getLocation() {
        return this.header.getLocation();
    }
}
