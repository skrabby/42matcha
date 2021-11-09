package fr.intra.util;




import fr.intra.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    public static boolean checkLocation(User user1, User user2) {
        try {
            double x1 = Double.parseDouble(user1.getLatitude());
            double x2 = Double.parseDouble(user2.getLatitude());
            double y1 = Double.parseDouble(user1.getLongitude());
            double y2 = Double.parseDouble(user2.getLongitude());

            double result = Math.sqrt(pow2(x1 - x2) + pow2(y1 - y2));
            if (result * 111.111 < 5) {
                return true;
            }
            return false;
        }
        catch (Exception ex){
            return false;
        }
    }

    private static double pow2(double base){
        return Math.pow(base, 2);
    }

    public static int checkOverlap(User r1, User r2, User user) {
        try {
            List<String> r1Tags = r1.getTags();
            List<String> r2Tags = r2.getTags();
            List<String> result1 = new ArrayList<>();
            List<String> result2 = new ArrayList<>();
            for (int i = 0; i < r1Tags.size(); i++) {
                result1.add(r1Tags.get(i));
            }
            for (int i = 0; i < r2Tags.size(); i++) {
                result2.add(r2Tags.get(i));
            }
            result1.retainAll(user.getTags());
            result2.retainAll(user.getTags());
            return result2.size() - result1.size();
        } catch (Exception ex){
            return 0;
        }
    }
}
