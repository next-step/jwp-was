package coordinate;

import java.util.List;

public class FigureFactory {
    static Figure getInstance(List<Point> points) {

        FigureCreator figureCreator = FigureCreatorFactory.create(points);
        return figureCreator.create(points);
    }
}
