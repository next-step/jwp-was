package coordinate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Type {
    LINE(2, Line::new),
    RECTANGLE(4, Rectangle::new),
    TRIANGLE(3, Triangle::new);

    private final int numberOfPoint;
    private final FigureCreator figureCreator;

    private static final Map<Integer, Type> figures;

    static {
        figures = Arrays.stream(Type.values())
                .collect(Collectors.toMap(type -> type.numberOfPoint, type -> type));
    }

    Type(int numberOfPoint, FigureCreator figureCreator) {
        this.numberOfPoint = numberOfPoint;
        this.figureCreator = figureCreator;
    }

    public static Figure of(List<Point> points) {
        return Optional.ofNullable(figures.get(points.size()))
                .map(type -> type.figureCreator.create(points))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 도형입니다."));
    }
}
