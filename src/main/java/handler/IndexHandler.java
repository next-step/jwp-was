package handler;

import model.HttpHeader;
import model.request.HttpRequestMessage;
import model.response.HttpResponseMessage;
import model.response.ResponseLine;
import utils.FileIoUtils;

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
            HttpHeader httpOkHeader =
                new HttpHeader.Builder()
                    .addHeader("Content-Type: text/html;charset=utf-8")
                    .addHeader("Content-Length: " + body.length)
                    .build();

            return new HttpResponseMessage(ResponseLine.httpOk(), httpOkHeader, body);
        }

        return new HttpResponseMessage(ResponseLine.httpBadRequest(), null, new byte[0]);
    }
}
