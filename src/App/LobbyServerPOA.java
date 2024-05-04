package App;


/**
* App/LobbyServerPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Saturday, May 4, 2024 8:45:32 PM SGT
*/

public abstract class LobbyServerPOA extends org.omg.PortableServer.Servant
 implements App.LobbyServerOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("createLobby", new java.lang.Integer (0));
    _methods.put ("joinLobby", new java.lang.Integer (1));
    _methods.put ("leaveLobby", new java.lang.Integer (2));
    _methods.put ("getLobbies", new java.lang.Integer (3));
    _methods.put ("submitWord", new java.lang.Integer (4));
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
       case 0:  // App/LobbyServer/createLobby
       {
         App.User creator = App.UserHelper.read (in);
         App.Controller clientController = App.ControllerHelper.read (in);
         App.Response $result = null;
         $result = this.createLobby (creator, clientController);
         out = $rh.createReply();
         App.ResponseHelper.write (out, $result);
         break;
       }

       case 1:  // App/LobbyServer/joinLobby
       {
         App.User user = App.UserHelper.read (in);
         String lobbyId = in.read_string ();
         App.Controller clientController = App.ControllerHelper.read (in);
         boolean $result = false;
         $result = this.joinLobby (user, lobbyId, clientController);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 2:  // App/LobbyServer/leaveLobby
       {
         String user = in.read_string ();
         String lobbyId = in.read_string ();
         App.Response $result = null;
         $result = this.leaveLobby (user, lobbyId);
         out = $rh.createReply();
         App.ResponseHelper.write (out, $result);
         break;
       }

       case 3:  // App/LobbyServer/getLobbies
       {
         App.Lobby $result[] = null;
         $result = this.getLobbies ();
         out = $rh.createReply();
         App.LobbyListHelper.write (out, $result);
         break;
       }

       case 4:  // App/LobbyServer/submitWord
       {
         String word = in.read_string ();
         String playerId = in.read_string ();
         String lobbyId = in.read_string ();
         App.Response $result = null;
         $result = this.submitWord (word, playerId, lobbyId);
         out = $rh.createReply();
         App.ResponseHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:App/LobbyServer:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public LobbyServer _this() 
  {
    return LobbyServerHelper.narrow(
    super._this_object());
  }

  public LobbyServer _this(org.omg.CORBA.ORB orb) 
  {
    return LobbyServerHelper.narrow(
    super._this_object(orb));
  }


} // class LobbyServerPOA
