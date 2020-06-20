package webserver.response;

import lombok.NoArgsConstructor;
import webserver.controller.ModelAndView;
import webserver.request.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static webserver.response.HttpResponseHeader.*;

@NoArgsConstructor
public class HttpResponse {

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
                    HTTP_PROTOCOL_PREFIX + request.getRequestHeaders().get("Host") + mav.getRedirectView());
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
        responseLine.response(dos);
        responseHeaders.response(dos);
        responseBody.response(dos);
    }
}
