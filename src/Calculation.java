import java.awt.*;

public class Calculation {
    private static float calculateB(int i, int n, float t) {

        return (float) (combination(n, i) * Math.pow(t, i) * Math.pow(1 - t, n - i));
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


    public static float calculateZ(float[][] z, float u, float v) {
        float res = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res += z[i][j] * calculateB(i, 3, u) * calculateB(j, 3, v);
            }
        }
        return res;
    }

    private static float[] calculateDerivativeU(float[][] z, float u, float v) {
        int n = 3;
        int m = 3;
        float sum = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < m; j++) {
                sum += (z[i + 1][j] - z[i][j]) * calculateB(i, n - 1, u) * calculateB(j, m, v);
            }
        }
        return new float[]{1, 0, n * sum};
    }

    private static float[] calculateDerivativeV(float[][] z, float u, float v) {
        int n = 3;
        int m = 3;
        float sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m - 1; j++) {
                sum += (z[i][j + 1] - z[i][j]) * calculateB(i, n, u) * calculateB(j, m - 1, v);
            }
        }
        return new float[]{0, 1, n * sum};
    }

    private static float[] vectorProduct(float[] v1, float[] v2) {
        return new float[]{v1[1] * v2[2] - v1[2] * v2[1], v1[2] * v2[0] - v1[0] * v2[2], v1[0] * v2[1] - v1[1] * v2[0]};
    }

    public static float[] normalVector(float[][] z, float u, float v) {
        float[] Pu = calculateDerivativeU(z, u, v);
        float[] Pv = calculateDerivativeV(z, u, v);

        float[] vector = vectorProduct(Pu, Pv);
        return normalizeVector(vector);
    }


    private static float lengthOfVector(float[] v) {
        return (float) Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
    }


    private float calculateBarZ(Point3D a, Point3D b, Point3D c, float[] d) {
        float w1 = ((b.y - c.y) * (d[0] - c.x) + (c.x - b.x) * (d[1] - c.y))
                / ((b.y - c.y) * (a.x - c.x) + (c.x - b.x) * (a.y - c.y));

        float w2 = ((c.y - a.y) * (d[0] - c.x) + (a.x - c.x) * (d[1] - c.y))
                / ((b.y - c.y) * (a.x - c.x) + (c.x - b.x) * (a.y - c.y));

        float w3 = 1 - w1 - w2;

        return w1 * a.z + w2 * b.z + w3 * c.z;
    }

    public float[] calculateBarVector(Point3D a, Point3D b, Point3D c, Point d, float[] v1, float[] v2, float[] v3) {
        float w1 = ((b.y - c.y) * (d.x - c.x) + (c.x - b.x) * (d.y - c.y))
                / ((b.y - c.y) * (a.x - c.x) + (c.x - b.x) * (a.y - c.y));

        float w2 = ((c.y - a.y) * (d.x - c.x) + (a.x - c.x) * (d.y - c.y))
                / ((b.y - c.y) * (a.x - c.x) + (c.x - b.x) * (a.y - c.y));

        float w3 = 1 - w1 - w2;

        float r1 = w1 * v1[0] + w2 * v2[0] + w3 * v3[0];
        float r2 = w1 * v1[1] + w2 * v2[1] + w3 * v3[1];
        float r3 = w1 * v1[2] + w2 * v2[2] + w3 * v3[2];
        float[] vector = new float[]{r1, r2, r3};
        return normalizeVector(vector);
    }

    public static float cos(float[] a, float[] b) {
        float cos = (a[0] * b[0] + a[1] * b[1] + a[2] * b[2]);
        return cos < 0 ? 0 : cos;
    }

    public static float crossProduct(float[] a, float[] b) {
        float res = 0;
        for (int i = 0; i < a.length; i++) {
            res += a[i] * b[i];
        }
        return res;
    }

    public static float[] calculateR(float[] n, float[] l) {
        float[] res = new float[3];
        for (int i = 0; i < res.length; i++) {
            res[i] = 2 * crossProduct(n, l) * n[i] - l[i];
        }
        return normalizeVector(res);
    }

    public static float[] calculateVectorFromPoints(float[] a, float[] b) {
        float[] vector = new float[]{b[0] - a[0], b[1] - a[1], b[2] - a[2]};
        return normalizeVector(vector);
    }

    public static float[] normalizeVector(float[] vector) {
        float l = lengthOfVector(vector);
        if(l != 0) {
            vector[0] /= l;
            vector[1] /= l;
            vector[2] /= l;
        }
        return vector;
    }

    public static float[][] calculateM(float[] n) {
        float[] B = vectorProduct(n, new float[]{0, 0, 1});
        if(n[0] == 0 && n[1] == 0 && n[2] == 1)
            B = new float[] {0,1,0};
        B = normalizeVector(B);
        float[] T = vectorProduct(B, n);
        T = normalizeVector(T);
        return new float[][] { T, B, n};
    }

    public static float[] matrixByVector(float[][] M, float[] v) {
        float x = M[0][0] * v[0] + M[0][1] * v[1] + M[0][2] * v[2];
        float y = M[1][0] * v[0] + M[1][1] * v[1] + M[1][2] * v[2];
        float z = M[2][0] * v[0] + M[2][1] * v[1] + M[2][2] * v[2];
        return new float[]{x, y, z};
    }


}
