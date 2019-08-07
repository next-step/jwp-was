package coordinate;

import java.util.List;

public enum SpecificFigureCreator implements FigureCreator {

    LINE(2) {
        @Override
        public Figure create(List<Point> points) {
            return new Line(points);
        }
    },

    TRIANGLE(3) {
        @Override
        public Figure create(List<Point> points) {
            return new Triangle(points);
        }
    },

    RECTANGLE(4) {
        @Override
        public Figure create(List<Point> points) {
            return new Rectangle(points);
        }
    };

    private final int size;

    SpecificFigureCreator(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}