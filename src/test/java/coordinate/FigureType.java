package coordinate;

import java.util.*;
import java.util.stream.Collectors;

public enum FigureType {
    LINE(2,  Line::new),
    TRIANGLE(3,  Triangle::new),
    RECTANGLE(4,  Rectangle::new);

    private int numberOfPoints;
    private  FigureCreator figureCreator;

    private static Map<Integer, FigureType> figures;

    static {
        figures = Arrays.stream(values())
                .collect(Collectors.toMap(figureType -> figureType.numberOfPoints, figureType -> figureType, (figureType1, figureType2) -> figureType1));
    }

    FigureType(int numberOfPoints, FigureCreator figureCreator) {
        this.numberOfPoints = numberOfPoints;
        this.figureCreator = figureCreator;
    }

    public static Figure getFigure(List<Point> points) {
        return Optional.ofNullable(figures.get(points.size()))
                .map(figureType -> figureType.figureCreator)
                .map(creator -> creator.create(points))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 도형입니다."));
    }
}
