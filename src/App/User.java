package App;


/**
* App/User.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Wednesday, May 15, 2024 8:21:39 PM SGT
*/

public final class User implements org.omg.CORBA.portable.IDLEntity
{
  public String userID = null;
  public String firstName = null;
  public String lastName = null;
  public String userName = null;
  public String password = null;
  public int score = (int)0;

  public User ()
  {
  } // ctor

  public User (String _userID, String _firstName, String _lastName, String _userName, String _password, int _score)
  {
    userID = _userID;
    firstName = _firstName;
    lastName = _lastName;
    userName = _userName;
    password = _password;
    score = _score;
  } // ctor

} // class User
