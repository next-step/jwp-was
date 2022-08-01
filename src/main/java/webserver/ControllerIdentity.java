package webserver;

import java.util.Objects;

public class ControllerIdentity {

    private final String path;

    public ControllerIdentity(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ControllerIdentity that = (ControllerIdentity)o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
