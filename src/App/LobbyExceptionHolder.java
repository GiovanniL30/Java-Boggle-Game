package App;

/**
* App/LobbyExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Friday, May 10, 2024 7:58:33 PM SGT
*/

public final class LobbyExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public App.LobbyException value = null;

  public LobbyExceptionHolder ()
  {
  }

  public LobbyExceptionHolder (App.LobbyException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = App.LobbyExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    App.LobbyExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return App.LobbyExceptionHelper.type ();
  }

}
