import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Mesh {

    List<Triangle> triangleFaces;
    public static int  numberInRow = 12;

    float[][] z;

    public Mesh() {
        z = new float[4][4];
        for(int i = 0 ; i < 4; i++) {
            for(int j = 0; j < 4; j++)
            {
                if((i == 0 && j == 0) ||(i == 3 && j == 0) || (i == 0 && j == 3) || (i == 3 && j == 3)) continue;
                //z[i][j] = (float) (Math.random() % 5);
            }
        }
        triangleFaces = new ArrayList<>();
        triangulate(numberInRow);
    }
    
    public void drawMesh(Graphics2D g) throws ExecutionException, InterruptedException {
        //ExecutorService executor = Executors.newFixedThreadPool(10);
        for (Triangle triangle :
                triangleFaces) {

//            CompletableFuture<List<ColoredPoint>> future = CompletableFuture.supplyAsync(() ->
//            {
//                return ScanLineAlgorithm.fillTriangleAsync(triangle, this);}
//            ,executor);
//            triangle.coloredPoints = future.get();
            ScanLineAlgorithm.fillTriangle(triangle, g, this);
        }
//        for (Triangle triangle :
//                triangleFaces) {
//            triangle.fill(g);
//        }
//        executor.shutdown();
    }

    public void triangulate(int numberInRow) {
        float width =  (float) 1 / numberInRow;
        int margin = DrawPanel.MARGIN;
        for (float i = 0; i < 1; i += width) {
            for (float j = 0; j < 1; j += width) {
                if(i + width > 1 || j + width > 1)
                    continue;
                float z1 = Calculation.calculateZ(z, i, j);
                float z2 = Calculation.calculateZ(z, i + width, j);
                float z3 = Calculation.calculateZ(z, i, j + width);
                float[] v1 = Calculation.normalVector(z, i, j);
                float[] v2 = Calculation.normalVector(z, i + width, j);
                float[] v3 = Calculation.normalVector(z, i, j + width);
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
