package webserver.resource;

import exception.HttpException;
import webserver.StatusCode;

import java.io.File;
import java.net.URL;

public abstract class AbstractResourceLoader implements ResourceLoader {

    protected ClassLoader classLoader;

    public AbstractResourceLoader() {
        this.classLoader = Thread.currentThread().getContextClassLoader();
    }

    public AbstractResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }


    protected void validate(String name) {
        URL resource = classLoader.getResource(name);

        if (resource == null) {
            throw new HttpException(StatusCode.NOT_FOUND);
        }

        File file = new File(resource.getFile());

        if (!file.isFile()) {
            throw new HttpException(StatusCode.NOT_FOUND);
        }
    }

}
