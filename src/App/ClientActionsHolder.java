package App;

/**
* App/ClientActionsHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Saturday, April 20, 2024 10:56:17 PM SGT
*/

public final class ClientActionsHolder implements org.omg.CORBA.portable.Streamable
{
  public App.ClientActions value = null;

  public ClientActionsHolder ()
  {
  }

  public ClientActionsHolder (App.ClientActions initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = App.ClientActionsHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    App.ClientActionsHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return App.ClientActionsHelper.type ();
  }

}
