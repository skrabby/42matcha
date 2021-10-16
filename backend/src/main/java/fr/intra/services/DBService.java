package fr.intra.services;

import fr.intra.Utils;
import fr.intra.user.User;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Service
public class DBService {

    public DBService() {
        initDBOfClientsGeneral();
        initDBOfGeneralTags();
        initDBOfClientsTags();
    }

    @Data
    public static class DataBase{

        private final String login;
        private final String pass;
        private final String url;

        public DataBase(String login, String pass, String url){
            this.login = login;
            this.url = url;
            this.pass = pass;
        }

//        public DataBase(String login){ //todo читать из конфига
//        }
    }

    private void initDBOfClientsGeneral(){
        try(Connection pgpool = DriverManager.getConnection("jdbc:postgresql://localhost:5432/userdb?user=user&password=q1w2e3r4")) {
            //Create general table
            Statement statement = pgpool.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS USERS(" +
                    "ID serial primary key," +
                    "NAME VARCHAR NOT NULL," +
                    "EMAIL VARCHAR UNIQUE,"   +
                    "SEXPREF VARCHAR DEFAULT ''," +
                    "BIOGRAPHY TEXT DEFAULT ''," +
                    "GENDER VARCHAR DEFAULT ''," +
                    "PICT1 bytea DEFAULT '\\000'," +
                    "PICT2 bytea DEFAULT '\\000'," +
                    "PICT3 bytea DEFAULT '\\000'," +
                    "PICT4 bytea DEFAULT '\\000'," +
                    "PICT5 bytea DEFAULT '\\000');");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void initDBOfGeneralTags(){
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/tags.conf"));
            ArrayList<String> lines = new ArrayList<String>();

            while (scanner.hasNextLine())
                lines.add(scanner.nextLine());

            try(Connection pgpool = DriverManager.getConnection("jdbc:postgresql://localhost:5432/userdb?user=user&password=q1w2e3r4")) {
                //Create TAG table
                Statement statement = pgpool.createStatement();
                statement.execute("create table if not exists TAGS(ID serial primary key, TAG VARCHAR UNIQUE);");

                //Generate sqlRequest to insert tags from conf
                String insertRequest = "INSERT INTO TAGS(TAG) VALUES ";
                for (int i = 0; i < lines.size()  - 1; i++) {
                    insertRequest += "('" + lines.get(i) + "'),";
                }
                insertRequest += "('" + lines.get(lines.size() -  1) + "');";
                try {
                    statement.executeUpdate(insertRequest);
                }catch (Exception excep){
                    excep.printStackTrace();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void initDBOfClientsTags() {
        try(Connection pgpool = DriverManager.getConnection("jdbc:postgresql://localhost:5432/userdb?user=user&password=q1w2e3r4")) {
            //Create Users_TAGS table
            Statement statement = pgpool.createStatement();
            statement.execute("create table if not exists USERS_TAGS(\n" +
                    "ID serial primary key, \n" +
                    "TAG_ID integer references tags (id) ON DELETE cascade, \n" +
                    "USER_ID integer references users (id)ON DELETE CASCADE);");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static boolean createClient(String name, String email){

        try(Connection pgpool = DriverManager.getConnection("jdbc:postgresql://localhost:5432/userdb?user=user&password=q1w2e3r4")){
            Statement statement = pgpool.createStatement();
            int updateSuccess = statement.executeUpdate("INSERT INTO USERS(NAME, EMAIL) VALUES " +
                    "('"+ name +"','"+ email +"')");
            if (updateSuccess == 1) {
                return true;
            }
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    public static boolean updateClient(User user, String id) {
        String updateRequest;
        updateRequest = "update users ";
        updateRequest += String.format("set " +
                "name = '%s', " +
                "gender = '%s'," +
                "sexpref = '%s'," +
                "biography = '%s' ",
                user.getName(),
                user.getGender(),
                user.getSexpref(),
                user.getBiography());
        updateRequest += "where id = " + id;

        return Utils.updateEvent(updateRequest);
    }
}
