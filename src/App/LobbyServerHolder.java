package App;

/**
* App/LobbyServerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Monday, May 13, 2024 8:52:54 PM SGT
*/

public final class LobbyServerHolder implements org.omg.CORBA.portable.Streamable
{
  public App.LobbyServer value = null;

  public LobbyServerHolder ()
  {
  }

  public LobbyServerHolder (App.LobbyServer initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = App.LobbyServerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    App.LobbyServerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return App.LobbyServerHelper.type ();
  }

}
