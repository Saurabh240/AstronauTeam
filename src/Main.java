import screens.LoginScreenGrid_GUI;
import screens.Managment_system_main_screen_GUI;

import javax.swing.*;

public class Main {


    public static void main(String[]args){

        Managment_system_main_screen_GUI managmentSystemMainScreenGui = new Managment_system_main_screen_GUI("127.0.0.1",9999);
        LoginScreenGrid_GUI login_screen = new LoginScreenGrid_GUI("AstronauTeam",managmentSystemMainScreenGui);
        login_screen.setVisible(true);
        login_screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);






    }
}
