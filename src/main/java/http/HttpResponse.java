package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;
    private DataOutputStream dataOutputStream;
    private boolean responseDone = false;

    private HttpResponse(DataOutputStream dataOutputStream) {
        this.httpStatus = HttpStatus.OK;
        this.httpHeaders = HttpHeaders.emptyHeaders();
        this.dataOutputStream = dataOutputStream;
    }

    public static HttpResponse from(DataOutputStream dataOutputStream) {
        return new HttpResponse(dataOutputStream);
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setHeader(String key, String value) {
        this.httpHeaders.put(key, value);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setContentType(String contentType) {
        setHeader(HttpHeaders.CONTENT_TYPE, contentType);
    }

    public void setContentLength(long length) {
        setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(length));
    }

    public Map<String, String> getHeaders() {
        return httpHeaders;
    }

    public void sendRedirect(String location) {
        setHeader(HttpHeaders.LOCATION, location);
        setHttpStatus(HttpStatus.FOUND);

        try {
            dataOutputStream.writeBytes("HTTP/1.1 " + httpStatus.getValue() + " " + httpStatus.getMessage() + "\r\n");

            for (String key : httpHeaders.keySet()) {
                dataOutputStream.writeBytes(key + ": " + httpHeaders.get(key) + "\r\n");
            }
            dataOutputStream.writeBytes(System.lineSeparator());
        } catch(IOException e) {
            logger.error(e.getMessage());
        }

        responseDone();
    }

    public void writeHeader() throws IOException {

        dataOutputStream.writeBytes("HTTP/1.1 " + httpStatus.getValue() + " " + httpStatus.getMessage() + "\r\n");
        for (String key : httpHeaders.keySet()) {
            dataOutputStream.writeBytes(key + ": " + httpHeaders.get(key) + "\r\n");
        }
        dataOutputStream.writeBytes(System.lineSeparator());
    }

    public boolean isResponseDone() {
        return responseDone;
    }

    public void responseDone() {
        responseDone = true;
        try {
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
