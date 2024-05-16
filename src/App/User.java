package App;


/**
* App/User.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Thursday, May 16, 2024 9:37:16 AM CST
*/

public final class User implements org.omg.CORBA.portable.IDLEntity
{
  public String userID = null;
  public String firstName = null;
  public String lastName = null;
  public String userName = null;
  public String password = null;
  public int score = (int)0;
  public boolean isOnline = false;

  public User ()
  {
  } // ctor

  public User (String _userID, String _firstName, String _lastName, String _userName, String _password, int _score, boolean _isOnline)
  {
    userID = _userID;
    firstName = _firstName;
    lastName = _lastName;
    userName = _userName;
    password = _password;
    score = _score;
    isOnline = _isOnline;
  } // ctor

} // class User
