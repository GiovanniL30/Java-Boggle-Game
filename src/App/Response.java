package App;


/**
* App/Response.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Sunday, April 21, 2024 12:13:37 PM SGT
*/

public final class Response implements org.omg.CORBA.portable.IDLEntity
{
  public org.omg.CORBA.Any payload = null;
  public boolean isSuccess = false;

  public Response ()
  {
  } // ctor

  public Response (org.omg.CORBA.Any _payload, boolean _isSuccess)
  {
    payload = _payload;
    isSuccess = _isSuccess;
  } // ctor

} // class Response
