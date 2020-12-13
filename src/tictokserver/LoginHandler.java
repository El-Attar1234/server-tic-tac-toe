/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictokserver;

public class LoginHandler {
    
    private void handleLogin()
    {
			//هاخد منه email & password لو لقاهم موجودين هيرجعلى ال ID & points
    }
    private void handleRegister()
    {
        // 1- هاروح اتاكد الايميل موجود قبل كدا ولا لا 
		// لو لقيتهخ هابعتله رساله اقوله موجود 
		// لو ملقيتهوش هاسجله عادى واقوله سجل 
    }
    private void handleAskToPlay(int idPlayer1,int idPlayer2)
    {
        // هابعتله ال id بتاع اللاعبين 
		// لو كان اللاعب التانى في جيم هاقوله كدا 
		// لو مكانش في جيم هابعت للاعب التانى 
    }
    private void handleResponseToPlay()
    {
        // لو قبل اللعب هاعمل جيم جديدة واجمع بوينتس الكسبان على الريكورد بتاعه في جدول ال player 
    }
    
    private void newPlayerconnected()
    {
        // دا نعمل ثريد يشتغل كل 30 ثانيه يعمل ابديت للعيبه اللى ظاهرة
    }
    private void handleUpdatePlayerPoints(String jsonStr) {
            // هاعمل ميثود تجيب السكور ولما حد يكسب اجمعه عليهع واروح اضيفه على القديم
    }

    
//    private void handleMessageFromClient(String jsonStr) {
//        String message = JsonOperations.getRequestType(jsonStr);
//        
//        switch (message) {
//            case "login":
//                handleLogin(jsonStr);
//                break;
//
//            case "register":
//                handleRegister(jsonStr);
//                break;
//
//            case "player1 ask to play":
//                handleAskToPlay(jsonStr);
//                break;
//
//            case "player2 respone":
//                handleResponseToPlay(jsonStr);
//                break;
//        }



}