package utils;

public class FileNameUtils {

    private FileNameUtils() {}

    public static String getExtension(String path) {
        String[] split = path.split("/");

        String resourceName = split[split.length - 1];

        String[] splited = resourceName.split("\\.");

        if (splited.length < 2) {
            return "";
        }

        return splited[splited.length - 1];
    }

}
