package App;


/**
* App/LobbyServerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Wednesday, May 15, 2024 8:21:39 PM SGT
*/

public interface LobbyServerOperations 
{
  App.Response createLobby (App.User creator, App.Controller clientController) throws App.LobbyException;
  boolean joinLobby (App.User user, String lobbyId, App.Controller clientController) throws App.LobbyException;
  App.Response leaveLobby (String user, String lobbyId) throws App.LobbyException;
  App.Lobby[] getLobbies ();
  App.Response submitWord (String word, String playerId, String lobbyId);
} // interface LobbyServerOperations
