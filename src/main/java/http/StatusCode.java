package http;

import http.responsetemplate.NotFound;
import http.responsetemplate.Ok;
import http.responsetemplate.Redirect;
import http.responsetemplate.ResponseTemplate;

import java.io.DataOutputStream;

public enum StatusCode {
    OK(200, "OK", new Ok()),
    REDIRECT(302, "Found", new Redirect()),
    NOT_FOUND(404, "Not Found", new NotFound());

    private final int statusCode;
    private final String statusText;
    private final ResponseTemplate templateClass;

    StatusCode(final int statusCode, final String statusText, final ResponseTemplate templateClass) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.templateClass = templateClass;
    }

    public void writeResponse(final HttpResponse httpResponse, final DataOutputStream dataOutputStream) {
        templateClass.write(httpResponse, dataOutputStream);
    }

    public int getCodeValue() {
        return statusCode;
    }

    public String getResponseLine() {
        return "HTTP/1.1 " + statusCode + " " + statusText;
    }
}
