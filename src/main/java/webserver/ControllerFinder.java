package webserver;

import annotation.Controller;
import annotation.RequestMapping;
import model.http.UriPath;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class ControllerFinder {
    private static final String BASE_CLASS_PATH = "";

    public static Optional<Method> findController(UriPath path) {
        try {
            return findAllClassInClassPath().stream()
                    .filter(cls -> cls.isAnnotationPresent(Controller.class)
                                && Arrays.stream(cls.getMethods())
                                .anyMatch(method ->
                                        method.isAnnotationPresent(RequestMapping.class)
                                                && path.isSamePath(method.getDeclaredAnnotation(RequestMapping.class).path())
                                )
                    ).map(cls -> Arrays.stream(cls.getMethods())
                                .filter(method ->
                                        method.isAnnotationPresent(RequestMapping.class)
                                                && path.isSamePath(method.getDeclaredAnnotation(RequestMapping.class).path())
                                ).findFirst()
                    ).findFirst()
                    .orElseGet(Optional::empty);

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    private static List<Class> findAllClassInClassPath() throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;

        String path = BASE_CLASS_PATH.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, BASE_CLASS_PATH));
        }
        return classes;
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        assert files != null;

        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                String packagePrefix = BASE_CLASS_PATH;
                if (!BASE_CLASS_PATH.equals(packageName)) packagePrefix = packageName + ".";
                classes.addAll(findClasses(file,  packagePrefix + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
