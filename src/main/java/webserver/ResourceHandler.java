package webserver;

import exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.RequestHeader;

import java.io.*;
import java.net.URL;

public class ResourceHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private ResourceNameResolver resourceNameResolver = new ResourceNameResolver();
    private ClassLoader classLoader;

    public ResourceHandler() {
        this.classLoader = Thread.currentThread().getContextClassLoader();
    }

    public ResourceHandler(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public String getContents(String path) {
        String name = resourceNameResolver.resolveName(path);
        URL resource = classLoader.getResource(name);

        if (resource == null) {
            throw new HttpException(StatusCode.NOT_FOUND);
        }

        File file = new File(resource.getFile());

        if (!file.isFile()) {
            throw new HttpException(StatusCode.NOT_FOUND);
        }

        try (InputStream in = classLoader.getResourceAsStream(name)) {
            return getContentsInternal(in);
        } catch (IOException e) {
            throw new HttpException(StatusCode.NOT_FOUND);
        }
    }

    private String getContentsInternal(InputStream in) throws IOException {
        if (in == null) {
            throw new HttpException(StatusCode.NOT_FOUND);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder content = new StringBuilder();
        String read;
        while ((read = reader.readLine()) != null) {
            content.append(read);
        }
        return content.toString();
    }
}
