package App;


/**
* App/PlayerScore.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Thursday, May 9, 2024 9:41:40 AM CST
*/

public final class PlayerScore implements org.omg.CORBA.portable.IDLEntity
{
  public App.User user = null;
  public int score = (int)0;

  public PlayerScore ()
  {
  } // ctor

  public PlayerScore (App.User _user, int _score)
  {
    user = _user;
    score = _score;
  } // ctor

} // class PlayerScore
