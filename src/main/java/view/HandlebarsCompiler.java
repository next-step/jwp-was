package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class HandlebarsCompiler implements ViewCompiler {

    private final Handlebars compiler;

    private HandlebarsCompiler(final Handlebars compiler) {
        this.compiler = compiler;
    }

    public static HandlebarsCompiler of(final String prefix,
                                        final String suffix) {
        final TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(prefix);
        loader.setSuffix(suffix);

        final Handlebars compiler = new Handlebars(loader);

        return new HandlebarsCompiler(compiler);
    }

    @Override
    public String compile(final String filePath,
                          final Object context) throws IOException {
        return compiler.compile(filePath)
                .apply(context);
    }
}
