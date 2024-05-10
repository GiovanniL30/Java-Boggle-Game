package App;


/**
* App/ControllerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Friday, May 10, 2024 7:58:33 PM SGT
*/

public interface ControllerOperations 
{
  void receiveUpdates (App.ClientActions clientActions);
  void sendUpdates (App.ClientActions clientActions);
  void updatePlayerListView ();
  void setWaitingTime (int time);
  void setGameTime (int time);
  void setRound (int round);
  void updatePlayerScore (String id, int newScore);
  void receiveLetter (String[] letters);
  void endGameUpdate (App.User winner, int score);
  void stopIdleTime ();
  void startIdleTime ();
  void setIdleTimeLeft (String message);
  void removeWord (String word);
} // interface ControllerOperations
