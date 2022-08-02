package handler;

import model.HttpHeader;
import model.request.HttpRequestHeader;
import model.response.HttpResponseHeader;
import model.response.ResponseLine;
import utils.FileIoUtils;
import utils.parser.HttpHeaderParser;

import java.util.Arrays;

public class StaticResourceHandler implements PathHandler {
    private static final String CSS_PATH = "css";
    private static final String IMAGES_PATH = "images";
    private static final String FONTS_PATH = "fonts";
    private static final String JS_PATH = "js";

    @Override
    public Boolean canHandling(HttpRequestHeader httpRequestHeader) {
        String[] resources = httpRequestHeader.getPath().split(RESOURCE_SEPARATOR);

        if (resources.length == 0) {
            return false;
        }

        return hasStaticResourcePath(resources[ROOT_RESOURCE_INDEX]);
    }

    private boolean hasStaticResourcePath(String rootResource) {
        if (rootResource.equals(CSS_PATH) ||
            rootResource.equals(IMAGES_PATH) ||
            rootResource.equals(FONTS_PATH) ||
            rootResource.equals(JS_PATH)
        ) {
            return true;
        }

        return false;
    }

    @Override
    public HttpResponseHeader Handle(HttpRequestHeader httpRequestHeader) {
        byte[] body = FileIoUtils.loadFileFromStaticFilePath(httpRequestHeader.getPath());

        return new HttpResponseHeader(ResponseLine.httpOk(), createOkStyleSheetHttpHeader(body), body);
    }

    private HttpHeader createOkStyleSheetHttpHeader(byte[] body) {
        return HttpHeaderParser.parseHeader(
            Arrays.asList(
                "Content-Type: text/css;charset=utf-8",
                "Content-Length: " + body.length
            ));
    }
}
