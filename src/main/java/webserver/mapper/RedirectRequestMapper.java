package webserver.mapper;

import enums.HttpMethod;
import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class RedirectRequestMapper implements RequestMapper{

	private final String mappedRequestUri;
	private final String redirectUrl;
	
    private RedirectRequestMapper(String mappedRequestUri, String redirectUrl) {
    	this.mappedRequestUri = mappedRequestUri;
    	this.redirectUrl = redirectUrl;
    }
    
    public static RedirectRequestMapper of(String mappedRequestUri, String redirectUrl) {
    	return new RedirectRequestMapper(mappedRequestUri, redirectUrl);
    }
    
    @Override
    public boolean isMatchedRequest(HttpMethod method, String requestUri) {

        if(HttpMethod.GET != method) {
            return false;
        }

        return mappedRequestUri.equalsIgnoreCase(requestUri);
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.sendRedirect(redirectUrl);
    }
}
