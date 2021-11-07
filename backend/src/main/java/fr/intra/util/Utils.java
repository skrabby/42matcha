package fr.intra.util;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static boolean updateEvent(String sql){
        try(Connection pgpool = DriverManager.getConnection("jdbc:postgresql://localhost:5432/matcha?user=user&password=q1w2e3r4")){
            Statement statement = pgpool.createStatement();
            int updateSuccess = statement.executeUpdate(sql);
            if (updateSuccess == 1) {
                return true;
            }
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    public static ResultSet selectEvent(String sql){
        ResultSet resultSet;
        try(Connection pgpool = DriverManager.getConnection("jdbc:postgresql://localhost:5432/matcha?user=user&password=q1w2e3r4")){
            Statement statement = pgpool.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return resultSet;
    }

    public static boolean checkAge(String date1, String date2){
        try {
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            Date firstDate = parser.parse(date1);
            Date secondDate = parser.parse(date2);

            // 3 years
            if(Math.abs(firstDate.getTime()-secondDate.getTime()) > (94608000000L))
                return false;
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
}
