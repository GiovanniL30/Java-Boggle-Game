package App;


/**
* App/LobbyException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Monday, May 13, 2024 6:44:56 PM AWST
*/

public final class LobbyException extends org.omg.CORBA.UserException
{
  public String errorMessage = null;

  public LobbyException ()
  {
    super(LobbyExceptionHelper.id());
  } // ctor

  public LobbyException (String _errorMessage)
  {
    super(LobbyExceptionHelper.id());
    errorMessage = _errorMessage;
  } // ctor


  public LobbyException (String $reason, String _errorMessage)
  {
    super(LobbyExceptionHelper.id() + "  " + $reason);
    errorMessage = _errorMessage;
  } // ctor

} // class LobbyException
