package webserver.http;

public interface RequestController {
    void service(HttpRequest httpRequest, HttpResponse response) throws Exception;

    void doGet(HttpRequest httpRequest, HttpResponse response) throws Exception;

    void doPost(HttpRequest httpRequest, HttpResponse response) throws Exception;
}
