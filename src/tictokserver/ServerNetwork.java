package tictokserver;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ServerNetwork {

 public static ServerSocket myServerSocket;
    Socket s;
  
    
    public ServerNetwork() {
         
    try {
            myServerSocket = new ServerSocket(5005);
        } catch (IOException ex) {
            Logger.getLogger(ServerNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        new Thread() {
            @Override
            public void run() {
         
                System.out.println("server conastracror");
                try {
                    while(true){
                       s = myServerSocket.accept();
                    System.out.println("accept client and make new handler");
                    new Handler(s).start();
                    }
                 
                    //مكانها مش هنا
                  // jesonParsing(msg);
                } catch (IOException ex) {
                    stop();
//علشان لو قفلت السرفر
                    Logger.getLogger(ServerNetwork.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();

    }

   
}
 class Handler extends Thread {
        DataInputStream dis;
    PrintStream ps;
    String msg;
 static Vector<Handler> clientsVector
            = new Vector<Handler>();
        

        public Handler(Socket s) {

            try {
               
                dis = new DataInputStream(s.getInputStream());
                ps = new PrintStream(s.getOutputStream());
                clientsVector.add(this);
            
            } catch (IOException ex) {
                Logger.getLogger(ServerNetwork.class.getName()).log(Level.SEVERE, null, ex);
            }
           
                
        }

    @Override
    public void run() {
        while(true){
            try {
                System.out.println("before listen");
                msg = dis.readLine();
                System.out.println(msg);
                System.out.println("after listen");
                jesonParsing(msg);
            } catch (IOException ex) {
                stop();
                Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
            }
                       
                           
                        }
    }
        
         
        
        public String getMessage(){
                return msg;
        }

        public  void sendallOnline() {

            ArrayList<Player> onlineList = DataBaseHandling.getInstance().getOnLineForClient();
            JSONObject jsonObject = new JSONObject();
            JSONArray array=new JSONArray();
            jsonObject.put("TYPE", "ONLINEPLAYERS");
            for (int i = 0; i < onlineList.size(); i++) {
                Map p = new LinkedHashMap(3);
                p.put("NAME", onlineList.get(i).getName());
                p.put("EMAIL", onlineList.get(i).getEmail());
                p.put("SCORE", onlineList.get(i).getScore());
                array.add(p);
            }  
            jsonObject.put("PLAYERS", array);
            String jsonText = JSONValue.toJSONString(jsonObject);
            for(Handler s : clientsVector){
                    s.ps.println(jsonText);
                     s.ps.flush();
                    System.out.println("......."+ jsonText+"\n");
            }
        }
         private void jesonParsing(String msg) {

        try {
            Object obj = new JSONParser().parse(msg);
            JSONObject jo2 = (JSONObject) obj;
            String type = (String) jo2.get("TYPE");

            switch (type) {
                case "LOGIN":
                    String email = (String) jo2.get("EMAIL");
                    String password = (String) jo2.get("PASSWORD");

                    logIN(email, password);
                    break;

                case "REGISTER":
                    String nameR = (String) jo2.get("NAME");
                    String emailR = (String) jo2.get("EMAIL");
                    String passwordR = (String) jo2.get("PASSWORD");

                    regester(nameR, emailR, passwordR);
                    break;

                case "PLAYER_1_ASK_TO_PLAY":
                    //someone ask to play ok / n
                    String EMAIL = (String) jo2.get("EMAIL");
                    String EMAIL_TARGET = (String) jo2.get("EMAIL_TARGET");
                    sendPlayingRequest(EMAIL, EMAIL_TARGET);
                    break;

                case "PLAYER_1_ASK_TO_PLAY_RESPONSE":

                    String EMAIL1 = (String) jo2.get("EMAIL1");
                    String EMAIL_TARGET1 = (String) jo2.get("EMAIL_TARGET");
                    String key = (String) jo2.get("Key");
                    if (key.equals("OK")) {
                        DataBaseHandling.getInstance().creatGame(EMAIL1, EMAIL_TARGET1, true);
                    } else {
                        handleAskToPlayResponse(EMAIL1, EMAIL_TARGET1);
                    }
                    break;
                case "CLIENT_IS_CLOSING":

                    String EMAILc = (String) jo2.get("EMAIL1");
                    handlCancelClient(EMAILc);
                    break;

                case "LIstof player":
                    sendOnline();
                    break;
                case "INITIALIZATION":
                  sendallOnline();
                 //   ps.flush();
                    break;
            }
        } catch (ParseException ex) {
            Logger.getLogger(ServerNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handlCancelClient(String EMAIL1) {
//        new Thread() {
//            @Override
//            public void run() {
                    DataBaseHandling.getInstance().makeClientOffline(EMAIL1);
                    sendallOnline();
//                    dis = new DataInputStream(s.getInputStream());
//                    ps = new PrintStream(s.getOutputStream());
//                    ps.println(onlinelist);
                  //  ps.flush();
//            }
//        }.start();
    }

    public void sendPlayingRequest(String EMAIL1, String EMAIL_TARGET) {
//        new Thread() {
//            @Override
//            public void run() {

                if (DataBaseHandling.getInstance().chechPlayerStatus(EMAIL_TARGET)) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("TYPE", "PLAYER_1_ASK_TO_PLAY");
                    jsonObject.put("EMAIL", EMAIL1);
                    jsonObject.put("EMAIL_TARGET", EMAIL_TARGET);;
                    String jsonText = JSONValue.toJSONString(jsonObject);
 
                        ps.println(jsonText);
                        ps.flush();
                } else {
                    try {
                        ps = new PrintStream("mesage to player 2");
                    } catch (IOException ex) {
                        Logger.getLogger(ServerNetwork.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
//
//            }
//        }.start();
    }

    public void handleAskToPlayResponse(String EMAIL1, String EMAIL_TARGET) {

        //create game or send mesage to say no
//        new Thread() {
//            @Override
//            public void run() {

                if (DataBaseHandling.getInstance().chechPlayerStatus(EMAIL_TARGET)) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("TYPE", "REFUSE");
                    jsonObject.put("EMAIL", EMAIL1);
                    jsonObject.put("EMAIL_TARGET", EMAIL_TARGET);
                    jsonObject.put("KEY", "REFUSE");
                    String jsonText = JSONValue.toJSONString(jsonObject);
                  
                        ps.println(jsonText);
                        ps.flush();


                } else {
                    try {
                        ps = new PrintStream("mesage to player 2");
                    } catch (IOException ex) {
                        Logger.getLogger(ServerNetwork.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

//            }
//        }.start();
    }

    public void regester(String NAME, String EMAIL, String PASSWORD) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                int id = DataBaseHandling.getInstance().signUp(NAME, EMAIL, PASSWORD, 0, false);
                if (id == -1) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("TYPE", "REGISTER_REPLAY");
                    jsonObject.put("KEY", "EMAIL_IS_USED_BEFORE");
                    String jsonText = JSONValue.toJSONString(jsonObject);
                 
                        ps.println(jsonText);
                        ps.flush();
                  
                } else {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("TYPE", "REGISTER_REPLAY");
                    jsonObject.put("KEY", "SUCCESS");
                    String jsonText = JSONValue.toJSONString(jsonObject);
                
                        ps.println(jsonText);
                        ps.flush();
                 

                }
//            }
//        }.start();
    }

    public void logIN(String mail, String password) {
       
                   int a = DataBaseHandling.getInstance().LogIn(mail, password);
                if (a == -1) {
             JSONObject jsonObject = new JSONObject();
                        jsonObject.put("TYPE", "LOGIN_REPLAY");
                        jsonObject.put("KEY", "INCORRECT_PASSWORD");
                        String jsonText = JSONValue.toJSONString(jsonObject);
                      
                        ps.println(jsonText);
                        ps.flush();

                    

                } else if (a == 1) {

                 
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("TYPE", "LOGIN_REPLAY");
                        jsonObject.put("KEY", "SUCCESS");

                        String jsonText = JSONValue.toJSONString(jsonObject);
                        
                        ps.println(jsonText);
                        ps.flush();
                        
                        /// push online list

                        sendallOnline();
                    //    ps.flush();
//                        //HomeServer.onlineText.setText("");
//                        HomeServer.setOnlineText();
//                        //HomeServer.offlineText.setText("");
//                        HomeServer.setOffText();

                   

                }else {

                   
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("TYPE", "LOGIN_REPLAY");
                        jsonObject.put("KEY", "EMAIL_NOT_FOUND");
                        String jsonText = JSONValue.toJSONString(jsonObject);
                      
                        ps.println(jsonText);
                        ps.flush();
                    

                }
          
           
           
    }

   /* public void closeNetwork() {
        try {
            if (s != null) {
                s.close();
            }
            if (myServerSocket != null) {
                myServerSocket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    public String sendOnline() {
        ArrayList<String> jsonArray = new ArrayList<>();
        ArrayList<Player> onlineList = DataBaseHandling.getInstance().getOnLineForClient();

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject.put("TYPE", "ONLINEPLAYERS");

        int counter = 1;
        for (int i = 0; i < onlineList.size(); i++) {
            Map p = new LinkedHashMap(3);

            p.put("NAME", onlineList.get(i).getName());
            p.put("EMAIL", onlineList.get(i).getEmail());
            p.put("SCORE", onlineList.get(i).getScore());

            jsonObject1.put("P" + counter, p);
            counter++;
        }
        jsonObject.put("PLAYER", jsonObject1);
        String jString = JSONValue.toJSONString(jsonObject);
        
        return jString;
    }
    
    public void stopServer(){
    
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("TYPE", "SERVER_IS_CLOSED");
            String jsonText = JSONValue.toJSONString(jsonObject);
        
            ps.println(jsonText);        
            ps.flush();
       
    }

   

    }
