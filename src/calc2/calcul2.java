package calc2;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
public class calcul2 {
    public static final String SEPARATOR = "----------------";

    public static double getIntegralValue(int method, double[] funcValues, double from, double to) {
        int intervalsCount = funcValues.length;
        double h = getIntervalLength(from, to, intervalsCount);
        double integral=0;
        switch (method) {
            case (1):
                integral = h * (funcValues[0] + funcValues[intervalsCount - 1]) / 2;
                for (int i = 1; i < intervalsCount - 1; i++) {
                    integral += h * funcValues[i];
                }
                break;
            case (2):
                integral = h * (funcValues[0] + funcValues[intervalsCount - 1]) / 3;
                for (int i = 1; i < intervalsCount - 1; i += 2) {
                    integral += h * 4 * funcValues[i] / 3;
                }
                for (int i = 2; i < intervalsCount - 2; i += 2) {
                    integral += h * 2 * funcValues[i] / 3;
                }
                break;

        }
            return integral;
        }

        public static double error ( int method, double from, double to, int intervalsCount){
            double[] funcValues1;
            double[] funcValues2;
            double err = 0;
            switch (method) {
                case (1):
                    funcValues1 = trapezium(from, to, intervalsCount);
                    funcValues2 = trapezium(from, to, intervalsCount / 2);
                    err = Math.abs(getIntegralValue(method, funcValues1, from, to) - getIntegralValue(method, funcValues2, from, to)) / 3;
                    break;
                case (2):
                    funcValues1 = simpson(from, to, intervalsCount);
                    funcValues2 = simpson(from, to, intervalsCount / 2);
                    err = Math.abs(getIntegralValue(method, funcValues1, from, to) - getIntegralValue(method, funcValues2, from, to)) / 15;
                    break;
            }
            return err;
        }
        public static void graphOfError ( int method, double from, double to, int intervalsCount) throws IOException {
            double h;
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ASUS\\Documents\\error2.txt");
            String str;
            double err = 0;
            for (int i = 4; i <= intervalsCount; i += 2) {
                err = error(method, from, to, i);
                h = getIntervalLength(from, to, i);
                str = String.format("%15.5f \t %15.7f %n", h, err); // 15d -> 15.2f

                fileOutputStream.write(str.getBytes());
            }
            fileOutputStream.close();
        }
        public static double getDefiniteIntegral ( double from, double to){
            return Math.pow(Math.E, to) - Math.pow(to, 4) / 4. + Math.pow(Math.E, from) - Math.pow(from, 4) / 4.;
        }

        public static void main (String[]args) throws IOException {
            double from = 0, to = 4;
            int intervalsCount = 50;
            int method = 1;
            if (intervalsCount % 2 != 0)
                intervalsCount--;
            double intervalLength = getIntervalLength(from, to, intervalsCount);
            double[] funcValues= new double[intervalsCount];

            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose a method:");
            System.out.println("1. trapezium  method");
            System.out.println("2. Simpson method");

            int choice = scanner.nextInt();
            switch (choice) {
                case (1):
                    method = 1;
                    funcValues = trapezium(from, to, intervalsCount);
                    break;
                case (2):
                    method = 2;
                    funcValues = simpson(from, to, intervalsCount);
                    break;

            }


            table(from, intervalsCount, to, funcValues);
            System.out.println(getIntegralValue(method, funcValues, from, to));
            System.out.println("Error: " + error(method, from, to, intervalsCount));

            System.out.println("Definite integral:");

            System.out.println(getDefiniteIntegral(from, to));
            toFile(from, intervalsCount, to, funcValues);
            graphOfError(method, from, to, intervalsCount);
        }

        public static double getIntervalLength ( double from, double to, int intervalsCount){
            return (to - from) / intervalsCount;
        }

        public static double[] trapezium ( double from, double to, int intervalsCount){
            double[] funcValues = new double[intervalsCount];
            double intervalLength = getIntervalLength(from, to, intervalsCount);
            double x = from;
            for (int i = 0; i < intervalsCount; i++, x += intervalLength) {
                funcValues[i] = Math.pow(Math.E, x) - x * x * x;
            }
            return funcValues;
        }

        public static double[] simpson ( double from, double to, int intervalsCount){
            if (intervalsCount % 2 != 0)
                intervalsCount--;
            double[] funcValues = new double[intervalsCount];
            double intervalLength = getIntervalLength(from, to, intervalsCount);
            double x = from;
            for (int i = 0; i < intervalsCount; i++, x += intervalLength) {
                funcValues[i] = Math.pow(Math.E, x) - x * x * x;
            }
            return funcValues;
        }

    /*public static double[] halfRectangle(double from, double to, int intervalsCount) {
        double[] funcValues = new double[intervalsCount];
        double intervalLength = getIntervalLength(from, to, intervalsCount);
        double x = from + intervalLength / 2;
        for (int i = 0; i < intervalsCount; i++, x += intervalLength) {
            funcValues[i] = Math.pow(Math.E, x) - x * x * x;
        }
        return funcValues;
    }*/

        public static void table ( double from, int intervalsCount, double to, double[] funcValues){
            double x = from;
            double intervalLength = getIntervalLength(from, to, intervalsCount);
            String format = "|%1$-16s|%2$-16s|\n";
            System.out.format(format, SEPARATOR, SEPARATOR);
            System.out.format(format,  "        x       ", "      y(x)      ");
            System.out.format(format, SEPARATOR, SEPARATOR);
            for (int i = 0; i < intervalsCount; i++, x += intervalLength) {
                System.out.format("|%15.2f |%15.7f |%n", x, funcValues[i]); // 15d -> 15.2f
                System.out.format(format, SEPARATOR, SEPARATOR);

            }
        }

        public static void toFile ( double from, int intervalsCount, double to, double[] funcValues) throws IOException
        {
            double x = from;
            double intervalLength = getIntervalLength(from, to, intervalsCount);
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ASUS\\Documents\\integral2.txt");
            String str;
            for (int i = 0; i < intervalsCount; i++, x += intervalLength) {
                str = String.format("%15.2f \t %15.7f %n", x, funcValues[i]); // 15d -> 15.2f

                fileOutputStream.write(str.getBytes());
            }
            fileOutputStream.close();

        }
    }
