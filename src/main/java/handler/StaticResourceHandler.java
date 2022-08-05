package handler;

import model.HttpHeader;
import model.request.HttpRequestMessage;
import model.response.HttpResponseMessage;
import model.response.ResponseLine;
import utils.FileIoUtils;

public class StaticResourceHandler implements PathHandler {
    private static final String CSS_PATH = "css";
    private static final String IMAGES_PATH = "images";
    private static final String FONTS_PATH = "fonts";
    private static final String JS_PATH = "js";

    @Override
    public Boolean canHandling(HttpRequestMessage httpRequestMessage) {
        String[] resources = httpRequestMessage.getPath().split(RESOURCE_SEPARATOR);

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
    public HttpResponseMessage Handle(HttpRequestMessage httpRequestMessage) {
        byte[] body = FileIoUtils.loadFileFromStaticFilePath(httpRequestMessage.getPath());

        return new HttpResponseMessage(ResponseLine.httpOk(), createOkStyleSheetHttpHeader(body), body);
    }

    private HttpHeader createOkStyleSheetHttpHeader(byte[] body) {
        return new HttpHeader.Builder()
            .addHeader("Content-Type: text/css;charset=utf-8")
            .addHeader("Content-Length: " + body.length)
            .build();
    }
}
