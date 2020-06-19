package webserver.response;

import lombok.NoArgsConstructor;
import webserver.controller.ModelAndView;
import webserver.request.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

@NoArgsConstructor
public class HttpResponse {

    private ResponseLine responseLine;
    private ResponseHeaders responseHeaders = new ResponseHeaders();
    private ResponseBody responseBody;

    public HttpResponse(ResponseHeaders responseHeaders, ResponseBody responseBody) {
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public static HttpResponse of(HttpRequest httpRequest) throws IOException, URISyntaxException {
        ResponseHeaders responseHeaders = new ResponseHeaders();
        ResponseBody responseBody = ResponseBody.of(httpRequest.getRequestLine());
        responseHeaders.addHeader("Content-Type", responseBody.getContentType(), "charset=utf-8");
        responseHeaders.addHeader("Content-Length", responseBody.getLength());
        return new HttpResponse(responseHeaders, responseBody);
    }

    public void addCookie(String value) {
        responseHeaders.addHeader("Set-Cookie", value, "Path=/");
    }

    public void response(DataOutputStream dos, ModelAndView mav, HttpRequest request) throws IOException {
        if (mav.isRedirect()) {
            responseLine = ResponseLine.of(HttpResponseStatus.FOUND);
            responseHeaders.addHeader("Location", "http://" + request.getRequestHeaders().get("Host") + mav.getRedirectView());
            responseLine.response(dos);
            responseHeaders.response(dos);
            return;
        }
        responseLine = ResponseLine.of(HttpResponseStatus.OK);
        responseBody = ResponseBody.of(mav);
        responseHeaders.addHeader("Content-Type", responseBody.getContentType(), "charset=utf-8");
        responseHeaders.addHeader("Content-Length", responseBody.getLength());
        responseLine.response(dos);
        responseHeaders.response(dos);
        responseBody.response(dos);
    }

    public void response(DataOutputStream dos) throws IOException {
        responseLine = ResponseLine.of(HttpResponseStatus.OK);
        responseLine.response(dos);
        responseHeaders.response(dos);
        responseBody.response(dos);
    }
}
