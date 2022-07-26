package webserver.http.response;

import endpoint.TemplatePage;
import utils.TemplatePageLoader;

import static utils.TemplatePageLoader.EMPTY_TEMPLATE_BYTES;

public class HttpResponseMessage {
    public static final String RESPONSE_END_OF_LINE_MARKER = "\r\n";

    private HttpResponseStatusLine httpResponseStatusLine;
    private HttpResponseBody httpResponseBody;
    private HttpResponseHeaders httpResponseHeaders;


    public HttpResponseMessage(HttpResponseStatusLine httpResponseStatusLine, HttpResponseBody httpResponseBody, HttpResponseHeaders httpResponseHeaders) {
        if (httpResponseBody.isHtmlPageBody()) {
            httpResponseHeaders.addHtmlContentTypeHeader();
        }
        httpResponseHeaders.addContentLengthHeader(httpResponseBody.getBodyBytesLength());

        this.httpResponseStatusLine = httpResponseStatusLine;
        this.httpResponseBody = httpResponseBody;
        this.httpResponseHeaders = httpResponseHeaders;
    }

    public static HttpResponseMessage justOk() {
        return new HttpResponseMessage(
                HttpResponseStatusLine.fromOnePointOne(HttpStatus.OK),
                new HttpResponseBody(EMPTY_TEMPLATE_BYTES),
                new HttpResponseHeaders()
        );
    }

    public static HttpResponseMessage page(String pagePath) {
        return new HttpResponseMessage(
                HttpResponseStatusLine.fromOnePointOne(HttpStatus.OK),
                new HttpResponseBody(TemplatePageLoader.getTemplatePage(pagePath)),
                new HttpResponseHeaders()
        );
    }

    public static HttpResponseMessage redirect(String redirectPath) {
        return new HttpResponseMessage(
                HttpResponseStatusLine.fromOnePointOne(HttpStatus.FOUND),
                new HttpResponseBody(TemplatePageLoader.getTemplatePage(redirectPath)),
                HttpResponseHeaders.ofLocation(redirectPath)
        );
    }

    public static HttpResponseMessage notFound() {
        return new HttpResponseMessage(
                HttpResponseStatusLine.fromOnePointOne(HttpStatus.NOT_FOUND),
                new HttpResponseBody(TemplatePage.NOT_FOUND_PAGE),
                new HttpResponseHeaders()
        );
    }

    public HttpResponseStatusLine getHttpResponseStatusLine() {
        return httpResponseStatusLine;
    }

    public HttpResponseBody getHttpResponseBody() {
        return httpResponseBody;
    }

    public HttpResponseHeaders getHttpResponseHeaders() {
        return httpResponseHeaders;
    }
}
