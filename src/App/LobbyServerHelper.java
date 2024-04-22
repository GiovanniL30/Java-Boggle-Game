package App;


/**
* App/LobbyServerHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Monday, April 22, 2024 9:20:41 PM SGT
*/

abstract public class LobbyServerHelper
{
  private static String  _id = "IDL:App/LobbyServer:1.0";

  public static void insert (org.omg.CORBA.Any a, App.LobbyServer that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static App.LobbyServer extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (App.LobbyServerHelper.id (), "LobbyServer");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static App.LobbyServer read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_LobbyServerStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, App.LobbyServer value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static App.LobbyServer narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof App.LobbyServer)
      return (App.LobbyServer)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      App._LobbyServerStub stub = new App._LobbyServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static App.LobbyServer unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof App.LobbyServer)
      return (App.LobbyServer)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      App._LobbyServerStub stub = new App._LobbyServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}