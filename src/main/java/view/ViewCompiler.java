package view;

import java.io.IOException;

@FunctionalInterface
public interface ViewCompiler {

    String compile(final String filePath, final Object context) throws IOException;
}
