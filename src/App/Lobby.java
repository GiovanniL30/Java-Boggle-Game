package App;


/**
* App/Lobby.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Sunday, April 21, 2024 10:38:12 AM SGT
*/

public final class Lobby implements org.omg.CORBA.portable.IDLEntity
{
  public String lobbyId = null;
  public String lobbyStatus = null;
  public String dateCreated = null;
  public String timeCreated = null;
  public App.User topPlayer = null;

  public Lobby ()
  {
  } // ctor

  public Lobby (String _lobbyId, String _lobbyStatus, String _dateCreated, String _timeCreated, App.User _topPlayer)
  {
    lobbyId = _lobbyId;
    lobbyStatus = _lobbyStatus;
    dateCreated = _dateCreated;
    timeCreated = _timeCreated;
    topPlayer = _topPlayer;
  } // ctor

} // class Lobby
