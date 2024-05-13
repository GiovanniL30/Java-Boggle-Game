package App;


/**
* App/LobbyServerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Monday, May 13, 2024 6:44:56 PM AWST
*/

public interface LobbyServerOperations 
{
  App.Response createLobby (App.User creator, App.Controller clientController) throws App.LobbyException;
  boolean joinLobby (App.User user, String lobbyId, App.Controller clientController) throws App.LobbyException;
  App.Response leaveLobby (String user, String lobbyId) throws App.LobbyException;
  App.Lobby[] getLobbies ();
  App.Response submitWord (String word, String playerId, String lobbyId);
} // interface LobbyServerOperations
