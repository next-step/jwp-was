package webserver;

import java.util.Objects;

public class Protocol {
    private static final int VALID_NUMBER_OF_PROPERTIES = 2;
    private static final String PROPERTIES_DELIMITER = "/";
    private String name;
    private String version;

    private Protocol(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public static Protocol from(String protocol) {
        String properties[] = protocol.split(PROPERTIES_DELIMITER);
        validate(properties);
        return new Protocol(properties[0], properties[1]);
    }

    private static void validate(String[] properties) {
        if (properties.length != VALID_NUMBER_OF_PROPERTIES) {
            throw new IllegalArgumentException(String.format("필요한 속성의 개수[%d]를 만족하지 않습니다.", VALID_NUMBER_OF_PROPERTIES));
        }
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol = (Protocol) o;
        return Objects.equals(name, protocol.name) && Objects.equals(version, protocol.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
