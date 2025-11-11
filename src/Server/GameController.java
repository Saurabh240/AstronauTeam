package Server;

import datamodels.User;
import screens.mission_card_GUI;

import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

public class GameController {
    private static boolean running = false;

    public static void start() {
        if (!running) {
            running = true;
            System.out.println("Game is now running!");
          Stack<mission_card_GUI> cards= missionCards();



        } else {
            System.out.println("Game already running!");
        }
    }

    public static void stop() {
        running = false;

        System.out.println("Game stopped.");
    }

    public static void cancel(){
        running = false;


        System.out.println("Game is Canceld");
    }


    //geeting all missioncards from the server
    public static Stack<mission_card_GUI> missionCards(){
        Stack<mission_card_GUI>cards =null;

        try {

            Serverconnection serverconnection = Serverconnection.getInstance();
            serverconnection.connect();


            serverconnection.sendMessage("Mission cards");


            cards = (Stack<mission_card_GUI>) serverconnection.readObject();



        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return cards;
    }
    //החזרת השחקנים של סבב נוכחי
    public static User[] getRotaionusers(String[]usernames){

        User[]users = new User[usernames.length];

        try {

            Serverconnection serverconnection = Serverconnection.getInstance();
            serverconnection.connect();

            serverconnection.sendMessage(Arrays.toString(usernames));

            users = (User[]) serverconnection.readObject();


        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public static String[] usernamesconnectied(){

        String[]usernames = null;
        try {

            Serverconnection serverconnection = Serverconnection.getInstance();
            serverconnection.connect();

            serverconnection.sendMessage("USERNAMES");

            usernames = (String[]) serverconnection.readObject();


        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        return usernames;

    }

}