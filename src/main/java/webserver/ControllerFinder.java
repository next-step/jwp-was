package webserver;

import annotation.Controller;
import annotation.RequestMapping;
import model.http.RequestLine;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class ControllerFinder {
    private static final String BASE_CLASS_PATH = "";
    private static final String CLASS_FILE_EXTENSION = ".class";

    public static Optional<Method> findController(RequestLine requestLine) {
        try {
            return findAllClassInClassPath().stream()
                    .filter(clazz -> clazz.isAnnotationPresent(Controller.class))
                    .flatMap(clazz -> Arrays.stream(clazz.getMethods()))
                    .filter(method -> isRequestMappingAnnotationPresentWithUriPath(requestLine, method))
                    .findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static List<Class> findAllClassInClassPath() throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Enumeration<URL> resources = classLoader.getResources(BASE_CLASS_PATH);
        List<File> dirs = getRootFiles(resources);
        List<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, BASE_CLASS_PATH));
        }
        return classes;
    }

    private static List<File> getRootFiles(Enumeration<URL> resources) {
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        return dirs;
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        File[] filesInDir;
        if (!directory.exists() || (filesInDir = directory.listFiles()) == null) {
            return Collections.emptyList();
        }

        List<Class> classes = new ArrayList<>();
        String packagePrefix = BASE_CLASS_PATH.equals(packageName)? BASE_CLASS_PATH : packageName + ".";

        for (File file : filesInDir) {
            String fileName = file.getName();
            if (file.isDirectory()) {
                classes.addAll(findClasses(file,  packagePrefix + fileName));
            } else if (isClassFile(file)) {
                classes.add(Class.forName(packagePrefix + getFileNameWithoutExtension(fileName)));
            }
        }
        return classes;
    }

    private static String getFileNameWithoutExtension(String fileName) {
        return fileName.substring(0, fileName.length() - CLASS_FILE_EXTENSION.length());
    }

    private static boolean isClassFile(File file) {
        return file.getName().endsWith(CLASS_FILE_EXTENSION);
    }

    private static boolean isRequestMappingAnnotationPresentWithUriPath(RequestLine requestLine, Method method) {
        return method.isAnnotationPresent(RequestMapping.class)
                && requestLine.isSamePath(method.getDeclaredAnnotation(RequestMapping.class).path())
                && requestLine.isSameMethod(method.getDeclaredAnnotation(RequestMapping.class).method());
    }
}
