package fr.intra;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

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
}
