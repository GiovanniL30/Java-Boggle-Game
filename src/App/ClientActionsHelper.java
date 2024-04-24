package App;


/**
* App/ClientActionsHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Wednesday, April 24, 2024 1:31:19 PM SGT
*/

abstract public class ClientActionsHelper
{
  private static String  _id = "IDL:App/ClientActions:1.0";

  public static void insert (org.omg.CORBA.Any a, App.ClientActions that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static App.ClientActions extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (App.ClientActionsHelper.id (), "ClientActions", new String[] { "NEW_MESSAGE", "START_GAME"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static App.ClientActions read (org.omg.CORBA.portable.InputStream istream)
  {
    return App.ClientActions.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, App.ClientActions value)
  {
    ostream.write_long (value.value ());
  }

}
