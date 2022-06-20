package webserver.domain.http;

import java.util.Objects;

public class Protocol {
    private static final String PROTOCOL_DELIMITER = "/";

    private final String protocol;
    private final String version;

    private Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public static Protocol from(String input) {
        verify(input);

        String[] splitInput = input.split(PROTOCOL_DELIMITER);
        return new Protocol(splitInput[0], splitInput[1]);
    }

    private static void verify(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        String[] splitInput = input.split(PROTOCOL_DELIMITER);
        if (splitInput.length < 2 || splitInput[0].trim().isEmpty() || splitInput[1].trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol1 = (Protocol) o;
        return Objects.equals(protocol, protocol1.protocol) && Objects.equals(version, protocol1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "protocol='" + protocol + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
