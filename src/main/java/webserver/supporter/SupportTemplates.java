package webserver.supporter;

import webserver.domain.ContentType;
import webserver.domain.HttpHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public final class SupportTemplates {

    public static final String PATH_TEMPLATES = "./templates";

    private SupportTemplates() {
    }

    public static void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.addHeader(HttpHeader.CONTENT_TYPE, ContentType.HTML.type());
        httpResponse.forward(PATH_TEMPLATES + httpRequest.getPath());
    }

}
