package App;

/**
* App/LobbyHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Sunday, April 21, 2024 12:13:37 PM SGT
*/

public final class LobbyHolder implements org.omg.CORBA.portable.Streamable
{
  public App.Lobby value = null;

  public LobbyHolder ()
  {
  }

  public LobbyHolder (App.Lobby initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = App.LobbyHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    App.LobbyHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return App.LobbyHelper.type ();
  }

}
