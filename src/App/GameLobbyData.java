package App;


/**
* App/GameLobbyData.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Monday, April 22, 2024 9:10:50 PM SGT
*/

public final class GameLobbyData implements org.omg.CORBA.portable.IDLEntity
{
  public int waitingTime = (int)0;
  public int gameTime = (int)0;

  public GameLobbyData ()
  {
  } // ctor

  public GameLobbyData (int _waitingTime, int _gameTime)
  {
    waitingTime = _waitingTime;
    gameTime = _gameTime;
  } // ctor

} // class GameLobbyData