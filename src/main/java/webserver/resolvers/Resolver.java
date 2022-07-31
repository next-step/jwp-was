package webserver.resolvers;

public interface Resolver {
    boolean support(Object obj);

    String resolve(Object obj);
}
