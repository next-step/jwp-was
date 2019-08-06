package webserver;

@FunctionalInterface
public interface HttpServlet {

    void service(Request request, Response response) throws Exception;
}
