package App;


/**
* App/PlayersHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Wednesday, April 24, 2024 6:22:54 PM SGT
*/

public final class PlayersHolder implements org.omg.CORBA.portable.Streamable
{
  public App.User value[] = null;

  public PlayersHolder ()
  {
  }

  public PlayersHolder (App.User[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = App.PlayersHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    App.PlayersHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return App.PlayersHelper.type ();
  }

}
