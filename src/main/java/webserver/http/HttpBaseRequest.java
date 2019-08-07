package webserver.http;

import enums.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import utils.IOUtils;
import utils.StringUtils;
import webserver.handler.ModelView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public class HttpBaseRequest implements HttpRequest {

	private final ModelView modelView;
	
    private final RequestLine requestLine;
    
    private final String requestUri;
    
    private final HttpHeaders httpHeaders;

    private final HttpCookies httpCookies;
    
    private final String body;
    
    private final MultiValueMap<String, String> params;

    private HttpSession httpSession;
    private boolean hasNewSession = false;


    private HttpBaseRequest(RequestLine requestLine, HttpHeaders httpHeaders, String body) {
    	this.modelView = new ModelView();
        this.requestLine = requestLine;
        this.requestUri = parseRequestUri(requestLine.getPath());
        this.httpHeaders = httpHeaders;
        this.httpCookies = HttpCookies.of(this.httpHeaders.getHeaderValueFirst(HttpHeaders.COOKIE));
        this.body = body;
        this.params = new LinkedMultiValueMap<>(QueryParams.parseByPath(requestLine.getPath()).getParameters());
    }

    public static String parseRequestUri(String path) {
        return path.replaceAll("http(s)?://.*?(?=/)", "")
                .replaceAll("\\?.*$", "");
    }

    public static HttpBaseRequest parse(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
        HttpHeaders httpHeaders = new HttpHeaders();
        String headerLine;
        while (!StringUtils.isEmpty(headerLine = bufferedReader.readLine())) {
            httpHeaders.addHeaderLine(headerLine);
        }

        String body = null;
        int contentLength = (int) httpHeaders.getContentLength();
        if (contentLength != -1) {
            body = IOUtils.readData(bufferedReader, contentLength);
        }

        return new HttpBaseRequest(requestLine, httpHeaders, body);
    }

    public HttpMethod getMethod() {
        return this.requestLine.getMethod();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public String getParameter(String name) {
        return Optional.ofNullable(getParameterValues(name))
                .filter(values -> values.length > 0)
                .map(values -> values[0])
                .orElse(null);
    }

    public String[] getParameterValues(String name) {
        return Optional.ofNullable(this.params.get(name))
                .map(values -> values.toArray(new String[values.size()]))
                .orElse(null);
    }

    public String getHeader(String name) {
        return this.httpHeaders.getHeaderValueFirst(name);
    }

    public String getBody() {
        return this.body;
    }

    public String getRequestURI() {
        return this.requestUri;
    }

	public ModelView getModelView() {
		return this.modelView;
	}

    @Override
    public HttpCookie getCookie(String cookieName) {
        return Optional.ofNullable(httpCookies)
                .map(cookies -> cookies.getCookie(cookieName))
                .orElse(null);
    }

    @Override
    public HttpSession getSession() {
        return getSession(true);
    }

    @Override
    public HttpSession getSession(boolean create) {

        setSessionIfNull();

        String cookieSessionId = Optional.ofNullable(this.httpSession).map(session -> session.getId()).orElse("");

        HttpSessionManager httpSessionManager = HttpSessionManager.getInstance();

        if(!create) {
            return this.httpSession;
        }

        httpSessionManager.invalidate(cookieSessionId);
        this.hasNewSession = true;
        this.httpSession = httpSessionManager.newHttpSession();
        return this.httpSession;
    }



    @Override
    public boolean hasNewSession() {
        return this.hasNewSession;
    }

    private void setSessionIfNull(){

        if(this.httpSession != null) {
            return;
        }

        HttpSessionManager httpSessionManager = HttpSessionManager.getInstance();
        String cookieSessionId = httpSessionManager.getSessionIdFromCookieValues(this.httpCookies);
        this.httpSession = httpSessionManager.getHttpSession(cookieSessionId);
    }

}
