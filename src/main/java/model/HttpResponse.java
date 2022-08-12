package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.Protocol;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private ResponseLine responseLine;
    private HttpHeader header;
    private ResponseBody body;
    private OutputStream outputStream;

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.body = ResponseBody.empty();
    }

    public void redirect(String location) {
        final HttpHeader httpHeader = new HttpHeader();
        final ResponseLine responseLine = ResponseLine.redirect();
        httpHeader.addLocation(location);

        this.responseLine = responseLine;
        this.header = httpHeader;
    }

    public void success(ResponseBody body, String contentType) {
        final HttpHeader httpHeader = new HttpHeader();
        httpHeader.addContentType(contentType);
        httpHeader.addContentLength(body.getLength());

        this.body = body;
        this.header = httpHeader;
        this.responseLine = ResponseLine.success();
    }

    public void loginRedirect(String location, Boolean loginValue) {
        final HttpHeader httpHeader = new HttpHeader();
        httpHeader.addLocation(location);
        httpHeader.addCookie("logined", loginValue.toString());

        this.header = httpHeader;
        this.responseLine = ResponseLine.redirect();
    }

    public void writeResponse() throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        try {
            responseLine.writeOutput(dos);
            header.writeOutput(dos);
            body.writeOutput(dos);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        dos.flush();
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }

    public void forward(String location) throws IOException, URISyntaxException {
        body = new ResponseBody(FileIoUtils.loadFileFromClasspath(location));
    }

    public void sendRedirect(String location) {
        header.addValue("Location", location);
    }

    public void addHeader(String name, String value) {
        header.addValue(name, value);
    }
}
