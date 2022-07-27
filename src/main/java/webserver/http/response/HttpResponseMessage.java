package webserver.http.response;

import endpoint.HttpStaticResourceFileExtension;
import endpoint.TemplateResource;
import utils.StaticResourceLoader;
import utils.TemplatePageLoader;

import java.util.Map;

import static utils.TemplatePageLoader.EMPTY_TEMPLATE_BYTES;
import static webserver.http.header.HttpHeaderConstants.*;

public class HttpResponseMessage {
    public static final String RESPONSE_END_OF_LINE_MARKER = "\r\n";

    private HttpResponseStatusLine httpResponseStatusLine;
    private HttpResponseBody httpResponseBody;
    private HttpResponseHeaders httpResponseHeaders;


    public HttpResponseMessage(HttpResponseStatusLine httpResponseStatusLine, HttpResponseBody httpResponseBody, HttpResponseHeaders httpResponseHeaders) {
        addContentHeader(httpResponseBody, httpResponseHeaders);

        this.httpResponseStatusLine = httpResponseStatusLine;
        this.httpResponseBody = httpResponseBody;
        this.httpResponseHeaders = httpResponseHeaders;
    }

    private static void addContentHeader(HttpResponseBody httpResponseBody, HttpResponseHeaders httpResponseHeaders) {

        HttpStaticResourceFileExtension httpStaticResourceFileExtension = httpResponseBody.getHttpStaticResourceFileExtension();

        switch (httpStaticResourceFileExtension) {
            case HTML:
                httpResponseHeaders.addHeader(CONTENT_TYPE, TEXT_HTML_CHARSET_UTF_8);
                break;
            case CSS:
                httpResponseHeaders.addHeader(CONTENT_TYPE, TEXT_CSS_CHARSET_UTF_8);
                break;
            default:
                break;
        }
        httpResponseHeaders.addContentLengthHeader(httpResponseBody.getBodyBytesLength());
    }

    public static HttpResponseMessage staticResource(String staticResourceFilename) {
        TemplateResource templateResource = StaticResourceLoader.getStaticResource(staticResourceFilename);

        if (templateResource.isEmpty()) {
            return notFound();
        }

        return new HttpResponseMessage(
                HttpResponseStatusLine.fromOnePointOne(HttpStatus.OK),
                new HttpResponseBody(StaticResourceLoader.getStaticResource(staticResourceFilename)),
                new HttpResponseHeaders()
        );
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

    public static HttpResponseMessage dynamicPage(String pagePath, Map<String, ?> viewModelMap) {
        return new HttpResponseMessage(
                HttpResponseStatusLine.fromOnePointOne(HttpStatus.OK),
                new HttpResponseBody(TemplatePageLoader.getDynamicTemplatePage(pagePath, viewModelMap)),
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
                new HttpResponseBody(TemplateResource.EMPTY),
                new HttpResponseHeaders()
        );
    }

    public static HttpResponseMessage notFoundPage() {
        return new HttpResponseMessage(
                HttpResponseStatusLine.fromOnePointOne(HttpStatus.NOT_FOUND),
                new HttpResponseBody(TemplateResource.NOT_FOUND_PAGE),
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
