package webserver.http.request.handler;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.http.request.header.RequestHeader;

public interface RequestHandler {

    byte[] execute(RequestHeader requestHeader) throws IOException, URISyntaxException;
}
