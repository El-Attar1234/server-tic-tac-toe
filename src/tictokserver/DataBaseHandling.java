package tictokserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.parser.JSONParser;

import org.apache.derby.jdbc.ClientDriver;

public class DataBaseHandling {

    private static DataBaseHandling instance;
    private static Connection connection;
    private static Statement stmt;

    public DataBaseHandling() {

        try {
            DriverManager.registerDriver(new ClientDriver());
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/TikTokGame", "group1", "group1");
            stmt = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DataBaseHandling getInstance() {
        if (instance == null) {
            instance = new DataBaseHandling();
        }

        return instance;
    }

    public boolean chechMail(String EMAIL) {
        try {
            PreparedStatement s = connection.prepareStatement("SELECT * FROM PLAYER WHERE EMAIL= ?");
            s.setString(1, EMAIL);
            ResultSet resultSet = s.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int signUp(String NAME, String EMAIL, String PASSWORD, int POINTS, boolean STATUS) {
        ///     register///
        // email is alreadu found  -1
        //  register if succed     1
        if (!chechMail(EMAIL)) {

            int id = 0;
            int i;
            try {
                PreparedStatement s = connection.prepareStatement("SELECT * FROM PLAYER");
                ResultSet resultSet = s.executeQuery();
                while (resultSet.next()) {
                    id++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("..............." + (id++));
            try {
                PreparedStatement pst = connection.prepareStatement("INSERT INTO PLAYER ( ID,NAME, EMAIL,PASSWORD,POINTS,STATUS) VALUES(?,?,?,?,?,?)");
                pst.setInt(1, id);
                pst.setString(2, NAME);
                pst.setString(3, EMAIL);
                pst.setString(4, PASSWORD);
                pst.setInt(5, POINTS);
                pst.setBoolean(6, STATUS);
                try {
                    i = pst.executeUpdate();
                    //update Gui
                    HomeServer.offlineText.setText("");
                    HomeServer.setOffText();

                    return i;
                } catch (SQLIntegrityConstraintViolationException e) {
                    Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, e);
                }

            } catch (SQLException ex) {
                Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return -1;
    }

    public int LogIn(String mail, String password) {
        int login = -1;

        try {

            PreparedStatement s = connection.prepareStatement("SELECT * FROM PLAYER WHERE EMAIL = ? AND PASSWORD = ?");
            s.setString(1, mail);
            s.setString(2, password);
            ResultSet resultSet = s.executeQuery();
            if (resultSet.next()) {
                PreparedStatement s1 = connection.prepareStatement("UPDATE PLAYER SET STATUS = ? WHERE EMAIL = ?");
                s1.setString(2, mail);
                s1.setBoolean(1, true);
                
                s1.executeUpdate();
                                
                login = 1;
            } else if (chechMail(mail)) {
                login = 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
        return login;
    }

    //return -1 if not added others if add
    public int creatGame(String PLAYER1_EMAIL, String PLAYER2_EMAIL, boolean STATUS) {
        int i = -1;
        try {

            int id = 0;
            PreparedStatement s = connection.prepareStatement("SELECT * FROM GAME");
            ResultSet resultSet = s.executeQuery();
            while (resultSet.next()) {
                id++;
            }
            PreparedStatement pst = connection.prepareStatement("INSERT INTO GAME ( ID,PLAYER1_EMAIL,PLAYER2_EMAIL,STATUS) VALUES(?,?,?,?)");
            pst.setInt(1, id);
            pst.setString(2, PLAYER1_EMAIL);
            pst.setString(3, PLAYER2_EMAIL);
            pst.setBoolean(4, STATUS);
            i = pst.executeUpdate();
            return i;
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    public void updateGame(int id, boolean STATUS) {
        try {
            PreparedStatement s = connection.prepareStatement("UPDATE Game SET STATUS=? WHERE ID= ?");
            s.setInt(1, id);
            s.setBoolean(2, STATUS);
            s.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // return status of  player false if offline true if online
    public boolean chechPlayerStatus(String player1email) {

        try {

            PreparedStatement s = connection.prepareStatement("SELECT STATUS FROM PLAYER WHERE EMAIL= ?");
            s.setString(1, player1email);
            ResultSet resultSet = s.executeQuery();
            while (resultSet.next()) {

                return resultSet.getBoolean(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    
    public void makeClientOffline(String EMAIL){
    
        try {
            PreparedStatement s = connection.prepareStatement("UPDATE PLAYER SET STATUS = ? WHERE EMAIL = ?");
            s.setBoolean(1, false);
            s.setString(2, EMAIL);
            s.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    }
    
    public void stopServerOfflineAll(){
        try {
            PreparedStatement s = connection.prepareStatement("UPDATE PLAYER SET STATUS = FALSE");
            
            s.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    public ArrayList<String> getOnLine() {
        ArrayList<String> arr = new ArrayList<>();
        try {
            PreparedStatement s = connection.prepareStatement("select NAME, POINTS from PLAYER WHERE STATUS = true");
            ResultSet rs = s.executeQuery();

            while (rs.next() == true) {
                String line = rs.getString(1) + "   [Score = " + rs.getString(2) + "]";

                arr.add(line);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;
    }

    public ArrayList<String> getOffLine() {
        ArrayList<String> arr = new ArrayList<>();
        try {
            PreparedStatement s = connection.prepareStatement("select NAME, POINTS from PLAYER WHERE STATUS = false");
            ResultSet rs = s.executeQuery();

            while (rs.next() == true) {
                String line = rs.getString(1) + "    " + rs.getString(2);

                arr.add(line);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;
    }

    public ArrayList<String> getOnGaming() {

        ArrayList<String> arr = new ArrayList<>();
        try {
            PreparedStatement s = connection.prepareStatement("select PLAYER1_EMAIL, PLAYER2_EMAIL from GAME WHERE STATUS = true");
            ResultSet rs = s.executeQuery();

            while (rs.next() == true) {
                String line = rs.getString(1) + "  Vs  " + rs.getString(2);

                arr.add(line);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;
    }

    public void clossingDataBase() {
        try {
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public ArrayList<Player> getOnLineForClient() {
        ArrayList<Player> arr = new ArrayList<>();
        try {
            PreparedStatement s = connection.prepareStatement("select NAME, POINTS,EMAIL from PLAYER WHERE STATUS = true");
            ResultSet rs = s.executeQuery();

            while (rs.next() == true) {
                String NAME = rs.getString(1);
                int POINTS=rs.getInt(2);
                String EMAIL=rs.getString(3);

                arr.add(new Player(NAME,EMAIL,POINTS));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;
    }
}
