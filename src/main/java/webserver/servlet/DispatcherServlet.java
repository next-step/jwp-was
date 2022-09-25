package webserver.servlet;

import webserver.http.HttpHeader;
import webserver.http.HttpHeaders;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseBody;
import webserver.http.response.ResponseLine;

public enum DispatcherServlet {
    INSTANCE;

    public HttpResponse doService(HttpRequest httpRequest) {
        RequestMappingHandler requestMappingHandler = new RequestMappingHandler(ServletConfig.servlets());

        if (requestMappingHandler.match(httpRequest)) {
            return requestMappingHandler.doService(httpRequest);
        }
        return doStaticService(httpRequest);
    }

    private HttpResponse doStaticService(HttpRequest httpRequest) {
        ResponseLine responseLine = ResponseLine.ok();

        String filePath = httpRequest.getRequestLine().getPathValue();
        ResponseBody responseBody = ResponseBody.from(filePath);

        HttpHeaders httpHeaders = HttpHeaders.init();
        httpHeaders.addResponseHeader(HttpHeader.CONTENT_TYPE, "text/html;charset=utf-8");
        httpHeaders.addResponseHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(responseBody.getContentsLength()));

        return new HttpResponse(responseLine, httpHeaders, responseBody);
    }
}
