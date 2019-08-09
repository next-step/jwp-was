package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.FileResponse;
import webserver.RequestMapping;
import webserver.ViewResolver;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String REDIRECT_START_WITH = "redirect:";
    private static final String HEADER_HOST_KEY = "Host";
    private static final String REDIRECT_URL_FORMAT = "http://%s%s";
    private static final String SESSION_ID_KEY = "jsessionid";

    private byte[] body;
    private Cookie cookie;
    private HttpHeaders httpHeaders;
    private ModelAndView modelAndView;
    private HttpStatus httpStatus;
    private String redirectPath;

    public HttpResponse() {
        this.cookie = new Cookie();
        this.modelAndView = new ModelAndView();
        this.httpStatus = HttpStatus.OK;
        this.httpHeaders = new HttpHeaders();
    }

    public HttpResponse(HttpStatus httpStatus, byte[] body) {
        this.body = body;
        this.cookie = new Cookie();
        this.modelAndView = new ModelAndView();
        this.httpStatus = httpStatus;
        this.httpHeaders = new HttpHeaders();
    }

    public static HttpResponse createResponse(HttpRequest httpRequest) {
        String requestPath = httpRequest.getPath();
        return FileResponse.getFileResponse(requestPath)
                .orElse(getViewMappingResponse(httpRequest));
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public byte[] getBody() {
        return this.body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public ModelAndView getModelAndView() {
        return modelAndView;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public void responseByStatus(DataOutputStream dos) {
        ResponseWriter.valueByHttpStatus(this.httpStatus)
                .ifPresent(responseWriter -> responseWriter.write(this, dos));
    }

    public void setCookie(String key, String value) {
        this.cookie.set(key, value);
    }

    private static HttpResponse getViewMappingResponse(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        setSession(httpRequest, httpResponse);
        String viewName = getViewName(httpRequest, httpResponse);
        if (viewName.startsWith(REDIRECT_START_WITH)) {
            return getRedirectHttpResponse(httpRequest, httpResponse, viewName);
        }

        httpResponse.setView(viewName);
        return httpResponse;
    }

    private static void setSession(HttpRequest httpRequest, HttpResponse httpResponse) {
        String sessionId = httpRequest.cookieValue(SESSION_ID_KEY);
        HttpSession httpSession = HttpSessionManager.get(sessionId);
        httpRequest.setSession(httpSession);

        if (Strings.isNullOrEmpty(sessionId)) {
            String newSessionId = httpSession.getId();
            httpRequest.addCookie(SESSION_ID_KEY, newSessionId);
            httpResponse.setCookie(SESSION_ID_KEY, newSessionId);
        }
    }

    private void setView(String viewName) {
        this.modelAndView.setView(viewName);
        try {
            setBody(ViewResolver.mapping(modelAndView).getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static HttpResponse getRedirectHttpResponse(HttpRequest httpRequest, HttpResponse httpResponse, String viewName) {
        String redirectPath = viewName.replace(REDIRECT_START_WITH, StringUtils.EMPTY);
        String redirectUrl = String.format(REDIRECT_URL_FORMAT, httpRequest.getHeaderValue(HEADER_HOST_KEY), redirectPath);
        httpResponse.setRedirectPath(redirectUrl);
        httpResponse.setHttpStatus(HttpStatus.REDIRECT);
        return httpResponse;
    }

    private static String getViewName(HttpRequest httpRequest, HttpResponse httpResponse) {
        return RequestMapping.mapping(httpRequest, httpResponse)
                .orElse(StringUtils.EMPTY);
    }
}

