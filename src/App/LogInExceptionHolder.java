package App;

/**
* App/LogInExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Monday, May 6, 2024 8:05:24 AM PST
*/

public final class LogInExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public App.LogInException value = null;

  public LogInExceptionHolder ()
  {
  }

  public LogInExceptionHolder (App.LogInException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = App.LogInExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    App.LogInExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return App.LogInExceptionHelper.type ();
  }

}
