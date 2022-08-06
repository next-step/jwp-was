package webserver;

public class Protocol {

    private static final int NUMBER_OF_PROTOCOL_PROPERTIES = 2;
    private static final int PROTOCOL_NAME_IDX = 0;
    private static final int PROTOCOL_VERSION_IDX = 1;

    private static final String PROTOCOL_SLASH = "/";

    private final String name;
    private final Double version;

    public Protocol(String name, Double version) {
        this.name = name;
        this.version = version;
    }

    public static Protocol parse(String protocol) {
        String[] splits = protocol.split(PROTOCOL_SLASH);

        if (splits.length != NUMBER_OF_PROTOCOL_PROPERTIES) {
            throw new IllegalArgumentException("올바르지 않은 프로토콜 입니다");
        }

        return new Protocol(
                splits[PROTOCOL_NAME_IDX],
                Double.parseDouble(splits[PROTOCOL_VERSION_IDX])
        );
    }

    public String getName() {
        return name;
    }

    public Double getVersion() {
        return version;
    }
}
