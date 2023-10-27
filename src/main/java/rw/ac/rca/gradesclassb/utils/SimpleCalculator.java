package rw.ac.rca.gradesclassb.utils;

public class SimpleCalculator {
    public static Integer add(int num1, int num2){
        return num1+num2;
    }
    public static double divide(int num1, int num2){
        if(num2==0) throw new IllegalArgumentException("Divisor can't be zero");
        return num1/num2;
    }
    public static double multiply(int num1, int num2){
        return num1 * num2;
    }
}
