package App;

/**
* App/ResponseHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Sunday, April 21, 2024 8:32:06 AM SGT
*/

public final class ResponseHolder implements org.omg.CORBA.portable.Streamable
{
  public App.Response value = null;

  public ResponseHolder ()
  {
  }

  public ResponseHolder (App.Response initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = App.ResponseHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    App.ResponseHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return App.ResponseHelper.type ();
  }

}