package webserver.http;

import webserver.ModelAndView;
import webserver.http.response.ResponseSender;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private ResponseSender sender;
    private String contentType;
    private Map<String, String> cookies;
    private static final String ERROR_PAGE = "/error.html";

    public HttpResponse(DataOutputStream dos, HttpRequest httpRequest) {
        sender = new ResponseSender(dos, ERROR_PAGE);
        contentType = httpRequest.getResponseContentType();
        cookies = new HashMap<>();
    }

    public void addCookie(String key, String value) {
        this.cookies.put(key, value);
    }
    public String getContentType() {
        return contentType;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void forward(HttpRequest request, String location) {
        ModelAndView mav = new ModelAndView(request.getAttributes(), location);
        sender.forward(this, mav);
    }

    public void sendRedirect(String location) {
        ModelAndView mav = new ModelAndView(location);
        sender.redirect(this, mav);
    }

    public void sendError(HttpStatusCode statusCode) {
        if (statusCode == HttpStatusCode.NOT_FOUND) {
            sender.notFound(this);
        }
    }
}
