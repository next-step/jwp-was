package webserver.http;

import enums.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.MimeTypeUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final HttpRequest httpRequest;

    private final HttpHeaders httpHeaders;

    private final DataOutputStream outputStream;

    private HttpStatus httpStatus = HttpStatus.OK;

    private byte[] responseBody;

    private HttpResponse(HttpRequest httpRequest, OutputStream out){
        this.httpRequest = httpRequest;
        this.httpHeaders = new HttpHeaders();
        this.outputStream = new DataOutputStream(out);
    }

    public static HttpResponse of(HttpRequest httpRequest, OutputStream out) {

        return new HttpResponse(httpRequest, out);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setHttpHeader(String name, String value) {
        this.httpHeaders.setHeader(name, value);
    }

    public HttpHeaders getHttpHeaders(){
        return this.httpHeaders;
    }
    
    public List<String> getHttpHeaderLines(){
        return this.httpHeaders.getHeaderLines();
    }

    public byte[] getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(byte[] responseBody) {
        setHttpHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(responseBody.length));
        this.responseBody = responseBody;
    }

    public void sendRedirect(String redirectUrl) {
        this.setHttpStatus(HttpStatus.FOUND);
        this.setHttpHeader(HttpHeaders.LOCATION, redirectUrl);
    }

    public void sendResource(URL resourceUrl){
        byte[] body = FileIoUtils.loadFileFromURL(resourceUrl);
        this.setResponseBody(body);

        String mimeType = MimeTypeUtils.guessContentTypeFromName(resourceUrl.getFile(), httpRequest.getHeader(HttpHeaders.ACCEPT));
        this.setHttpHeader(HttpHeaders.CONTENT_TYPE, mimeType);
    }

    public void send404(){
        this.setHttpStatus(HttpStatus.NOT_FOUND);
    }

    public void writeResponse() {
        writeStatusLine(this.outputStream, this.getHttpStatus());
        writeHeaderLines(this.outputStream, this.getHttpHeaderLines());
        writeBody(this.outputStream, this.getResponseBody());
        writeFlush(this.outputStream);
    }

    private void writeStatusLine(DataOutputStream dos, HttpStatus httpStatus) {
        try {
            dos.writeBytes("HTTP/1.1 " + httpStatus.getValue() + " " + httpStatus.getReasonPhrase() + " \r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeaderLines(DataOutputStream dos, List<String> headerLines) {
        try {
            for (String headerLine : headerLines) {
                dos.writeBytes(headerLine + " \r\n");
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeBody(DataOutputStream dos, byte[] body) {
        if (body == null) {
            return;
        }

        try {
            dos.write(body, 0, body.length);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeFlush(DataOutputStream dos) {
        try {
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
