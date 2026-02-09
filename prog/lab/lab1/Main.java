import java.util.Random;

public class Main {
    public static void main(String[] args) {
        long[] l = new long[8];
        double[] x = new double[19];
        double[][] w = new double[8][19];
        var rand = new Random();
        for (int i = 4, j = 0; i < 19; i += 2, j += 1){
            l[j] = i;
        }
        for (var i = 0; i < 19; i++) {
            x[i] = rand.nextDouble(-15.0, 5.0);
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 19; j++) {
                if (l[i] == 16) {
                    w[i][j] = Calc1(x[j]);
                } else if (l[i] == 6 || l[i] == 8 || l[i] == 12 || l[i] == 18) {
                    w[i][j] = Calc2(x[j]);
                } else {
                    w[i][j] = Calc3(x[j]);
                }
            }
        }
        PrintMatrix(w);
    }



    private static double Calc1(double x) {
        return Math.atan(Math.pow((x - 5) / 2 * Math.E + 1, 2));
    }

    private static double Calc2(double x) {
        return Math.pow(Math.sin(Math.tan(x)), Math.cos(Math.sin(x)));
    }

    private static double Calc3(double x) {
        return (Math.cbrt(Math.log(Math.sqrt(Math.abs(x))))) * (Math.PI / Math.atan(Math.sin(Math.tan(Math.asin((x - 5) / 2 * Math.E + 1)))));
    }

    private static void PrintMatrix(double[][] w) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 19; j++) {
                if (j != 18) {
                    System.out.print(String.format("%8.5f", w[i][j]) + ' ');
                } else {
                    System.out.printf("%8.5f", w[i][j]);
                }
            }
            System.out.print('\n');
        }
    }
}

