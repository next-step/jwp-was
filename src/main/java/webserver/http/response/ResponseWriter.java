package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter {
    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);

    public static final String LINE_SEPARATOR = "\r\n";

    private final DataOutputStream out;

    public ResponseWriter(OutputStream out) {
        this.out = new DataOutputStream(out);
    }

    public void process(HttpResponse httpResponse) {
        try {
            writeResponseLine(httpResponse.getResponseLine());
            writeResponseHeader(httpResponse.getResponseHeader());
            writeResponseBody(httpResponse.getResponseBody());
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponseHeader(ResponseHeader responseHeader) throws IOException {
        out.writeBytes(responseHeader.getHeader());
    }

    private void writeResponseLine(ResponseLine responseLine) throws IOException {
        out.writeBytes(responseLine.getResponseLine());
    }

    private void writeResponseBody(ResponseBody responseBody) throws IOException {
        out.writeBytes(LINE_SEPARATOR);
        out.write(responseBody.getResponseBody(), 0, responseBody.getContentLength());
    }
}
