package coordinate;

import java.util.*;

public class FigureFactory {

    private enum DefaultFigureCreator implements FigureCreator {
        LINE(2) {
            @Override
            public Figure create(List<Point> points) {
                return new Line(points);
            }
        },
        Triangle(3) {
            @Override
            public Figure create(List<Point> points) {
                return new Triangle(points);
            }
        },
        Rectangle(4) {
            @Override
            public Figure create(List<Point> points) {
                return new Rectangle(points);
            }
        };

        private int pointCount;

        DefaultFigureCreator(int pointCount) {
            this.pointCount = pointCount;
        }

        public boolean isEqual(int pointCount) {
            return this.pointCount == pointCount;
        }

        public static Optional<Figure> figureBy(List<Point> points) {
            return Arrays.stream(DefaultFigureCreator.values())
                    .filter(figure -> figure.isEqual(points.size()))
                    .map(defaultFigureCreator -> defaultFigureCreator.create(points))
                    .findFirst();
        }
    }

    static Figure getInstance(List<Point> points) {
        Optional<Figure> maybeFigure = DefaultFigureCreator.figureBy(points);

        if (maybeFigure.isPresent()) {
            return maybeFigure
                    .get();
        }

        throw new IllegalArgumentException("유효하지 않은 도형입니다.");
    }
}
