package webserver.resolvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewResolverContainer implements ResolverContainer {

    public static final String ALREADY_EXIST_RESOLVER_MSG = "이미 존재하는 리졸버입니다. Resolver: ";
    private final List<Resolver> resolvers = new ArrayList<>();

    @Override
    public List<Resolver> resolvers() {
        return Collections.unmodifiableList(resolvers);
    }

    @Override
    public void addResolver(Resolver resolver) {
        if (resolvers.contains(resolver)) {
            throw new IllegalArgumentException(ALREADY_EXIST_RESOLVER_MSG + resolver);
        }

        resolvers.add(resolver);
    }
}
