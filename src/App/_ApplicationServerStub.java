package App;


/**
* App/_ApplicationServerStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Tuesday, 14 May, 2024 8:55:57 PM SGT
*/

public class _ApplicationServerStub extends org.omg.CORBA.portable.ObjectImpl implements App.ApplicationServer
{

  public App.User login (String userName, String password, App.Controller controller) throws App.LogInException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("login", true);
                $out.write_string (userName);
                $out.write_string (password);
                App.ControllerHelper.write ($out, controller);
                $in = _invoke ($out);
                App.User $result = App.UserHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:App/LogInException:1.0"))
                    throw App.LogInExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return login (userName, password, controller        );
            } finally {
                _releaseReply ($in);
            }
  } // login

  public void logout (String userID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("logout", true);
                $out.write_string (userID);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                logout (userID        );
            } finally {
                _releaseReply ($in);
            }
  } // logout

  public App.Response createAccount (App.User user) throws App.CreateException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("createAccount", true);
                App.UserHelper.write ($out, user);
                $in = _invoke ($out);
                App.Response $result = App.ResponseHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:App/CreateException:1.0"))
                    throw App.CreateExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return createAccount (user        );
            } finally {
                _releaseReply ($in);
            }
  } // createAccount

  public App.Response createLobby (App.User creator, App.Controller clientController) throws App.LobbyException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("createLobby", true);
                App.UserHelper.write ($out, creator);
                App.ControllerHelper.write ($out, clientController);
                $in = _invoke ($out);
                App.Response $result = App.ResponseHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:App/LobbyException:1.0"))
                    throw App.LobbyExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return createLobby (creator, clientController        );
            } finally {
                _releaseReply ($in);
            }
  } // createLobby

  public App.Response joinLobby (App.User user, String lobbyId, App.Controller clientController) throws App.LobbyException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("joinLobby", true);
                App.UserHelper.write ($out, user);
                $out.write_string (lobbyId);
                App.ControllerHelper.write ($out, clientController);
                $in = _invoke ($out);
                App.Response $result = App.ResponseHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:App/LobbyException:1.0"))
                    throw App.LobbyExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return joinLobby (user, lobbyId, clientController        );
            } finally {
                _releaseReply ($in);
            }
  } // joinLobby

  public App.Response leaveLobby (String user, String lobbyId) throws App.LobbyException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("leaveLobby", true);
                $out.write_string (user);
                $out.write_string (lobbyId);
                $in = _invoke ($out);
                App.Response $result = App.ResponseHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:App/LobbyException:1.0"))
                    throw App.LobbyExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return leaveLobby (user, lobbyId        );
            } finally {
                _releaseReply ($in);
            }
  } // leaveLobby

  public App.Lobby[] getLobbies ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getLobbies", true);
                $in = _invoke ($out);
                App.Lobby $result[] = App.LobbyListHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getLobbies (        );
            } finally {
                _releaseReply ($in);
            }
  } // getLobbies

  public App.User[] getPlayers (String lobbyId)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getPlayers", true);
                $out.write_string (lobbyId);
                $in = _invoke ($out);
                App.User $result[] = App.PlayersHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getPlayers (lobbyId        );
            } finally {
                _releaseReply ($in);
            }
  } // getPlayers

  public App.Response submitWord (String word, String playerId, String lobbyId)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("submitWord", true);
                $out.write_string (word);
                $out.write_string (playerId);
                $out.write_string (lobbyId);
                $in = _invoke ($out);
                App.Response $result = App.ResponseHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return submitWord (word, playerId, lobbyId        );
            } finally {
                _releaseReply ($in);
            }
  } // submitWord

  public void startGame (String lobbyId)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("startGame", true);
                $out.write_string (lobbyId);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                startGame (lobbyId        );
            } finally {
                _releaseReply ($in);
            }
  } // startGame

  public App.User[] getAllUsers ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getAllUsers", true);
                $in = _invoke ($out);
                App.User $result[] = App.PlayersHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getAllUsers (        );
            } finally {
                _releaseReply ($in);
            }
  } // getAllUsers

  public int[] getTime ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getTime", true);
                $in = _invoke ($out);
                int $result[] = App.GameTimesHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getTime (        );
            } finally {
                _releaseReply ($in);
            }
  } // getTime

  public void updateGameTime (int time)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("updateGameTime", true);
                $out.write_long (time);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                updateGameTime (time        );
            } finally {
                _releaseReply ($in);
            }
  } // updateGameTime

  public void updateWaitingTime (int time)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("updateWaitingTime", true);
                $out.write_long (time);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                updateWaitingTime (time        );
            } finally {
                _releaseReply ($in);
            }
  } // updateWaitingTime

  public App.Response banUser (String userId)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("banUser", true);
                $out.write_string (userId);
                $in = _invoke ($out);
                App.Response $result = App.ResponseHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return banUser (userId        );
            } finally {
                _releaseReply ($in);
            }
  } // banUser

  public App.Response unBanUser (String userId)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("unBanUser", true);
                $out.write_string (userId);
                $in = _invoke ($out);
                App.Response $result = App.ResponseHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return unBanUser (userId        );
            } finally {
                _releaseReply ($in);
            }
  } // unBanUser

  public App.Response deleteUserAccount (String userId)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deleteUserAccount", true);
                $out.write_string (userId);
                $in = _invoke ($out);
                App.Response $result = App.ResponseHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return deleteUserAccount (userId        );
            } finally {
                _releaseReply ($in);
            }
  } // deleteUserAccount

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:App/ApplicationServer:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     com.sun.corba.se.impl.orbutil.IORCheckImpl.check(str, "App._ApplicationServerStub");
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _ApplicationServerStub
