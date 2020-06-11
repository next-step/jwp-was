package webserver;

import http.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;

@Slf4j
public class ResponseHandler {
    public static final String NEW_LINE_STRING = "\r\n";

    public static void writeHttpResponse(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(httpResponse.getStatusLine() + NEW_LINE_STRING);

            if (httpResponse.hasHttpHeaders()) {
                dos.writeBytes(httpResponse.getHttpHeaderString());
                dos.writeBytes(NEW_LINE_STRING);
            }

            dos.writeBytes(NEW_LINE_STRING);

            if (httpResponse.hasHttpBody()) {
                dos.write(httpResponse.getHttpBody().getBytes(), 0, httpResponse.getHttpBodyLength());
            }
            dos.flush();
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
