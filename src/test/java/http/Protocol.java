package http;

public class Protocol {
    private final String protocol;
    private final String version;
    public Protocol(String value) {
        String[] protocolAndVersion = value.split("/");
        if(protocolAndVersion.length != 2){
            throw new IllegalArgumentException();
        }
        protocol = protocolAndVersion[0];
        version = protocolAndVersion[1];
    }

    public Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public String getProtocol(){
        return protocol;
    }

    public String getVersion() {
        return version;
    }

}
