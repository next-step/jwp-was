package utils;

import exceptions.ResourceLoadException;
import exceptions.ResourceNotFoundException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileIoUtils {

    public static Optional<URL> getResourceUrl(String filePath) {
        return Optional.ofNullable(FileIoUtils.class.getClassLoader().getResource(filePath));
    }

    public static byte[] loadFileFromClasspath(String filePath) {
        Optional<URL> url = getResourceUrl(filePath);

        if(!url.isPresent()){
            throw new ResourceNotFoundException("Can not find Resource : " + filePath);
        }

        return loadFileFromURL(url.get());
    }

    public static byte[] loadFileFromURL(URL url) {
        try {
            URI uri = url.toURI();
            Path path = Paths.get(uri);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new ResourceLoadException(e);
        } catch (URISyntaxException e) {
            throw new ResourceLoadException(e);
        }

    }
}
