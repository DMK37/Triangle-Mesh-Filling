import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Mesh {

    List<Triangle> triangleFaces;
    static int  numberInRow = 28;

    double[][] z;

    public Mesh() {
        z = new double[4][4];
        for(int i = 0 ; i < 4; i++) {
            for(int j = 0; j < 4; j++)
            {
                if((i == 0 && j == 0) ||(i == 3 && j == 0) || (i == 0 && j == 3) || (i == 3 && j == 3)) continue;
                z[i][j] = Math.random() % 5;
            }
        }

        triangleFaces = new ArrayList<>();
        triangulate(numberInRow);
    }
    
    public void drawMesh(Graphics2D g) {
        for (Triangle triangle:
             triangleFaces) {
            ScanLineAlgorithm.fillTriangle(triangle, g, this);
        }
    }

    public void triangulate(int numberInRow) {
        double width = (double) 1 / numberInRow;
        int margin = DrawPanel.MARGIN;
        for (double i = 0; i < 1; i += width) {
            for (double j = 0; j < 1; j += width) {
                if(i + width > 1 || j + width > 1)
                    continue;
                double z1 = Calculation.calculateZ(z, i, j);
                double z2 = Calculation.calculateZ(z, i + width, j);
                double z3 = Calculation.calculateZ(z, i, j + width);
                double[] v1 = Calculation.normalVector(z, i, j);
                double[] v2 = Calculation.normalVector(z, i + width, j);
                double[] v3 = Calculation.normalVector(z, i, j + width);
                triangleFaces.add(new Triangle(new Point3D(i, j, z1), new Point3D(i + width, j, z2),
                        new Point3D(i, j + width, z3), v1, v2, v3));

                z1 = Calculation.calculateZ(z, i + width, j + width);
                v1 = Calculation.normalVector(z, i + width, j + width);
                triangleFaces.add(new Triangle(new Point3D(i + width, j + width , z1), new Point3D(i + width, j, z2),
                        new Point3D(i, j + width, z3), v1, v2, v3));
            }

        }
    }
}
