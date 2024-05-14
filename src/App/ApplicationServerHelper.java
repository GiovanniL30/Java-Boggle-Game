package App;


/**
* App/ApplicationServerHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Wednesday, May 15, 2024 5:54:17 AM SGT
*/

abstract public class ApplicationServerHelper
{
  private static String  _id = "IDL:App/ApplicationServer:1.0";

  public static void insert (org.omg.CORBA.Any a, App.ApplicationServer that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static App.ApplicationServer extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (App.ApplicationServerHelper.id (), "ApplicationServer");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static App.ApplicationServer read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_ApplicationServerStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, App.ApplicationServer value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static App.ApplicationServer narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof App.ApplicationServer)
      return (App.ApplicationServer)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      App._ApplicationServerStub stub = new App._ApplicationServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static App.ApplicationServer unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof App.ApplicationServer)
      return (App.ApplicationServer)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      App._ApplicationServerStub stub = new App._ApplicationServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
