package webserver.domain;

public interface EntitySupplier<T> {
    T supply();
}
