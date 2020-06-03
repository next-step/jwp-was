package http;

import java.util.Objects;

public class Protocol {

    private String protocol;
    private String version;

    private Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    static Protocol of(String value){
        String[] values = value.split("/");
        return of(values[0], values[1]);
    }

    static Protocol of(String protocol, String version){
        return new Protocol(protocol, version);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Protocol protocol1 = (Protocol) o;
        return Objects.equals(protocol, protocol1.protocol) &&
            Objects.equals(version, protocol1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }
}
