package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Tokens {
    private final StringTokenizer stringTokenizer;

    public Tokens(final String origin, final String delimiter) {
        validate(origin, delimiter);

        stringTokenizer = new StringTokenizer(origin, delimiter);
    }

    public static Tokens init(final String origin, final String delimiter) {
        return new Tokens(origin, delimiter);
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

    public List<String> getAllTokens() {
        List<String> tokens = new ArrayList<>();

        while (stringTokenizer.hasMoreTokens()) {
            tokens.add(stringTokenizer.nextToken());
        }

        return tokens;
    }
}
