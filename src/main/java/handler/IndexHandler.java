package handler;

import model.HttpHeader;
import model.request.HttpRequestMessage;
import model.response.HttpResponseMessage;
import model.response.ResponseLine;
import utils.FileIoUtils;

import java.util.Arrays;

public class IndexHandler implements PathHandler {
    @Override
    public Boolean canHandling(HttpRequestMessage httpRequestMessage) {
        String[] resources = httpRequestMessage.getPath().split(RESOURCE_SEPARATOR);

        if (resources.length == 0) {
            return false;
        }

        return hasResourceIdentifier(resources[ROOT_RESOURCE_INDEX]);
    }

    @Override
    public HttpResponseMessage Handle(HttpRequestMessage httpRequestMessage) {
        if (hasResourceIdentifier(httpRequestMessage.getPath())) {
            byte[] body = FileIoUtils.loadFileFromClasspath(httpRequestMessage.getPath());
            HttpHeader httpOkHeader = new HttpHeader(
                Arrays.asList(
                    "Content-Type: text/html;charset=utf-8",
                    "Content-Length: " + body.length
                ));

            return new HttpResponseMessage(ResponseLine.httpOk(), httpOkHeader, body);
        }

        return new HttpResponseMessage(ResponseLine.httpBadRequest(), null, new byte[0]);
    }
}
