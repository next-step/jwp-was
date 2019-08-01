package coordinate;

import java.util.Arrays;
import java.util.List;

public class FigureFactory {
    static Figure getInstance(List<Point> points) {
        return Arrays.stream(FigureCreatorType.values())
                .filter(f -> f.getSize() == points.size())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 도형입니다."))
                .create(points);
    }

    public enum FigureCreatorType implements FigureCreator {
        LINE(2) {
            public Figure create(List<Point> points) { return new Line(points); }
        },
        TRIANGLE(3) {
            public Figure create(List<Point> points) { return new Triangle(points); }
        },
        RECTANGL(4) {
            public Figure create(List<Point> points) { return new Rectangle(points); }
        };

        private int size;

        FigureCreatorType(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }
}
