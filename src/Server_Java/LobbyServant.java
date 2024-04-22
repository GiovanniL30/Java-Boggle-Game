package Server_Java;

import App.*;
import Server_Java.dataBase.Database;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.util.HashMap;

public class LobbyServant extends LobbyServerPOA {

    private HashMap<String, GameLobby> lobbies = new HashMap<>();


    @Override
    public Response createLobby(User creator, Controller clientController) {
        shared.referenceClasses.Response<String> dataBaseResponse = Database.createNewLobby(creator.userID);
        Any responseData = ORB.init().create_any();

        if(dataBaseResponse.isSuccess()) {
            responseData.insert_string(dataBaseResponse.getData());
            GameLobby gameLobby = new GameLobby(dataBaseResponse.getData());
            gameLobby.addPlayer(creator.userID, clientController);
            lobbies.put(dataBaseResponse.getData(),gameLobby);
            return new Response(responseData, true);
        }

        responseData.insert_string("Failed to create a new game lobby");
        return new Response(responseData, false);
    }

    @Override
    public boolean joinLobby(User user, String lobbyId, Controller clientController) {
        if(Database.addPlayer(user.userID, lobbyId)) {
            lobbies.get(lobbyId).addPlayer(user.userID, clientController);
            return true;
        }
        return false;
    }

    @Override
    public Response leaveLobby(User user, String lobbyId) {
        Any any = ORB.init().create_any();
        if(Database.removePlayer(user.userID, lobbyId)) {
            lobbies.get(lobbyId).removePlayer(user.userID);
            any.insert_string("Success");
            return new Response(any, true);
        }else {
            any.insert_string("Failed");
            return new Response(any, false);
        }
    }

    @Override
    public Lobby[] getLobbies() {
        return new Lobby[0];
    }
}