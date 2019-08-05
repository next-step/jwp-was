package webserver.request;

import webserver.ContentType;
import webserver.Parameter;

import java.io.BufferedReader;
import java.io.IOException;

import static utils.IOUtils.readData;
import static webserver.ContentType.X_WWW_FORM_URLENCODED;
import static webserver.Parameter.parseParameter;

public class RequestBody {

    private Parameter parameter;

    public RequestBody(Parameter parameter) {
        this.parameter = parameter;
    }

    public static RequestBody parse(BufferedReader reader, RequestHeader requestHeader) throws IOException {
        ContentType contentType = ContentType.getByType(requestHeader.getContentType());
        Parameter parameter = null;
        if (contentType == X_WWW_FORM_URLENCODED) {
            parameter = parseParameter(readData(reader, requestHeader.getContentLength()));
        }

        return new RequestBody(parameter);
    }

    public Parameter getParameter() {
        return this.parameter;
    }



}
