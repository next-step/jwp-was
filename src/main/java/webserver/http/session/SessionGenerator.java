package webserver.http.session;

@FunctionalInterface
public interface SessionGenerator {

    Session generate();
}
