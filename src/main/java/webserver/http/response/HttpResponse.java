package webserver.http.response;

import java.io.IOException;

import webserver.http.request.header.RequestHeader;
import webserver.http.service.CreateHeader;

public class HttpResponse {
    public String run(RequestHeader requestHeader, int lengthOfBodyContent) throws IOException {
        CreateHeader createHeader = new CreateHeader();
        return createHeader.create(requestHeader, lengthOfBodyContent);
    }
}