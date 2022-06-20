package webserver.http.response;

public interface Response {

    int getLength();

    byte[] getBody();
}
