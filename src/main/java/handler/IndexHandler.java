package handler;

import model.HttpHeader;
import model.request.HttpRequestHeader;
import model.response.HttpResponseHeader;
import model.response.ResponseLine;
import utils.FileIoUtils;
import utils.parser.HttpHeaderParser;

import java.util.Arrays;

public class IndexHandler implements PathHandler {
    @Override
    public Boolean canHandling(HttpRequestHeader httpRequestHeader) {
        String[] resources = httpRequestHeader.getPath().split(RESOURCE_SEPARATOR);

        if (resources.length == 0) {
            return false;
        }

        return hasResourceIdentifier(resources[ROOT_RESOURCE_INDEX]);
    }

    @Override
    public HttpResponseHeader Handle(HttpRequestHeader httpRequestHeader) {
        if (hasResourceIdentifier(httpRequestHeader.getPath())) {
            byte[] body = FileIoUtils.loadFileFromClasspath(httpRequestHeader.getPath());
            HttpHeader httpOkHeader = HttpHeaderParser.parseHeader(
                Arrays.asList(
                    "Content-Type: text/html;charset=utf-8",
                    "Content-Length: " + body.length
                ));

            return new HttpResponseHeader(ResponseLine.httpOk(), httpOkHeader, body);
        }

        return new HttpResponseHeader(ResponseLine.httpBadRequest(), null, new byte[0]);
    }
}
