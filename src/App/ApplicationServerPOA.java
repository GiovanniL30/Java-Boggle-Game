package App;


/**
* App/ApplicationServerPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Sunday, April 21, 2024 12:13:37 PM SGT
*/

public abstract class ApplicationServerPOA extends org.omg.PortableServer.Servant
 implements App.ApplicationServerOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("login", new java.lang.Integer (0));
    _methods.put ("logout", new java.lang.Integer (1));
    _methods.put ("createAccount", new java.lang.Integer (2));
    _methods.put ("createLobby", new java.lang.Integer (3));
    _methods.put ("joinLobby", new java.lang.Integer (4));
    _methods.put ("leaveLobby", new java.lang.Integer (5));
    _methods.put ("getLobbies", new java.lang.Integer (6));
    _methods.put ("getPlayers", new java.lang.Integer (7));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // App/ApplicationServer/login
       {
         String userName = in.read_string ();
         String password = in.read_string ();
         App.Controller controller = App.ControllerHelper.read (in);
         App.User $result = null;
         $result = this.login (userName, password, controller);
         out = $rh.createReply();
         App.UserHelper.write (out, $result);
         break;
       }

       case 1:  // App/ApplicationServer/logout
       {
         String userID = in.read_string ();
         this.logout (userID);
         out = $rh.createReply();
         break;
       }

       case 2:  // App/ApplicationServer/createAccount
       {
         App.User user = App.UserHelper.read (in);
         App.Response $result = null;
         $result = this.createAccount (user);
         out = $rh.createReply();
         App.ResponseHelper.write (out, $result);
         break;
       }

       case 3:  // App/ApplicationServer/createLobby
       {
         App.User creator = App.UserHelper.read (in);
         App.Response $result = null;
         $result = this.createLobby (creator);
         out = $rh.createReply();
         App.ResponseHelper.write (out, $result);
         break;
       }

       case 4:  // App/ApplicationServer/joinLobby
       {
         App.User user = App.UserHelper.read (in);
         String lobbyId = in.read_string ();
         boolean $result = false;
         $result = this.joinLobby (user, lobbyId);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 5:  // App/ApplicationServer/leaveLobby
       {
         App.User user = App.UserHelper.read (in);
         String lobbyId = in.read_string ();
         App.Response $result = null;
         $result = this.leaveLobby (user, lobbyId);
         out = $rh.createReply();
         App.ResponseHelper.write (out, $result);
         break;
       }

       case 6:  // App/ApplicationServer/getLobbies
       {
         App.Lobby $result[] = null;
         $result = this.getLobbies ();
         out = $rh.createReply();
         App.LobbyListHelper.write (out, $result);
         break;
       }

       case 7:  // App/ApplicationServer/getPlayers
       {
         String lobbyId = in.read_string ();
         App.User $result[] = null;
         $result = this.getPlayers (lobbyId);
         out = $rh.createReply();
         App.PlayersHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:App/ApplicationServer:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public ApplicationServer _this() 
  {
    return ApplicationServerHelper.narrow(
    super._this_object());
  }

  public ApplicationServer _this(org.omg.CORBA.ORB orb) 
  {
    return ApplicationServerHelper.narrow(
    super._this_object(orb));
  }


} // class ApplicationServerPOA
