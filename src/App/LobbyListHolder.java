package App;


/**
* App/LobbyListHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Monday, May 6, 2024 9:20:58 AM CST
*/

public final class LobbyListHolder implements org.omg.CORBA.portable.Streamable
{
  public App.Lobby value[] = null;

  public LobbyListHolder ()
  {
  }

  public LobbyListHolder (App.Lobby[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = App.LobbyListHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    App.LobbyListHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return App.LobbyListHelper.type ();
  }

}
