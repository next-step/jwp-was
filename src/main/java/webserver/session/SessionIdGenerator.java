package webserver.session;

@FunctionalInterface
public interface SessionIdGenerator {

    String generate();

}
