package webserver.servlet;

import utils.FileIoUtils;
import webserver.http.HttpHeader;
import webserver.http.HttpHeaders;
import webserver.http.MediaType;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseBody;
import webserver.http.response.ResponseLine;

public class ResourceServlet extends HttpServlet {

    @Override
    protected HttpResponse doGet(HttpRequest request) {
        String filePath = request.getRequestLine().getPathValue();
        String staticResourceExtension = request.getRequestLine().getStaticResourceExtension();

        ResponseLine responseLine = ResponseLine.ok();
        return MediaType.from(staticResourceExtension)
                .map(mediaType -> createStaticResourceResponse(filePath, responseLine, mediaType))
                .orElseGet(() -> createViewResourceResponse(filePath, responseLine));
    }

    private HttpResponse createStaticResourceResponse(String filePath, ResponseLine responseLine, MediaType mediaType) {
        ResponseBody responseBody = ResponseBody.from(FileIoUtils.STATIC_RESOURCES_PATH, filePath);

        HttpHeaders httpHeaders = HttpHeaders.init();
        httpHeaders.addResponseHeader(HttpHeader.CONTENT_TYPE, mediaType.getChemical());
        httpHeaders.addResponseHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(responseBody.getContentsLength()));

        return new HttpResponse(responseLine, httpHeaders, responseBody);
    }

    private HttpResponse createViewResourceResponse(String filePath, ResponseLine responseLine) {
        ResponseBody responseBody = ResponseBody.from(FileIoUtils.VIEW_RESOURCES_PATH, filePath);

        HttpHeaders httpHeaders = HttpHeaders.init();
        httpHeaders.addResponseHeader(HttpHeader.CONTENT_TYPE, "text/html;charset=utf-8");
        httpHeaders.addResponseHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(responseBody.getContentsLength()));

        return new HttpResponse(responseLine, httpHeaders, responseBody);
    }
}
