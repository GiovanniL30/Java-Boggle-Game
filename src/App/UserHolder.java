package App;

/**
* App/UserHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Tuesday, 14 May, 2024 8:55:57 PM SGT
*/

public final class UserHolder implements org.omg.CORBA.portable.Streamable
{
  public App.User value = null;

  public UserHolder ()
  {
  }

  public UserHolder (App.User initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = App.UserHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    App.UserHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return App.UserHelper.type ();
  }

}
