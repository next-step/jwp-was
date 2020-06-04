package utils;

import java.util.StringTokenizer;

public class Token {
    private final StringTokenizer stringTokenizer;

    public Token(final String origin, final String delimiter) {
        validate(origin, delimiter);

        stringTokenizer = new StringTokenizer(origin, delimiter);
    }

    public static Token init(final String origin, final String delimiter) {
        return new Token(origin, delimiter);
    }

    private void validate(final String origin, final String delimiter) {
        if (StringUtil.isEmpty(origin) || StringUtil.isEmpty(delimiter)) {
            throw new IllegalArgumentException("String or delimiter is null or empty");
        }
    }

    public String nextToken() {
        if(stringTokenizer.countTokens() < 1) {
            return null;
        }

        return stringTokenizer.nextToken();
    }

    public void validate(final int expectedTokenSize) {
        if (stringTokenizer.countTokens() < expectedTokenSize) {
            throw new IllegalArgumentException("Tokenized string is not enough");
        }
    }
}
