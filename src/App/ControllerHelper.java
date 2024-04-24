package App;


/**
* App/ControllerHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Wednesday, April 24, 2024 1:31:19 PM SGT
*/

abstract public class ControllerHelper
{
  private static String  _id = "IDL:App/Controller:1.0";

  public static void insert (org.omg.CORBA.Any a, App.Controller that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static App.Controller extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (App.ControllerHelper.id (), "Controller");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static App.Controller read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_ControllerStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, App.Controller value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static App.Controller narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof App.Controller)
      return (App.Controller)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      App._ControllerStub stub = new App._ControllerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static App.Controller unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof App.Controller)
      return (App.Controller)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      App._ControllerStub stub = new App._ControllerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
