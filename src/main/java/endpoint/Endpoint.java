package endpoint;

import webserver.http.request.requestline.HttpMethod;

public class Endpoint {
    private HttpMethod httpMethod;
    private String endpointPath;

    public Endpoint(HttpMethod httpMethod, String endpointPath) {
        this.httpMethod = httpMethod;
        this.endpointPath = endpointPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Endpoint endpoint = (Endpoint) o;

        if (httpMethod != endpoint.httpMethod) return false;
        return endpointPath != null ? endpointPath.equals(endpoint.endpointPath) : endpoint.endpointPath == null;
    }

    @Override
    public int hashCode() {
        int result = httpMethod != null ? httpMethod.hashCode() : 0;
        result = 31 * result + (endpointPath != null ? endpointPath.hashCode() : 0);
        return result;
    }
}
