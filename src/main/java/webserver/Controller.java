package webserver;

@FunctionalInterface
public interface Controller {

    void service(Request request, Response response) throws Exception;
}
