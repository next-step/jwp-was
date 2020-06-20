package http.response;

import http.common.HttpHeader;
import http.common.HttpHeaders;
import http.request.HttpProtocol;
import org.slf4j.Logger;


import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;

import static org.slf4j.LoggerFactory.getLogger;

public class ResponseMessage {

    private static final Logger logger = getLogger(ResponseMessage.class);
    public static final String CRLF = "\r\n";
    public static final String SPACE = " ";
    public static final String EMPTY_STRING = "";

    private final DataOutputStream dos;
    private final HttpHeaders httpHeaders = new HttpHeaders(Collections.emptyList());
    private byte[] body = EMPTY_STRING.getBytes();

    public ResponseMessage(DataOutputStream dos) {
        this.dos = dos;
    }

    public void setHeader(HttpHeader name, String value) {
        this.httpHeaders.set(name.getText(), value);
    }

    private void setBody(byte[] body) {
        setHeader(HttpHeader.CONTENT_LENGTH, Integer.toString(body.length));
        this.body = body;
    }

    public void responseWith(HttpStatus status, byte[] body, ContentType contentType) {
        setHeader(HttpHeader.CONTENT_TYPE, contentType.toStringWithCharsetUTF8());
        setBody(body);
        write(status);
    }

    public void write(HttpStatus status) {
        try {
            writeStatusLine(status);
            writeHeader();
            writeBody();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeStatusLine(HttpStatus status) throws IOException {
        dos.writeBytes(HttpProtocol.HTTP_V1_1.toJoinedString() + SPACE + status + CRLF);
    }

    private void writeHeader() throws IOException {
        if (httpHeaders.size() > 0) {
            dos.writeBytes(this.httpHeaders.toJoinedString());
        }
        dos.writeBytes(CRLF);
    }

    private void writeBody() throws IOException {
        dos.write(this.body, 0, this.body.length);
        dos.flush();
    }

    public void redirectTo(String url) {
        setHeader(HttpHeader.LOCATION, url);
        try {
            writeStatusLine(HttpStatus.REDIRECT);
            writeHeader();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
