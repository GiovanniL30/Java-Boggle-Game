package App;


/**
* App/GamePlayer.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Thursday, May 16, 2024 9:37:16 AM CST
*/

public final class GamePlayer implements org.omg.CORBA.portable.IDLEntity
{
  public App.User user = null;
  public int score = (int)0;

  public GamePlayer ()
  {
  } // ctor

  public GamePlayer (App.User _user, int _score)
  {
    user = _user;
    score = _score;
  } // ctor

} // class GamePlayer
