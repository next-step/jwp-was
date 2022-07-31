package webserver.resolvers;

import java.util.Objects;

public class EmptyViewResolver implements Resolver{

    private static final EmptyViewResolver instance = new EmptyViewResolver();
    public static final String EMPTY = "";

    private EmptyViewResolver() {
    }

    public static EmptyViewResolver getInstance() {
        return instance;
    }

    @Override
    public boolean support(Object obj) {
        return true;
    }

    @Override
    public String resolve(Object obj) {
        if (Objects.isNull(obj)) {
            return EMPTY;
        }
        return obj.toString();
    }
}
