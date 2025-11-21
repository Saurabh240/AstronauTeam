package database;

import datamodels.*;
import screens.mission_card_GUI;

import javax.net.ssl.SSLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/** מחלקה שמייצגת את הבסיס הנתונים הראשי של המשחק , מחוברת לבסיס הנתונים של הראשי של המשחק , מולו נעשת כל הפונקציונליות של נתונים : משיכת נתונים, העלאת נתונים
 *
 */


public class Database {

    private static Connection con;
    private static Statement stmt;
    private static String queryStr;
    private static String queryStr1;
    private static ResultSet rs;
    private static ResultSet rs2;
    private static  boolean active =false;


    //connect to database
    public static   void connect(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myapp?allowPublicKeyRetrieval=true&useSSL=false", username, password);

        } catch (SQLException | ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

       public Database(String username,String passeword){
              connect(username,passeword);
       }



    //פונקצייה להחזרת כל כרטיסי המשימה
    public static Stack<mission_card_GUI> displaycards() {
        Stack<mission_card_GUI>taskcards = new Stack<>();

        try {
            stmt = con.createStatement();
            queryStr = "Select * from mission_cards";
            rs = stmt.executeQuery(queryStr);
            while (rs.next()) {
               String Subject= rs.getString(2);

               String destanationplanet = rs.getString(3);

               int oxygenunits = rs.getInt(4);

               String instruments = rs.getString(5);

               String[]ins = instruments.split(",");

               String taskcode = rs.getString(6);

                mission_card_GUI taskcard = new mission_card_GUI(Subject,destanationplanet,oxygenunits,ins,taskcode);
               taskcards.push(taskcard);
            }


        } catch (SQLException e) {
            System.err.println("Error in implementing the qeury");

            e.printStackTrace();
        }

        return taskcards;
    }

    //פונקצייה להחזרת עצם לוח משחק עם אינקס נתון

    public gameboard getgameboard(int index){
        gameboard gameboard = null;
        try {
            stmt = con.createStatement();
            queryStr = "Select * from gameboard";
            rs = stmt.executeQuery(queryStr);

            while (rs.next()){

                int Index = rs.getInt(1);

                String name =rs.getString(2);

                String level = rs.getString(3);

                if(Index == index){

                    gameboard  = new gameboard(index, name,level);
                    break;
                }

            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return gameboard;
    }

    //מחזיר את המישתמשים שהשמות מישתמשים שלהם היתקבלו כפרמטר
    public ArrayList<User> getUsers(String[]usernames){

        ArrayList<User>users = new ArrayList<User>();

        try {

         for (int i =0; i < usernames.length; i++) {

             String current = usernames[i];

             stmt = con.createStatement();
             queryStr = "Select * from users";
             rs = stmt.executeQuery(queryStr);

             while (rs.next()) {

                  int userid = rs.getInt(0);

                  String username = rs.getString(1);


                 String password = rs.getString(2);

                 String firstname = rs.getString(3);

                 String lastname = rs.getString(4);

                 String role = rs.getString(5);

                 if(username.equals(current)){

                     User user = new User(username,password,firstname,lastname,role);

                     users.add(user);
                 }
             }
         }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }



    //פונקצייה להחזרת כל כרטיסי משימה ללוח משחק הלוגי;
    private taskcard[] missions(){
        int count =0;
        taskcard[]taskcards =null;
        try {
            stmt = con.createStatement();
            queryStr = "Select * from mission_cards";
            rs = stmt.executeQuery(queryStr);
            while (rs.next()){
                String Subject= rs.getString(2);
                String destanationplanet = rs.getString(3);
                int oxygenunits = rs.getInt(4);
                String instruments = rs.getString(5);
                String[]ins = instruments.split(",");
                String taskcode = rs.getString(6);
                count++;
            }
            taskcards = new taskcard[count];
            count =0;
            while (rs.next()) {
                String Subject = rs.getString(2);
                String destanationplanet = rs.getString(3);
                int oxygenunits = rs.getInt(4);
                String instruments = rs.getString(5);
                String[] ins = instruments.split(",");
                String taskcode = rs.getString("6");
                    taskcard taskcard = new taskcard(Subject,destanationplanet,oxygenunits,ins,taskcode);
                    taskcards[count++]=  taskcard;
                }



        }catch (SQLException e){
            e.printStackTrace();
        }
       return taskcards;
    }




    //   פונקצייה שמקבלת פרמטר של שם משתמש  של שחקן נוכחי ומחזירה אובייקט  המייצג את המישתמש של השחקן הנוכחי שהיתקבל כפרמטר
    public static User current(String username){
        User user = null;


        try {
            stmt = con.createStatement();
            queryStr = "Select * from users";
            rs = stmt.executeQuery(queryStr);

            while (rs.next()){
                int userid = rs.getInt(1);
                String firstname = rs.getString(4);
                String lastname =  rs.getString(5);
                String Username = rs.getString(2);
                String role = rs.getString(6);
                if(Username.equals(username)){
                    user = new User(userid,firstname,lastname,role);
                    break;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    //פונקצייה להיתחברות
    public static boolean login_user(String username,String password){
        boolean isfound = false;
        try {
            stmt = con.createStatement();
            queryStr = "Select * from users";
            rs = stmt.executeQuery(queryStr);
            while (rs.next()) {

            String Username = rs.getString(2);
            String Password = rs.getString(3);
             if(Username.equals(username) && Password.equals(password)){
                 isfound = true;
                 break;

             }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }



        return isfound;
    }

    //פונקצייה לרישום משתמש חדש
    public static boolean registeruser(User user) {
        int rowsAffected1 =0;


        String username = user.getUsername();
        String password = user.getPassword();
        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        String role = user.getRole();


        queryStr = "INSERT INTO users (userid,username,password,firstname,lasstname,role) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(queryStr)) {
            ps.setInt(1, generateauserid());
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, firstname);
            ps.setString(5, lastname);
            ps.setString(6, role);

            rowsAffected1 = ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
       return  rowsAffected1 >0;
    }

     //פונקצייה ליצירת מזהה משתמש
    private static int generateauserid(){
        int min = 2;
        int max = 9999;
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    //פונקצייה לחסימת מישתמש מלהיתחבר במשך 10 דקות
    public void blockuser(){

        for(int i  =0; i < 600; i++){

            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    //פונקצייה לשחרור משתמש חסום
    public void activeblocking(){
        blockuser();
    }

    //פונקצייה לבדיקה האם המישתמש חסום
    public  boolean isActive(){
        return active;
    }




    //פונקצייה לאיפוס סיסמא
    public static boolean resetPassword(String username, String password){
        queryStr1 = "UPDATE users SET password = ? WHERE username =?";
        try(PreparedStatement ps = con.prepareStatement(queryStr1)) {
            ps.setString(1, password);
            ps.setString(2, username);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //מחזיר את שמות המישתמשים שמחוברים כרגע
    public static String[] whoisconnectied(){
        int count =0;
        int count1 =0;
        String[]connectiedusers = null;

        try {
            stmt = con.createStatement();
            queryStr = "SELECT *  FROM users WHERE isconnectied = 1 ";
            queryStr1 = "SELECT *  FROM users WHERE isconnectied = 1 ";
            rs = stmt.executeQuery(queryStr);
            rs2 = stmt.executeQuery(queryStr1);
            while (rs.next()){

                String username = rs.getString(2);
                int isConnectied = rs.getInt(7);

                if(isConnectied == 1){
                    count++;
                }
            }
            connectiedusers = new String[count];
            while (rs2.next()){

                String username = rs.getString(2);
                int isConnectied = rs.getInt(7);

                if(isConnectied == 1){
                    connectiedusers[count1++] = username;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return connectiedusers;
    }


    // disconnect
    public static void disConnect() {

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}









