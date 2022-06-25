package webserver.http.response;

public interface Response {

    int getLength();

    int getStatus();

    String getStatusMessage();

    String getLocation();

    byte[] getBody();
}
