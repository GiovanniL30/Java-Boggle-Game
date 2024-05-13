package App;


/**
* App/ApplicationServerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Monday, May 13, 2024 9:34:31 PM SGT
*/

public interface ApplicationServerOperations 
{
  App.User login (String userName, String password, App.Controller controller) throws App.LogInException;
  void logout (String userID);
  App.Response createAccount (App.User user) throws App.CreateException;
  App.Response createLobby (App.User creator, App.Controller clientController) throws App.LobbyException;
  App.Response joinLobby (App.User user, String lobbyId, App.Controller clientController) throws App.LobbyException;
  App.Response leaveLobby (String user, String lobbyId) throws App.LobbyException;
  App.Lobby[] getLobbies ();
  App.User[] getPlayers (String lobbyId);
  App.Response submitWord (String word, String playerId, String lobbyId);
  void startGame (String lobbyId);
  App.User[] getAllUsers ();
  void getTime (int time);
  void updateGameTime (int time);
  void updateWaitingTime (int time);
  App.Response banUser (String userId);
  App.Response unBanUser (String userId);
  App.Response deleteUserAccount (String userId);
} // interface ApplicationServerOperations
