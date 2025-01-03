package App;


/**
* App/LogInException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Thursday, May 16, 2024 10:07:05 AM CST
*/

public final class LogInException extends org.omg.CORBA.UserException
{
  public String errorMessage = null;

  public LogInException ()
  {
    super(LogInExceptionHelper.id());
  } // ctor

  public LogInException (String _errorMessage)
  {
    super(LogInExceptionHelper.id());
    errorMessage = _errorMessage;
  } // ctor


  public LogInException (String $reason, String _errorMessage)
  {
    super(LogInExceptionHelper.id() + "  " + $reason);
    errorMessage = _errorMessage;
  } // ctor

} // class LogInException
