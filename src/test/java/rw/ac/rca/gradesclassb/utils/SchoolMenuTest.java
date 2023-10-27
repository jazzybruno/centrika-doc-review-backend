package rw.ac.rca.gradesclassb.utils;

import org.junit.jupiter.api.Test;

import javax.xml.validation.Schema;
import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SchoolMenuTest {
    @Test
    void whenSunday_schoolMenu_shouldbeMaize(){
        assertEquals(SchoolMenu.getMenuByDayOfWeek(DayOfWeek.SUNDAY),"Maize");
    }
}
