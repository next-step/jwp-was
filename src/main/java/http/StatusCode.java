package http;

import http.responsetemplate.NotFound;
import http.responsetemplate.Ok;
import http.responsetemplate.Redirect;
import http.responsetemplate.ResponseTemplate;

import java.io.DataOutputStream;

public enum StatusCode {
    OK(200, new Ok()),
    REDIRECT(302, new Redirect()),
    NOT_FOUND(404, new NotFound());

    private final int statusCode;
    private final ResponseTemplate templateClass;

    StatusCode(final int statusCode, final ResponseTemplate templateClass) {
        this.statusCode = statusCode;
        this.templateClass = templateClass;
    }

    public void writeResponse(final HttpResponse httpResponse, final DataOutputStream dataOutputStream) {
        templateClass.write(httpResponse, dataOutputStream);
    }

    public int getCodeValue() {
        return statusCode;
    }
}
