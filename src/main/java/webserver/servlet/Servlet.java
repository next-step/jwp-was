package webserver.servlet;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Servlet {
	void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
