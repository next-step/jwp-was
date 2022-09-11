package webserver.http.session;

@FunctionalInterface
public interface SessionIdGenerator {

    String run();
}
