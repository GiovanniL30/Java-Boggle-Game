package App;

/**
* App/ControllerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Sunday, April 21, 2024 8:32:06 AM SGT
*/

public final class ControllerHolder implements org.omg.CORBA.portable.Streamable
{
  public App.Controller value = null;

  public ControllerHolder ()
  {
  }

  public ControllerHolder (App.Controller initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = App.ControllerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    App.ControllerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return App.ControllerHelper.type ();
  }

}