package App;


/**
* App/ApplicationServerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Wednesday, April 24, 2024 7:14:24 PM SGT
*/

public interface ApplicationServerOperations 
{
  App.User login (String userName, String password, App.Controller controller);
  void logout (String userID);
  App.Response createAccount (App.User user);
  App.Response createLobby (App.User creator, App.Controller clientController);
  App.Response joinLobby (App.User user, String lobbyId, App.Controller clientController);
  App.Response leaveLobby (App.User user, String lobbyId);
  App.Lobby[] getLobbies ();
  App.User[] getPlayers (String lobbyId);
  App.Response submitWord (String word, String playerId, String lobbyId);
  void startGame (String lobbyId);
} // interface ApplicationServerOperations
