package App;


/**
* App/ApplicationServerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Sunday, April 21, 2024 10:38:12 AM SGT
*/

public interface ApplicationServerOperations 
{
  App.User login (String userName, String password, App.Controller controller);
  void logout (String userID);
  App.Response createAccount (App.User user);
  App.Response createLobby (App.User creator);
  App.Response joinLobby (App.User user, String lobbyId);
  App.Response leaveLobby (App.User user, String lobbyId);
  App.Lobby[] getLobbies ();
} // interface ApplicationServerOperations
