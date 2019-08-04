package header;

import response.HeaderResponse;

/**
 * Created by youngjae.havi on 2019-08-04
 */
public class Location implements HeaderResponse {
    private String location;

    public Location(String location) {
        this.location = location;
    }

    @Override
    public String key() {
        return "Location";
    }

    @Override
    public String value() {
        return location;
    }
}
