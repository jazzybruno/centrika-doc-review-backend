package rw.ac.rca.gradesclassb.utils;

public class DegreesConversions {

    public double fromFahrenheitToCelsius(double fahrenheit){
        return ((fahrenheit  -  32  ) *  5)/9;
        //when fahrenheit =32
        // happy path 89.6Fa we should get 32 cel
    }
    public double fromCelsiusToFahrenheit(double celsius){
        return ((celsius*9)/5)+32;
        //when ce =0
        // happy path 89.6Fa we should get 32 cel
    }
}
