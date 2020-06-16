package utils;

import webserver.exceptions.FailReadFileException;
import webserver.exceptions.UncheckedURISyntaxException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (URISyntaxException e) {
            throw new UncheckedURISyntaxException(filePath);
        } catch (IOException e) {
            throw new FailReadFileException();
        }
    }
}
