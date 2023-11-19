import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScanLineAlgorithm {
    private static List<Integer> sortVertices(List<Point> points, WrappedYMinMax wrapper) {
        List<Integer> sortedIndexes = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            sortedIndexes.add(i);
        }
        sortedIndexes.sort(Comparator.comparingInt(a -> (int) points.get(a).getY()));
        wrapper.yMin = points.get(sortedIndexes.get(0)).y;
        wrapper.yMax = points.get(sortedIndexes.get(sortedIndexes.size() - 1)).y;
        return sortedIndexes;
    }

    private static class WrappedYMinMax {
        int yMin;
        int yMax;
    }

    private static class AET {
        public Point A;
        public Point B;
        public float X;

        public AET(Point a, Point b, int y) {
            this.A = a;
            this.B = b;
            this.SetX(y);
        }

        public void SetX(int y) {
            float tmp = ((float) (A.y - B.y) / (float) (A.x - B.x));
            X = Math.abs(A.x - B.x) < 1 ? A.x : (y - (A.y - tmp * A.x)) / tmp;
        }
    }

    public static void fillPolygon(List<Point> points, Graphics2D g) {
        WrappedYMinMax wrappedYMinMax = new WrappedYMinMax();
        List<Integer> sortedIndexees = sortVertices(points, wrappedYMinMax);
        int yMin = wrappedYMinMax.yMin;
        int yMax = wrappedYMinMax.yMax;

        List<AET> aets = new ArrayList<>();

        for (int y = yMin; y <= yMax; y++) {
            for (int i = 0; i < sortedIndexees.size(); i++) {
                int idx = sortedIndexees.get(i);
                if (points.get(idx).y == y - 1) {
                    int prev;
                    if (idx - 1 < 0)
                        prev = sortedIndexees.size() - 1;
                    else {
                        prev = (idx - 1);
                    }
                    if (points.get(prev).y < points.get(idx).y) {
                        aets.removeIf(node -> (node.A == points.get(prev) && node.B == points.get(idx)) || (node.A == points.get(idx) && node.B == points.get(prev)));
                    } else {
                        if (points.get(prev).y > points.get(idx).y)
                            aets.add(new AET(points.get(prev), points.get(idx), y));
                    }
                    int next = (sortedIndexees.get(i) + 1) % sortedIndexees.size();
                    if (points.get(next).y < points.get(idx).y) {
                        aets.removeIf(node -> (node.A == points.get(next) && node.B == points.get(idx)) || (node.A == points.get(idx) && node.B == points.get(next)));
                    } else {
                        if (points.get(next).y > points.get(idx).y) {
                            aets.add(new AET(points.get(next), points.get(idx), y));
                        }
                    }


                }
            }
            aets.sort(Comparator.comparingDouble(a -> a.X));
            for (int i = 0; i < aets.size() - 1; i += 2) {
                int xMin = (int) aets.get(i).X;
                int xMax = (int) aets.get(i + 1).X;
                for (int x = xMin; x < xMax; x++) g.drawOval(x, y, 1, 1);
            }

            for (var aet :
                    aets) {
                aet.SetX(y);
            }
        }
    }

    public static void fillTriangle(Triangle triangle, Graphics2D g, Mesh mesh) {
        List<Point> points = new ArrayList<>();
        float j = triangle.points[0].x * DrawPanel.HEIGHT + 2 * DrawPanel.MARGIN;
        float k = triangle.points[0].y * DrawPanel.HEIGHT + 2 * DrawPanel.MARGIN;
        points.add(new Point((int) j, (int) k));
        j = triangle.points[1].x * DrawPanel.HEIGHT + 2 * DrawPanel.MARGIN;
        k = triangle.points[1].y * DrawPanel.HEIGHT + 2 * DrawPanel.MARGIN;
        points.add(new Point((int) j, (int) k));
        j = triangle.points[2].x * DrawPanel.HEIGHT + 2 * DrawPanel.MARGIN;
        k = triangle.points[2].y * DrawPanel.HEIGHT + 2 * DrawPanel.MARGIN;
        points.add(new Point((int) j, (int) k));

        WrappedYMinMax wrappedYMinMax = new WrappedYMinMax();
        List<Integer> sortedIndexees = sortVertices(points, wrappedYMinMax);
        int yMin = wrappedYMinMax.yMin;
        int yMax = wrappedYMinMax.yMax;

        List<AET> aets = new ArrayList<>();

        for (int y = yMin; y <= yMax; y++) {
            for (int i = 0; i < sortedIndexees.size(); i++) {
                int idx = sortedIndexees.get(i);
                if (points.get(idx).y == y - 1) {
                    int prev;
                    if (idx - 1 < 0)
                        prev = sortedIndexees.size() - 1;
                    else {
                        prev = (idx - 1);
                    }
                    if (points.get(prev).y < points.get(idx).y) {
                        aets.removeIf(node -> (node.A == points.get(prev) && node.B == points.get(idx)) || (node.A == points.get(idx) && node.B == points.get(prev)));
                    } else {
                        if (points.get(prev).y > points.get(idx).y)
                            aets.add(new AET(points.get(prev), points.get(idx), y));
                    }
                    int next = (sortedIndexees.get(i) + 1) % sortedIndexees.size();
                    if (points.get(next).y < points.get(idx).y) {
                        aets.removeIf(node -> (node.A == points.get(next) && node.B == points.get(idx)) || (node.A == points.get(idx) && node.B == points.get(next)));
                    } else {
                        if (points.get(next).y > points.get(idx).y) {
                            aets.add(new AET(points.get(next), points.get(idx), y));
                        }
                    }


                }
            }
            aets.sort(Comparator.comparingDouble(a -> a.X));
            for (int i = 0; i < aets.size() - 1; i += 2) {
                int xMin = (int) aets.get(i).X;
                int xMax = (int) aets.get(i + 1).X;
                for (int x = xMin; x < xMax; x++) {
                    float trX =  (float) (x - DrawPanel.MARGIN) / DrawPanel.WIDTH;
                    float trY =  (float) (y - DrawPanel.MARGIN) / DrawPanel.WIDTH;
                    float[] nVector = Calculation.normalVector(mesh.z, trX, trY);
                    float trZ = Calculation.calculateZ(mesh.z, trX, trY);
                    float[] c;
                    if(DrawPanel.image == null)
                    {
                        c = triangle.pickColor(DrawPanel.lightColor, DrawPanel.objectColor, nVector, Calculation.calculateVectorFromPoints(new float[]{trX, trY, trZ}, DrawPanel.ligthPosition));
                    }
                    else {
                        int col = DrawPanel.image.getRGB(x - 2*DrawPanel.MARGIN, y - 2*DrawPanel.MARGIN);
                        int  red = (col & 0x00ff0000) >> 16;
                        int  green = (col & 0x0000ff00) >> 8;
                        int  blue = col & 0x000000ff;
                        if(OptionsPanel.normalMapBox.isSelected()) {
                            float[][] mat = Calculation.calculateM(nVector);
                            // rescale rgb
                            nVector = Calculation.matrixByVector(mat, new float[] {(float) red / 255 * 2 - 1, (float) green / 255 * 2 - 1, (float) blue / 255});
                        }
                        float[] objColor = new float[] {(float) red / 255, (float) green / 255, (float) blue / 255};
                        c = triangle.pickColor(DrawPanel.lightColor, objColor, nVector, Calculation.calculateVectorFromPoints(new float[]{trX, trY, trZ}, DrawPanel.ligthPosition));
                    }
                    g.setColor(new Color((int) (c[0] * 255), (int) (c[1] * 255), (int) (c[2] * 255)));
                    g.drawOval(x, y, 1, 1);
                }
            }

            for (var aet :
                    aets) {
                aet.SetX(y);
            }
        }
    }
}
