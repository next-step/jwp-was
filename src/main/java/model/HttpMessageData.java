package model;

import java.util.List;

public class HttpMessageData {

    private List<String> httpMessageData;

    public HttpMessageData(List<String> httpMessageData) {
        this.httpMessageData = httpMessageData;
    }

    public String toStringHttpMessageData() {
        StringBuilder value = new StringBuilder();
        value.append("\n");
        value.append("[" + "\n");
        httpMessageData.forEach(data -> value.append(data).append("\n"));
        value.append("]");

        return value.toString();
    }

    public List<String> getHttpMessageData() {
        return httpMessageData;
    }

}
