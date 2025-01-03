package App;


/**
* App/ApplicationServerPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Thursday, May 16, 2024 10:07:05 AM CST
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
    _methods.put ("submitWord", new java.lang.Integer (8));
    _methods.put ("startGame", new java.lang.Integer (9));
    _methods.put ("getAllUsers", new java.lang.Integer (10));
    _methods.put ("getTime", new java.lang.Integer (11));
    _methods.put ("updateGameTime", new java.lang.Integer (12));
    _methods.put ("updateWaitingTime", new java.lang.Integer (13));
    _methods.put ("banUser", new java.lang.Integer (14));
    _methods.put ("unBanUser", new java.lang.Integer (15));
    _methods.put ("deleteUserAccount", new java.lang.Integer (16));
    _methods.put ("getPlayerWithOnlineStatus", new java.lang.Integer (17));
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
         try {
           String userName = in.read_string ();
           String password = in.read_string ();
           App.Controller controller = App.ControllerHelper.read (in);
           App.User $result = null;
           $result = this.login (userName, password, controller);
           out = $rh.createReply();
           App.UserHelper.write (out, $result);
         } catch (App.LogInException $ex) {
           out = $rh.createExceptionReply ();
           App.LogInExceptionHelper.write (out, $ex);
         }
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
         try {
           App.User user = App.UserHelper.read (in);
           App.Response $result = null;
           $result = this.createAccount (user);
           out = $rh.createReply();
           App.ResponseHelper.write (out, $result);
         } catch (App.CreateException $ex) {
           out = $rh.createExceptionReply ();
           App.CreateExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // App/ApplicationServer/createLobby
       {
         try {
           App.User creator = App.UserHelper.read (in);
           App.Controller clientController = App.ControllerHelper.read (in);
           App.Response $result = null;
           $result = this.createLobby (creator, clientController);
           out = $rh.createReply();
           App.ResponseHelper.write (out, $result);
         } catch (App.LobbyException $ex) {
           out = $rh.createExceptionReply ();
           App.LobbyExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 4:  // App/ApplicationServer/joinLobby
       {
         try {
           App.User user = App.UserHelper.read (in);
           String lobbyId = in.read_string ();
           App.Controller clientController = App.ControllerHelper.read (in);
           App.Response $result = null;
           $result = this.joinLobby (user, lobbyId, clientController);
           out = $rh.createReply();
           App.ResponseHelper.write (out, $result);
         } catch (App.LobbyException $ex) {
           out = $rh.createExceptionReply ();
           App.LobbyExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // App/ApplicationServer/leaveLobby
       {
         try {
           String user = in.read_string ();
           String lobbyId = in.read_string ();
           App.Response $result = null;
           $result = this.leaveLobby (user, lobbyId);
           out = $rh.createReply();
           App.ResponseHelper.write (out, $result);
         } catch (App.LobbyException $ex) {
           out = $rh.createExceptionReply ();
           App.LobbyExceptionHelper.write (out, $ex);
         }
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

       case 8:  // App/ApplicationServer/submitWord
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

       case 9:  // App/ApplicationServer/startGame
       {
         String lobbyId = in.read_string ();
         this.startGame (lobbyId);
         out = $rh.createReply();
         break;
       }

       case 10:  // App/ApplicationServer/getAllUsers
       {
         App.User $result[] = null;
         $result = this.getAllUsers ();
         out = $rh.createReply();
         App.PlayersHelper.write (out, $result);
         break;
       }

       case 11:  // App/ApplicationServer/getTime
       {
         int $result[] = null;
         $result = this.getTime ();
         out = $rh.createReply();
         App.GameTimesHelper.write (out, $result);
         break;
       }

       case 12:  // App/ApplicationServer/updateGameTime
       {
         int time = in.read_long ();
         this.updateGameTime (time);
         out = $rh.createReply();
         break;
       }

       case 13:  // App/ApplicationServer/updateWaitingTime
       {
         int time = in.read_long ();
         this.updateWaitingTime (time);
         out = $rh.createReply();
         break;
       }

       case 14:  // App/ApplicationServer/banUser
       {
         String userId = in.read_string ();
         App.Response $result = null;
         $result = this.banUser (userId);
         out = $rh.createReply();
         App.ResponseHelper.write (out, $result);
         break;
       }

       case 15:  // App/ApplicationServer/unBanUser
       {
         String userId = in.read_string ();
         App.Response $result = null;
         $result = this.unBanUser (userId);
         out = $rh.createReply();
         App.ResponseHelper.write (out, $result);
         break;
       }

       case 16:  // App/ApplicationServer/deleteUserAccount
       {
         String userId = in.read_string ();
         App.Response $result = null;
         $result = this.deleteUserAccount (userId);
         out = $rh.createReply();
         App.ResponseHelper.write (out, $result);
         break;
       }

       case 17:  // App/ApplicationServer/getPlayerWithOnlineStatus
       {
         App.User $result[] = null;
         $result = this.getPlayerWithOnlineStatus ();
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
