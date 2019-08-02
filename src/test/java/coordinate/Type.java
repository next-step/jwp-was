package coordinate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Type implements FigureCreator {
    LINE(2) {
        @Override
        public Figure create(List<Point> points) {
            return new Line(points);
        }
    },
    RECTANGLE(4) {
        @Override
        public Figure create(List<Point> points) {
            return new Rectangle(points);
        }
    },
    TRIANGLE(3) {
        @Override
        public Figure create(List<Point> points) {
            return new Triangle(points);
        }
    };

    private final int numberOfPoint;

    private static final Map<Integer, Type> figures;

    static {
        figures = Arrays.stream(Type.values())
                .collect(Collectors.toMap(type -> type.numberOfPoint, type -> type));
    }

    Type(int numberOfPoint) {
        this.numberOfPoint = numberOfPoint;
    }

    public static Figure of(List<Point> points) {
        return Optional.ofNullable(figures.get(points.size()))
                .map(type -> type.create(points))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 도형입니다."));
    }
}
