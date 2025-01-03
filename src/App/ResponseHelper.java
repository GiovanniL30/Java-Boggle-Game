package App;


/**
* App/ResponseHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Thursday, May 16, 2024 10:07:05 AM CST
*/

abstract public class ResponseHelper
{
  private static String  _id = "IDL:App/Response:1.0";

  public static void insert (org.omg.CORBA.Any a, App.Response that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static App.Response extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_any);
          _members0[0] = new org.omg.CORBA.StructMember (
            "payload",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_boolean);
          _members0[1] = new org.omg.CORBA.StructMember (
            "isSuccess",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (App.ResponseHelper.id (), "Response", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static App.Response read (org.omg.CORBA.portable.InputStream istream)
  {
    App.Response value = new App.Response ();
    value.payload = istream.read_any ();
    value.isSuccess = istream.read_boolean ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, App.Response value)
  {
    ostream.write_any (value.payload);
    ostream.write_boolean (value.isSuccess);
  }

}
