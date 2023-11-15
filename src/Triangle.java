import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Triangle {
    Point3D[] points;

    double[] normalVector1;
    double[] normalVector2;
    double[] normalVector3;

    public Triangle(Point3D a, Point3D b, Point3D c, double[] vector1, double[] vector2, double[] vector3) {
        points = new Point3D[3];
        points[0] = a;
        points[1] = b;
        points[2] = c;
        normalVector1 = vector1;
        normalVector2 = vector2;
        normalVector3 = vector3;
    }

    public void fillTriangle(Graphics2D g) {
        List<Point> p = new ArrayList<>();
        p.add(new Point((int) (points[0].x * DrawPanel.WIDTH) + 2 * DrawPanel.MARGIN, (int) (points[0].y * DrawPanel.WIDTH) + 2 * DrawPanel.MARGIN));
        p.add(new Point((int) (points[1].x * DrawPanel.WIDTH) + 2 * DrawPanel.MARGIN, (int) (points[1].y * DrawPanel.WIDTH) + 2 * DrawPanel.MARGIN));
        p.add(new Point((int) (points[2].x * DrawPanel.WIDTH) + 2 * DrawPanel.MARGIN, (int) (points[2].y * DrawPanel.WIDTH) + 2 * DrawPanel.MARGIN));
        ScanLineAlgorithm.fillPolygon(p, g);
    }

    public double[] pickColor(double[] lightColor, double[] objectColor, double[] normalVector, double[] lightVersor) {
        double r = (double) OptionsPanel.KdSlider.getValue() / 100 * lightColor[0] * objectColor[0] * Calculation.cos(normalVector, lightVersor)
                + (double) OptionsPanel.KsSlider.getValue() / 100 * lightColor[0] * objectColor[0] * Math.pow(Calculation.cos(new double[]{0, 0, 1}, Calculation.calculateR(normalVector, lightVersor)), OptionsPanel.mSlider.getValue() == 0 ? 1 : OptionsPanel.mSlider.getValue());
        double g = (double) OptionsPanel.KdSlider.getValue() / 100 * lightColor[1] * objectColor[1] * Calculation.cos(normalVector, lightVersor)
                + (double) OptionsPanel.KsSlider.getValue() / 100 * lightColor[1] * objectColor[1] * Math.pow(Calculation.cos(new double[]{0, 0, 1}, Calculation.calculateR(normalVector, lightVersor)), OptionsPanel.mSlider.getValue() == 0 ? 1 : OptionsPanel.mSlider.getValue());
        double b = (double) OptionsPanel.KdSlider.getValue() / 100 * lightColor[2] * objectColor[2] * Calculation.cos(normalVector, lightVersor)
                + (double) OptionsPanel.KsSlider.getValue() / 100 * lightColor[2] * objectColor[2] * Math.pow(Calculation.cos(new double[]{0, 0, 1}, Calculation.calculateR(normalVector, lightVersor)), OptionsPanel.mSlider.getValue() == 0 ? 1 : OptionsPanel.mSlider.getValue());
        if(r > 1) r = 1;
        if(g > 1) g = 1;
        if(b > 1) b = 1;
        return new double[]{r, g, b};
    }


}
