package webserver.http.response;

import webserver.ModelAndView;
import webserver.http.HttpHeader;
import webserver.http.request.HttpRequest;

import java.io.IOException;
import java.net.URISyntaxException;

import static webserver.http.HttpHeader.*;

public class HttpResponseProcessor {

    private static final String HTTP_PROTOCOL_PREFIX = "http://";

    public static void init(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        ResponseLine responseLine = httpResponse.getResponseLine();
        responseLine.setStatus(HttpResponseStatus.OK);

        ResponseBody responseBody = httpResponse.getResponseBody();
        responseBody.addBody(httpRequest.getRequestLine());

        ResponseHeaders responseHeaders = httpResponse.getResponseHeaders();
        responseHeaders.addHeader(CONTENT_TYPE, responseBody.getContentType(), "charset=utf-8");
        responseHeaders.addHeader(CONTENT_LENGTH, responseBody.getLength());
    }

    public static void init(ModelAndView mav, HttpRequest httpRequest, HttpResponse httpResponse) {
        ResponseLine responseLine = httpResponse.getResponseLine();
        ResponseHeaders responseHeaders = httpResponse.getResponseHeaders();
        ResponseBody responseBody = httpResponse.getResponseBody();

        if (mav.isRedirect()) {
            responseLine.setStatus(HttpResponseStatus.FOUND);
            responseHeaders.addHeader(LOCATION,
                    HTTP_PROTOCOL_PREFIX + httpRequest.getRequestHeaders().get(HttpHeader.HOST) + mav.getRedirectView());
            return;
        }
        responseLine.setStatus(HttpResponseStatus.OK);
        responseBody.addBody(mav);
        responseHeaders.addHeader(CONTENT_TYPE, responseBody.getContentType(), "charset=utf-8");
        responseHeaders.addHeader(CONTENT_LENGTH, responseBody.getLength());
    }
}
