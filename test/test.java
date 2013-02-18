import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Years;

import java.sql.Date;

public class test {
    public static void main(String[] args) {
        Date date1 = new Date(Date.valueOf("1989-12-25").getTime());
        Date date2 = new Date(Date.valueOf("1992-11-25").getTime());
        System.out.println(DateTime.now().toLocalDate());
        System.out.println(Years.yearsBetween(new DateTime(date1), new DateTime(date2)).getYears());
    }
}
