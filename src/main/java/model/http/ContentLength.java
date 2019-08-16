package model.http;

import java.util.Objects;

public class ContentLength {
    public static final String HEADER_PREFIX = "Content-Length: ";

    private int length;

    private ContentLength(int length) {
        this.length = length;
    }

    public static ContentLength of(int length) {
        return new ContentLength(length);
    }

    public static ContentLength of(String headerLine) {
        return new ContentLength(Integer.valueOf(headerLine.replace(HEADER_PREFIX, "")));
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentLength that = (ContentLength) o;
        return length == that.length;
    }

    @Override
    public int hashCode() {
        return Objects.hash(length);
    }

    @Override
    public String toString() {
        return "ContentLength{" +
                "length=" + length +
                '}';
    }
}
