package coordinate;

import java.util.Arrays;
import java.util.List;

public enum FigureConstructor implements FigureCreator{

    LINE(2){
        @Override
        public Figure create(List<Point> points) {
            return new Line(points);
        }
    },
    TRIANGLE(3){
        @Override
        public Figure create(List<Point> points) {
            return new Triangle(points);
        }
    },
    RECTANGLE(4){
        @Override
        public Figure create(List<Point> points) {
            return new Rectangle(points);
        }
    };


    private final int pointSize;

    FigureConstructor(int pointSize) {
        this.pointSize = pointSize;
    }

    public static Figure getFigureConstructor(List<Point> points){
        return Arrays.stream(FigureConstructor.values())
                .filter(filter -> filter.pointSize == points.size())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 도형입니다."))
                .create(points);
    }
}
