package webserver.http;

import java.util.Optional;

import org.springframework.util.MultiValueMap;

import enums.HttpMethod;
import webserver.handler.ModelView;

public class HttpBodyRequest implements HttpRequest {


    private final HttpRequest wrapedRequest;
    
    private final MultiValueMap<String, String> bodyParams;

    public HttpBodyRequest(HttpRequest httpRequest, MultiValueMap<String, String> bodyParams) {
        this.wrapedRequest = httpRequest;
        this.bodyParams = bodyParams;
    }


    @Override
    public HttpMethod getMethod() {
        return wrapedRequest.getMethod();
    }

    @Override
    public String getPath() {
        return wrapedRequest.getPath();
    }

    @Override
    public String getParameter(String name) {
        return Optional.ofNullable(wrapedRequest.getParameter(name))
                .orElseGet(() -> this.bodyParams.getFirst(name));

    }

    @Override
    public String[] getParameterValues(String name) {
        return Optional.ofNullable(wrapedRequest.getParameterValues(name))
                .orElseGet(() -> {
                    return Optional.ofNullable(this.bodyParams.get(name))
                            .map(values -> values.toArray(new String[values.size()]))
                            .orElse(null);
                });
    }

    @Override
    public String getHeader(String name) {
        return wrapedRequest.getHeader(name);
    }

    @Override
    public String getBody() {
        return null;
    }

    @Override
    public String getRequestURI() {
        return wrapedRequest.getRequestURI();
    }


	@Override
	public ModelView getModelView() {
		return wrapedRequest.getModelView();
	}
}
