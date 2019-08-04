package webserver;

import utils.FileUtils;
import webserver.request.HttpRequest;

import java.util.Set;

/**
 * Created by hspark on 2019-08-05.
 */
public class FileResolverRegistration {
    private Set<String> suffixSet;
    private String resourcePath;

    public FileResolverRegistration(Set<String> suffixSet, String resourcePath) {
        this.suffixSet = suffixSet;
        this.resourcePath = resourcePath;
    }

    public String resolve(HttpRequest httpRequest) {
        if (!isTarget(httpRequest.getPath())) {
            throw new IllegalArgumentException("Not Supported Suffix, " + httpRequest.getPath());
        }
        return resourcePath + httpRequest.getPath();
    }

    public boolean isTarget(String path) {
        return suffixSet.contains(FileUtils.getExtension(path));
    }
}
