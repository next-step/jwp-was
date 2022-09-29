package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpHeaders;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.List;

public class ResponseViewFactory {

    private static final Logger logger = LoggerFactory.getLogger(ResponseViewFactory.class);

    public static void write(OutputStream out, HttpResponse httpResponse) {
        try {
            DataOutputStream dos = new DataOutputStream(out);

            writeResponseLine(dos, httpResponse.getResponseLine());
            writeResponseHeaders(dos, httpResponse.getHeaders());
            writeResponseBody(dos, httpResponse.getBody());
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private static void writeResponseLine(DataOutputStream dos, ResponseLine responseLine) throws IOException {
        dos.writeBytes(responseLine + "\r\n");
    }

    private static void writeResponseHeaders(DataOutputStream dos, HttpHeaders httpHeaders) throws IOException {
        List<String> outputHeaders = httpHeaders.getOutputHeaders();
        for (String header : outputHeaders) {
            dos.writeBytes(header + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private static void writeResponseBody(DataOutputStream dos, ResponseBody responseBody) throws IOException, URISyntaxException {
        dos.write(responseBody.getContents(), 0, responseBody.getContentsLength());
        dos.flush();
    }
}
