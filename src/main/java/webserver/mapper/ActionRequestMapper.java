package webserver.mapper;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.google.common.base.Functions;

import enums.HttpMethod;
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
		this.httpMethods = convertToConcurrentSet(httpMethods);
	}

	@Override
	public boolean isMatchedRequest(HttpMethod method, String requestUri) {
		return this.httpMethods.contains(method) && mappedRequestUri.equalsIgnoreCase(requestUri);
	}

	@Override
	public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
		actionHandler.actionHandle(httpRequest, httpResponse);
	}

	private Set<HttpMethod> convertToConcurrentSet(HttpMethod[] httpMethods) {

		Map<HttpMethod, Boolean> methods = Arrays.stream(httpMethods)
				.collect(Collectors.toMap(Functions.identity(), (v) -> Boolean.TRUE, (v1, v2) -> v1));

		return new ConcurrentHashMap<>(methods).keySet();
	}
}
