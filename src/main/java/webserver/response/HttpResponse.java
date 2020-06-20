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
    private ResponseBody responseBody = new ResponseBody();

    public void addCookie(String value) {
        responseHeaders.addHeader("Set-Cookie", value, "Path=/");
    }

    public void response(ModelAndView mav, HttpRequest request) {
        if (mav.isRedirect()) {
            responseLine = ResponseLine.of(HttpResponseStatus.FOUND);
            responseHeaders.addHeader("Location",
                    "http://" + request.getRequestHeaders().get("Host") + mav.getRedirectView());
            return;
        }
        responseLine = ResponseLine.of(HttpResponseStatus.OK);
        responseBody = ResponseBody.of(mav);
        responseHeaders.addHeader("Content-Type", responseBody.getContentType(), "charset=utf-8");
        responseHeaders.addHeader("Content-Length", responseBody.getLength());
    }

    public void response(HttpRequest httpRequest) throws IOException, URISyntaxException {
        responseLine = ResponseLine.of(HttpResponseStatus.OK);
        responseBody = ResponseBody.of(httpRequest.getRequestLine());
        responseHeaders.addHeader("Content-Type", responseBody.getContentType(), "charset=utf-8");
        responseHeaders.addHeader("Content-Length", responseBody.getLength());
    }

    public void sendResponse(DataOutputStream dos) throws IOException {
        responseLine.response(dos);
        responseHeaders.response(dos);
        responseBody.response(dos);
    }
}
