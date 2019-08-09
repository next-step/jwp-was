package webserver.mapper;

import java.util.Set;

import enums.HttpMethod;
import utils.ConcurrentCollectionUtils;
import webserver.handler.ActionHandler;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class ActionRequestMapper implements RequestMapper {


	private final String mappedRequestUri;

	private final ActionHandler actionHandler;

	private final Set<HttpMethod> httpMethods;


	public ActionRequestMapper(String mappedRequestUri, ActionHandler actionHandler) {
		this(mappedRequestUri, actionHandler, HttpMethod.values());
	}

	public ActionRequestMapper(String mappedRequestUri, ActionHandler actionHandler, HttpMethod[] httpMethods) {
		this.mappedRequestUri = mappedRequestUri;
		this.actionHandler = actionHandler;
		this.httpMethods = ConcurrentCollectionUtils.convertToConcurrentSet(httpMethods);
	}

	@Override
	public boolean isMatchedRequest(HttpMethod method, String requestUri) {
		return this.httpMethods.contains(method) && mappedRequestUri.equalsIgnoreCase(requestUri);
	}

	@Override
	public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
		actionHandler.actionHandle(httpRequest, httpResponse);
	}
}
