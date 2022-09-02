package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private ResponseLine responseLine;
    private HttpHeader header;
    private ResponseBody body;

    public void redirect(String location) {
        this.responseLine = ResponseLine.redirect();
        this.header = new HttpHeader.HttpHeaderBuilder()
                .location(location)
                .build();
    }

    public void forward(ResponseBody body, String contentType) {
        this.body = body;
        this.header = new HttpHeader.HttpHeaderBuilder()
                .contentLength(body.getLength())
                .contentType(contentType)
                .build();
        this.responseLine = ResponseLine.success();
    }

    public void loginRedirect(String location, Boolean loginValue) {
        this.header = new HttpHeader.HttpHeaderBuilder()
                .cookie("logined", loginValue.toString())
                .location(location)
                .build();
        this.responseLine = ResponseLine.redirect();
    }

    public void writeResponse(DataOutputStream dos) throws IOException {
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
}
