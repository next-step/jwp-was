package webserver.http.response;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ModelAndView;
import webserver.http.HttpHeader;
import webserver.http.request.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

import static webserver.http.HttpHeader.*;

@NoArgsConstructor
public class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private static final String HTTP_PROTOCOL_PREFIX = "http://";

    private ResponseLine responseLine;
    private ResponseHeaders responseHeaders = new ResponseHeaders();
    private ResponseBody responseBody = new ResponseBody();

    public void addCookie(String value) {
        responseHeaders.addHeader(SET_COOKIE, value, "Path=/");
    }

    public void response(ModelAndView mav, HttpRequest request) {
        if (mav.isRedirect()) {
            responseLine = ResponseLine.of(HttpResponseStatus.FOUND);
            responseHeaders.addHeader(LOCATION,
                    HTTP_PROTOCOL_PREFIX + request.getRequestHeaders().get(HttpHeader.HOST) + mav.getRedirectView());
            return;
        }
        responseLine = ResponseLine.of(HttpResponseStatus.OK);
        responseBody = ResponseBody.of(mav);
        responseHeaders.addHeader(CONTENT_TYPE, responseBody.getContentType(), "charset=utf-8");
        responseHeaders.addHeader(CONTENT_LENGTH, responseBody.getLength());
    }

    public void response(HttpRequest httpRequest) throws IOException, URISyntaxException {
        responseLine = ResponseLine.of(HttpResponseStatus.OK);
        responseBody = ResponseBody.of(httpRequest.getRequestLine());
        responseHeaders.addHeader(CONTENT_TYPE, responseBody.getContentType(), "charset=utf-8");
        responseHeaders.addHeader(CONTENT_LENGTH, responseBody.getLength());
    }

    public void sendResponse(DataOutputStream dos) throws IOException {
        responseLine(dos);
        responseHeader(dos);
        responseBody(dos);
    }

    private void responseLine(DataOutputStream dos) throws IOException {
        dos.writeBytes(responseLine.response());
    }

    private void responseHeader(DataOutputStream dos) {
        Map<HttpHeader, ResponseHeader> headers = responseHeaders.getResponseHeaders();
        try {
            for (HttpHeader name : headers.keySet()) {
                dos.writeBytes(name + ": " + headers.get(name));
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos) {
        byte[] file = responseBody.getFile();
        if (Objects.isNull(file)) {
            return;
        }

        try {
            dos.write(file, 0, file.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
