package webserver.http.response;

import endpoint.TemplatePage;

public class HttpResponseBody {
    private byte[] bodyBytes;


    private boolean isHtmlPageBody = false;

    public HttpResponseBody(byte[] bodyBytes) {
        this.bodyBytes = bodyBytes;
    }

    public HttpResponseBody(TemplatePage templatePage) {
        this.isHtmlPageBody = true;
        this.bodyBytes = templatePage.getPageFileBytes();
    }

    public boolean isHtmlPageBody() {
        return isHtmlPageBody;
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
