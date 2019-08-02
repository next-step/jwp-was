package coordinate;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;

public class FigureConstructor implements FigureCreator{

    public static HashMap<Integer, Class> figureHashMap = new HashMap<>();

    public FigureConstructor(){
        figureHashMap.put(2, Line.class);
        figureHashMap.put(3, Triangle.class);
        figureHashMap.put(4, Rectangle.class);
    }

    @Override
    public Figure create(List<Point> points){
        try {
            Class figureClass = figureHashMap.get(points.size());
            Constructor cs = figureClass.getConstructor(new Class[]{List.class});

            Figure figure = (Figure) cs.newInstance(points);
            return figure;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
