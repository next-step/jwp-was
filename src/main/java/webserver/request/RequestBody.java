package webserver.request;

import webserver.ContentType;
import webserver.HttpParameter;

import java.io.BufferedReader;
import java.io.IOException;

import static utils.IOUtils.readData;
import static webserver.ContentType.X_WWW_FORM_URLENCODED;
import static webserver.HttpParameter.parseParameter;

public class RequestBody {

    private HttpParameter httpParameter;

    public RequestBody(HttpParameter httpParameter) {
        this.httpParameter = httpParameter;
    }

    public static RequestBody parse(BufferedReader reader, RequestHeader requestHeader) throws IOException {
        ContentType contentType = ContentType.getByType(requestHeader.getContentType());
        HttpParameter httpParameter = null;
        if (contentType == X_WWW_FORM_URLENCODED) {
            httpParameter = parseParameter(readData(reader, requestHeader.getContentLength()));
        }

        return new RequestBody(httpParameter);
    }

    public HttpParameter getHttpParameter() {
        return this.httpParameter;
    }



}
