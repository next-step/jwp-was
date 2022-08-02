package webserver.http.response;

import endpoint.TemplateResource;
import webserver.http.header.HttpStaticResourceFileExtension;

public class HttpResponseBody {
    private final byte[] bodyBytes;

    private HttpStaticResourceFileExtension httpStaticResourceFileExtension = HttpStaticResourceFileExtension.NOTHING;

    public HttpResponseBody(byte[] bodyBytes) {
        this.bodyBytes = bodyBytes;
    }

    public HttpResponseBody(TemplateResource templateResource) {
        this.httpStaticResourceFileExtension = HttpStaticResourceFileExtension.select(templateResource.fileExtensionName());
        this.bodyBytes = templateResource.getFileBytes();
    }

    public HttpStaticResourceFileExtension getHttpStaticResourceFileExtension() {
        return httpStaticResourceFileExtension;
    }

    public byte[] getBodyBytes() {
        return bodyBytes;
    }

    public int getBodyBytesLength() {
        if (bodyBytes == null) {
            return 0;
        }

        return bodyBytes.length;
    }
}
