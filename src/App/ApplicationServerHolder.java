package App;

/**
* App/ApplicationServerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Saturday, April 20, 2024 10:56:17 PM SGT
*/

public final class ApplicationServerHolder implements org.omg.CORBA.portable.Streamable
{
  public App.ApplicationServer value = null;

  public ApplicationServerHolder ()
  {
  }

  public ApplicationServerHolder (App.ApplicationServer initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = App.ApplicationServerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    App.ApplicationServerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return App.ApplicationServerHelper.type ();
  }

}
