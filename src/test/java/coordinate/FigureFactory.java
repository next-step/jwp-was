package coordinate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FigureFactory {
	private static final Map<Integer, FigureType> FIGURE_MAP;

	static {
		FIGURE_MAP = Arrays.stream(FigureType.values())
			.collect(Collectors.toMap(figureType -> figureType.pointSize, Function.identity()));
	}

	static Figure getInstance(List<Point> points) {
		FigureType figureType = FIGURE_MAP.get(points.size());
		if (Objects.isNull(figureType)) {
			throw new IllegalArgumentException("유효하지 않은 도형입니다.");
		}
		return figureType.create(points);
	}

	private enum FigureType {
		LINE(Line.POINT_SIZE, Line::new),
		TRIANGLE(Triangle.POINT_SIZE, Triangle::new),
		RECTANGLE(Rectangle.POINT_SIZE, Rectangle::new);

		private int pointSize;
		private Function<List<Point>, Figure> figureConstructor;

		FigureType(int pointSize, Function<List<Point>, Figure> figureConstructor) {
			this.pointSize = pointSize;
			this.figureConstructor = figureConstructor;
		}

		public Figure create(List<Point> points) {
			return figureConstructor.apply(points);
		}

	}
}
