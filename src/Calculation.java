import java.awt.*;

public class Calculation {
    private static double calculateB(int i, int n, double t) {
        return combination(n, i) * Math.pow(t, i) * Math.pow(1 - t, n - i);
    }

    private static float factorial(int n) {
        if (n < 0)
            throw new ArithmeticException("n can't be less than zero");
        int res = 1;
        for (int i = 1; i <= n; i++) {
            res *= i;
        }
        return res;
    }

    private static float combination(int n, int i) {
        if (i > n)
            throw new ArithmeticException("n can't be less than zero");

        return factorial(n) / (factorial(n - i) * factorial(i));
    }


    public static double calculateZ(double[][] z, double u, double v) {
        double res = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res += z[i][j] * calculateB(i, 3, u) * calculateB(j, 3, v);
            }
        }
        return res;
    }

    private static double[] calculateDerivativeU(double[][] z, double u, double v) {
        int n = 3;
        int m = 3;
        double sum = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < m; j++) {
                sum += (z[i + 1][j] - z[i][j]) * calculateB(i, n - 1, u) * calculateB(j, m, v);
            }
        }
        return new double[]{1, 0, n * sum};
    }

    private static double[] calculateDerivativeV(double[][] z, double u, double v) {
        int n = 3;
        int m = 3;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m - 1; j++) {
                sum += (z[i][j + 1] - z[i][j]) * calculateB(i, n, u) * calculateB(j, m - 1, v);
            }
        }
        return new double[]{0, 1, n * sum};
    }

    public static double[] normalVector(double[][] z, double u, double v) {
        double[] Pu = calculateDerivativeU(z, u, v);
        double[] Pv = calculateDerivativeV(z, u, v);

        double[] vector = new double[]{Pu[1] * Pv[2] - Pu[2] * Pv[1], Pu[2] * Pv[0] - Pu[0] * Pv[2], Pu[0] * Pv[1] - Pu[1] * Pv[0]};
        double l = lengthOfVector(vector);
        if(l != 0) {
            vector[0] /= l;
            vector[1] /= l;
            vector[2] /= l;
        }
        return vector;
    }


    private static double lengthOfVector(double[] v) {
        return Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
    }


    private double calculateBarZ(Point3D a, Point3D b, Point3D c, double[] d) {
        double w1 = (double) ((b.y - c.y) * (d[0] - c.x) + (c.x - b.x) * (d[1] - c.y))
                / ((b.y - c.y) * (a.x - c.x) + (c.x - b.x) * (a.y - c.y));

        double w2 = (double) ((c.y - a.y) * (d[0] - c.x) + (a.x - c.x) * (d[1] - c.y))
                / ((b.y - c.y) * (a.x - c.x) + (c.x - b.x) * (a.y - c.y));

        double w3 = 1 - w1 - w2;

        return w1 * a.z + w2 * b.z + w3 * c.z;
    }

    public double[] calculateBarVector(Point3D a, Point3D b, Point3D c, Point d, double[] v1, double[] v2, double[] v3) {
        double w1 = (double) ((b.y - c.y) * (d.x - c.x) + (c.x - b.x) * (d.y - c.y))
                / ((b.y - c.y) * (a.x - c.x) + (c.x - b.x) * (a.y - c.y));

        double w2 = (double) ((c.y - a.y) * (d.x - c.x) + (a.x - c.x) * (d.y - c.y))
                / ((b.y - c.y) * (a.x - c.x) + (c.x - b.x) * (a.y - c.y));

        double w3 = 1 - w1 - w2;

        double r1 = w1 * v1[0] + w2 * v2[0] + w3 * v3[0];
        double r2 = w1 * v1[1] + w2 * v2[1] + w3 * v3[1];
        double r3 = w1 * v1[2] + w2 * v2[2] + w3 * v3[2];
        double[] vector = new double[]{r1, r2, r3};
        double l = lengthOfVector(vector);
        if(l != 0) {
            vector[0] /= l;
            vector[1] /= l;
            vector[2] /= l;
        }
        return vector;
    }

    public static double cos(double[] a, double[] b) {
        double cos = (a[0] * b[0] + a[1] * b[1] + a[2] * b[2]);
        return cos < 0 ? 0 : cos;
    }

    public static double crossProduct(double[] a, double[] b) {
        double res = 0;
        for (int i = 0; i < a.length; i++) {
            res += a[i] * b[i];
        }
        return res;
    }

    public static double[] calculateR(double[] n, double[] l) {
        double[] res = new double[3];
        for (int i = 0; i < res.length; i++) {
            res[i] = 2 * crossProduct(n, l) * n[i] - l[i];
        }
        double len = lengthOfVector(res);
        if(len != 0) {
            res[0] /= len;
            res[1] /= len;
            res[2] /= len;
        }
        return res;
    }

    public static double[] calculateVectorFromPoints(double[] a, double[] b) {
        double[] vector = new double[]{b[0] - a[0], b[1] - a[1], b[2] - a[2]};
        double l = lengthOfVector(vector);
        if(l != 0) {
            vector[0] /= l;
            vector[1] /= l;
            vector[2] /= l;
        }
        return vector;
    }


}
