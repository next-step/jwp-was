package utils;

import java.util.StringTokenizer;

public class Token {
    private final StringTokenizer stringTokenizer;

    public Token(String string, String splitter) {
        stringTokenizer = new StringTokenizer(string, splitter);
    }

    public String nextToken() {
        return stringTokenizer.nextToken();
    }
}
