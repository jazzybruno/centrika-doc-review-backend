package rw.ac.rca.gradesclassb.utils;

import java.time.DayOfWeek;

public class SchoolMenu {

    public static String getMenuByDayOfWeek(DayOfWeek dayOfWeek){
        switch (dayOfWeek){
            case SUNDAY -> {
                return "Maize";
            }
            case MONDAY -> {
                return "Potato";
            }
            case TUESDAY -> {
                return "Snacks";
            }
            case WEDNESDAY -> {
                return "Meat";
            }
            case THURSDAY -> {
                return "Rice";
            }
            case FRIDAY -> {
                return "Beans";
            }
            default -> {
                return "Invalid";
            }
        }
    }
}
