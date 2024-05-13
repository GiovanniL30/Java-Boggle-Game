package App;


/**
* App/PlayersHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Monday, May 13, 2024 9:34:31 PM SGT
*/

abstract public class PlayersHelper
{
  private static String  _id = "IDL:App/Players:1.0";

  public static void insert (org.omg.CORBA.Any a, App.User[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static App.User[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = App.UserHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (App.PlayersHelper.id (), "Players", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static App.User[] read (org.omg.CORBA.portable.InputStream istream)
  {
    App.User value[] = null;
    int _len0 = istream.read_long ();
    value = new App.User[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = App.UserHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, App.User[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      App.UserHelper.write (ostream, value[_i0]);
  }

}
