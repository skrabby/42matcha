package fr.intra;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class Utils {

    public static boolean updateEvent(String sql){
        try(Connection pgpool = DriverManager.getConnection("jdbc:postgresql://localhost:5432/userdb?user=user&password=q1w2e3r4")){
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

    public static boolean selectEventBytes(String sql){
        try(Connection pgpool = DriverManager.getConnection("jdbc:postgresql://localhost:5432/userdb?user=user&password=q1w2e3r4")){
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
