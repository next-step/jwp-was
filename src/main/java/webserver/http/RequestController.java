package webserver.http;

import webserver.http.HttpResponse;
import webserver.http.Request;

public interface RequestController {
    HttpResponse service(Request httpRequest) throws Exception;

    HttpResponse doGet(Request httpRequest) throws Exception;

    HttpResponse doPost(Request httpRequest) throws Exception;
}
