package App;


/**
* App/ControllerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Sunday, April 21, 2024 10:38:12 AM SGT
*/

public interface ControllerOperations 
{
  void receiveUpdates (App.ClientActions clientActions);
  void sendUpdates (App.ClientActions clientActions);
  void updateWaitingLobbyView (App.User user);
} // interface ControllerOperations
