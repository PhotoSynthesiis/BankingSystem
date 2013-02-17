import java.sql.Date;

public class test {
    public static void main(String[] args) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        java.util.Date utilDate = cal.getTime();
        java.sql.Date sqlDate = new Date(utilDate.getTime());
        System.out.println(sqlDate);
    }
}
