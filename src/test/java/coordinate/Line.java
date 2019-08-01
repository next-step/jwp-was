package coordinate;

import java.util.List;

public class Line extends AbstractFigure {

    static final int COUNT_OF_POINTS = 2;

    public Line(List<Point> points) {
        super(points);
    }

    @Override
    public int size() {
        return COUNT_OF_POINTS;
    }

    @Override
    public String getName() {
        return "선";
    }

    @Override
    public double area() {
        return 0;
    }
}
