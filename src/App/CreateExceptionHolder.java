package App;

/**
* App/CreateExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Thursday, May 16, 2024 10:07:05 AM CST
*/

public final class CreateExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public App.CreateException value = null;

  public CreateExceptionHolder ()
  {
  }

  public CreateExceptionHolder (App.CreateException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = App.CreateExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    App.CreateExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return App.CreateExceptionHelper.type ();
  }

}
