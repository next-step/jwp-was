package webserver.handlers;

import java.io.IOException;

public interface Handler<T, R> {
    R handle(T obj) throws IOException;
}
