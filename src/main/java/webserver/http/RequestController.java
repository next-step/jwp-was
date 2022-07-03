package webserver.http;

public interface RequestController {
    HttpResponse service(HttpRequest httpRequest) throws Exception;

    HttpResponse doGet(HttpRequest httpRequest) throws Exception;

    HttpResponse doPost(HttpRequest httpRequest) throws Exception;
}
