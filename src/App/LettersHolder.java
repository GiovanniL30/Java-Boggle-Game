package App;


/**
* App/LettersHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Monday, May 13, 2024 8:52:54 PM SGT
*/

public final class LettersHolder implements org.omg.CORBA.portable.Streamable
{
  public String value[] = null;

  public LettersHolder ()
  {
  }

  public LettersHolder (String[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = App.LettersHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    App.LettersHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return App.LettersHelper.type ();
  }

}
