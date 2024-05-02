package App;


/**
* App/_LobbyServerStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Friday, May 3, 2024 3:12:17 AM SGT
*/

public class _LobbyServerStub extends org.omg.CORBA.portable.ObjectImpl implements App.LobbyServer
{

  public App.Response createLobby (App.User creator, App.Controller clientController)
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
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return createLobby (creator, clientController        );
            } finally {
                _releaseReply ($in);
            }
  } // createLobby

  public boolean joinLobby (App.User user, String lobbyId, App.Controller clientController)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("joinLobby", true);
                App.UserHelper.write ($out, user);
                $out.write_string (lobbyId);
                App.ControllerHelper.write ($out, clientController);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return joinLobby (user, lobbyId, clientController        );
            } finally {
                _releaseReply ($in);
            }
  } // joinLobby

  public App.Response leaveLobby (String user, String lobbyId)
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

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:App/LobbyServer:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
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
} // class _LobbyServerStub
