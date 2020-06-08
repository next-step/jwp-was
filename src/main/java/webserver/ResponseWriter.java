package webserver;

import http.Cookies;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

public class ResponseWriter {
    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);

    public static void write(OutputStream out, HttpResponse httpResponse) {
        DataOutputStream dos = new DataOutputStream(out);
        try {
            writeResponseLine(dos, httpResponse);
            writeCookie(dos, httpResponse);
            writeHeader(dos, httpResponse);
            writeResponseBody(dos, httpResponse);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void writeResponseLine(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.writeBytes("HTTP/1.1 " + httpResponse.getStatusCode() + " " + httpResponse.getStatusMessage() + "\r\n");
    }

    private static void writeHeader(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        for (Iterator it = httpResponse.getHeader().iterator(); it.hasNext(); ) {
            String headerName = (String) it.next();
            String headerValue = httpResponse.getHeader(headerName);
            dos.writeBytes(headerName + ": " + headerValue + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private static void writeCookie(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        Cookies cookies = httpResponse.getCookie();
        if (!cookies.isEmpty()) {
            dos.writeBytes("Set-Cookie: " + cookies.stringify() + "\r\n");
        }
    }

    private static void writeResponseBody(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
    }


}
