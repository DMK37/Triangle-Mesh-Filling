
public class Triangle {
    Point3D[] points;

    float[] normalVector1;
    float[] normalVector2;
    float[] normalVector3;

    public Triangle(Point3D a, Point3D b, Point3D c, float[] vector1, float[] vector2, float[] vector3) {
        points = new Point3D[3];
        points[0] = a;
        points[1] = b;
        points[2] = c;
        normalVector1 = vector1;
        normalVector2 = vector2;
        normalVector3 = vector3;
    }

    public float[] pickColor(float[] lightColor, float[] objectColor, float[] normalVector, float[] lightVersor) {
        float[] R = Calculation.calculateR(normalVector, lightVersor);
        float r = (float) (OptionsPanel.KdSlider.getValue() / 100 * lightColor[0] * objectColor[0] * Calculation.cos(normalVector, lightVersor)
                + (float) OptionsPanel.KsSlider.getValue() / 100 * lightColor[0] * objectColor[0] * Math.pow(Calculation.cos(new float[]{0, 0, 1}, R), OptionsPanel.mSlider.getValue() == 0 ? 1 : OptionsPanel.mSlider.getValue()));
        float g = (float) ((float) OptionsPanel.KdSlider.getValue() / 100 * lightColor[1] * objectColor[1] * Calculation.cos(normalVector, lightVersor)
                + (float) OptionsPanel.KsSlider.getValue() / 100 * lightColor[1] * objectColor[1] * Math.pow(Calculation.cos(new float[]{0, 0, 1}, R), OptionsPanel.mSlider.getValue() == 0 ? 1 : OptionsPanel.mSlider.getValue()));
        float b = (float) ((float) OptionsPanel.KdSlider.getValue() / 100 * lightColor[2] * objectColor[2] * Calculation.cos(normalVector, lightVersor)
                + (float) OptionsPanel.KsSlider.getValue() / 100 * lightColor[2] * objectColor[2] * Math.pow(Calculation.cos(new float[]{0, 0, 1}, R), OptionsPanel.mSlider.getValue() == 0 ? 1 : OptionsPanel.mSlider.getValue()));
        if (r > 1) r = 1;
        if (g > 1) g = 1;
        if (b > 1) b = 1;
        return new float[]{r, g, b};
    }


}
