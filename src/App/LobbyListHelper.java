package App;


/**
* App/LobbyListHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl/
* Wednesday, April 24, 2024 7:39:09 PM SGT
*/

abstract public class LobbyListHelper
{
  private static String  _id = "IDL:App/LobbyList:1.0";

  public static void insert (org.omg.CORBA.Any a, App.Lobby[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static App.Lobby[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = App.LobbyHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (App.LobbyListHelper.id (), "LobbyList", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static App.Lobby[] read (org.omg.CORBA.portable.InputStream istream)
  {
    App.Lobby value[] = null;
    int _len0 = istream.read_long ();
    value = new App.Lobby[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = App.LobbyHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, App.Lobby[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      App.LobbyHelper.write (ostream, value[_i0]);
  }

}
