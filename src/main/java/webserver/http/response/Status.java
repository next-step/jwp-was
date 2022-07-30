package webserver.http.response;

import webserver.http.protocol.Protocol;

public class Status {
    private final Protocol protocol;
    private final StatusCode statusCode;

    public Status(Protocol protocol, StatusCode statusCode) {
        this.protocol = protocol;
        this.statusCode = statusCode;
    }

    public static Status ok() {
        return new Status(Protocol.http1Point1(), StatusCode.OK);
    }

    public static Status found() {
        return new Status(Protocol.http1Point1(), StatusCode.FOUND);
    }

    public static Status badRequest() {
        return new Status(Protocol.http1Point1(), StatusCode.BAD_REQUEST);
    }

    public static Status notFound() {
        return new Status(Protocol.http1Point1(), StatusCode.NOT_FOUND);
    }

    public static Status internalError() {
        return new Status(Protocol.http1Point1(), StatusCode.INTERNAL_ERROR);
    }
}
