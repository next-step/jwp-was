package utils;

public interface Parser<T> {
    T parse(String input);
}