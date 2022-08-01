package webserver.resolvers;

import webserver.exception.NoSuchResolverException;

import java.util.List;

public interface ResolverContainer {
    String INVALID_OBJECT_TYPE_MSG = "유효한 리졸버가 아닙니다. :";

    void addResolver(Resolver resolver);

    List<Resolver> resolvers();

    default Resolver find(Object obj) {
        return resolvers().stream()
                .filter(resolver -> resolver.support(obj))
                .findFirst()
                .orElseGet(EmptyViewResolver::getInstance);
    }

    default ResolverContainer add(Object obj) {
        if (!(obj instanceof Resolver)) {
            throw new IllegalArgumentException(INVALID_OBJECT_TYPE_MSG + obj.getClass().getName());
        }
        addResolver((Resolver) obj);

        return this;
    }
}
