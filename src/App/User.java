package App;


/**
* App/User.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Friday, May 3, 2024 3:12:17 AM SGT
*/

public final class User implements org.omg.CORBA.portable.IDLEntity
{
  public String userID = null;
  public String firstName = null;
  public String lastName = null;
  public String userName = null;
  public String password = null;
  public boolean isLoggedIn = false;
  public String status = null;

  public User ()
  {
  } // ctor

  public User (String _userID, String _firstName, String _lastName, String _userName, String _password, boolean _isLoggedIn, String _status)
  {
    userID = _userID;
    firstName = _firstName;
    lastName = _lastName;
    userName = _userName;
    password = _password;
    isLoggedIn = _isLoggedIn;
    status = _status;
  } // ctor

} // class User
