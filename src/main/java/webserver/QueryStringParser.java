package webserver;

import java.util.HashMap;

public class QueryStringParser {
    private HashMap<String, String> QueryParameters = new HashMap<String, String>();

    public QueryStringParser(String queryString) {
        String[] queryStringElements = queryString.split("&");
        HashMap<String, String> querySet = new HashMap<String, String>();
        for (String queryElement : queryStringElements) {
            String parameter = queryElement.split("=")[0];
            String value = queryElement.split("=")[1];
            querySet.put(parameter,value);
        }
        this.QueryParameters = querySet;
    }

    public HashMap<String, String> getQueryParameters() {
        return QueryParameters;
    }
}
