package calc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class calcul {
    public static final String SEPARATOR = "----------------";

    public static double getIntegralValue(double[] funcValues, double from, double to) {
        int intervalsCount = funcValues.length;
        double h = getIntervalLength(from, to, intervalsCount);
        double integral = 0;
        for (int i = 0; i < intervalsCount; i++) {
            integral += h * funcValues[i];
        }
        return integral;
    }

    public static double error(int method, double from, double to, int intervalsCount) {
        double[] funcValues1;
        double[] funcValues2;
        double err = 0;
        switch (method) {
            case (1):
                funcValues1 = halfRectangle(from, to, intervalsCount);
                funcValues2 = halfRectangle(from, to, intervalsCount / 2);
                err = Math.abs(getIntegralValue(funcValues1, from, to) - getIntegralValue(funcValues2, from, to)) / 3;
                break;
            case (2):
                funcValues1 = leftRectangle(from, to, intervalsCount);
                funcValues2 = leftRectangle(from, to, intervalsCount / 2);
                err = Math.abs(getIntegralValue(funcValues1, from, to) - getIntegralValue(funcValues2, from, to));
                break;
            case (3):
                funcValues1 = rightRectangle(from, to, intervalsCount);
                funcValues2 = rightRectangle(from, to, intervalsCount / 2);
                err = Math.abs(getIntegralValue(funcValues1, from, to) - getIntegralValue(funcValues2, from, to));
                break;
        }
        return err;
    }
    public static void graphOfError(int method, double from, double to, int intervalsCount) throws IOException {
        double h;
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ASUS\\Documents\\error.txt");
        String str;
        double err = 0;
        for (int i=4; i<= intervalsCount; i+=2){
          err =error(method, from, to, i);
            h = getIntervalLength(from, to, i);
            str= String.format("%15.5f \t %15.7f %n", h, err); // 15d -> 15.2f

            fileOutputStream.write(str.getBytes());
        }
        fileOutputStream.close();
    }
    public static double getDefiniteIntegral(double from, double to) {
        return Math.pow(Math.E, to) - Math.pow(to, 4) / 4. + Math.pow(Math.E, from) - Math.pow(from, 4) / 4.;
    }

    public static void main(String[] args) throws IOException {
        double from = 0, to = 4;
        double from2=from;
        int intervalsCount = 50;
        int method=1;
        double intervalLength = getIntervalLength(from, to, intervalsCount);
        double[] funcValues = new double[intervalsCount];;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a method:");
        System.out.println("1. half rectangle method");
        System.out.println("2. left rectangle method");
        System.out.println("3. right rectangle method");
        int choice = scanner.nextInt();
        switch (choice) {
            case (1) :
                method=1;
                from2=from + intervalLength / 2;
                funcValues = halfRectangle(from, to, intervalsCount);
                break;
            case (2) :
                method=2;
                from2=from;
                funcValues = leftRectangle(from, to, intervalsCount);
                break;
            case (3) :
                method=3;
                from2=from + intervalLength;
                funcValues = rightRectangle(from, to, intervalsCount);
                break;
            }

        //  System.out.println(getIntegralValue(leftRectangle(from, to, intervalsCount), from, to));
        //  System.out.println(getIntegralValue(rightRectangle(from, to, intervalsCount), from, to));
        table(from2, intervalsCount, to, funcValues);
        System.out.println(getIntegralValue(funcValues, from, to));
        System.out.println("Error: " + error(method, from, to, intervalsCount));

        System.out.println("Definite integral:");

        System.out.println(getDefiniteIntegral(from, to));
        toFile(from2, intervalsCount, to, funcValues);
        graphOfError(method, from, to, intervalsCount);
    }

    public static double getIntervalLength(double from, double to, int intervalsCount) {
        return (to - from) / intervalsCount;
    }

    public static double[] leftRectangle(double from, double to, int intervalsCount) {
        double[] funcValues = new double[intervalsCount];
        double intervalLength = getIntervalLength(from, to, intervalsCount);
        double x = from;
        for (int i = 0; i < intervalsCount; i++, x += intervalLength) {
            funcValues[i] = Math.pow(Math.E, x) - x * x * x;
        }
        return funcValues;
    }

    public static double[] rightRectangle(double from, double to, int intervalsCount) {
        double[] funcValues = new double[intervalsCount];
        double intervalLength = getIntervalLength(from, to, intervalsCount);
        double x = from + intervalLength;
        for (int i = 0; i < intervalsCount; i++, x += intervalLength) {
            funcValues[i] = Math.pow(Math.E, x) - x * x * x;
        }
        return funcValues;
    }

    public static double[] halfRectangle(double from, double to, int intervalsCount) {
        double[] funcValues = new double[intervalsCount];
        double intervalLength = getIntervalLength(from, to, intervalsCount);
        double x = from + intervalLength / 2;
        for (int i = 0; i < intervalsCount; i++, x += intervalLength) {
            funcValues[i] = Math.pow(Math.E, x) - x * x * x;
        }
        return funcValues;
    }

    public static void table(double from, int intervalsCount, double to, double[] funcValues) {
        double x = from;
        double intervalLength = getIntervalLength(from, to, intervalsCount);
        String format = "|%1$-16s|%2$-16s|\n";
        System.out.format(format, SEPARATOR, SEPARATOR);
        System.out.format(format, "      y(x)      ", "        x       ");
        System.out.format(format, SEPARATOR, SEPARATOR);
        for (int i = 0; i < intervalsCount; i++, x += intervalLength) {
            System.out.format("|%15.7e |%15.2f |%n", funcValues[i], x); // 15d -> 15.2f
            System.out.format(format, SEPARATOR, SEPARATOR);

        }
    }

    public static void toFile(double from, int intervalsCount, double to, double[] funcValues) throws IOException {
        double x = from;
        double intervalLength = getIntervalLength(from, to, intervalsCount);
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ASUS\\Documents\\test.txt");
        String str;
        for (int i = 0; i < intervalsCount; i++, x += intervalLength) {
            str= String.format("%15.2f \t %15.7f %n",x, funcValues[i]); // 15d -> 15.2f

            fileOutputStream.write(str.getBytes());
        }
        fileOutputStream.close();

    }
}
