package Server_Java;

import App.*;
import Server_Java.dataBase.Database;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class ApplicationServant extends ApplicationServerPOA {

    private final HashMap<String, Controller> controllerHashMap = new HashMap<>();

    @Override
    public User login(String userName, String password, Controller controller) {
        shared.referenceClasses.Response<User> response =  Database.logIn(userName, password);
        User user = response.getData();

       if(response.isSuccess()) {
           controllerHashMap.put(user.userID, controller);

           for(Controller controller1 : controllerHashMap.values()) {
               if(!controller1.equals(controller)) {
                   controller1.receiveUpdates(ClientActions.NEW_MESSAGE);
               }
           }

           System.out.println("A new user Logged in total users online: " + controllerHashMap.size() );
       }

        return user;
    }

    @Override
    public void logout(String userID) {
        Database.logout(userID);
        controllerHashMap.remove(userID);
        System.out.println("A user logged out total users online: " + controllerHashMap.size() );
    }

    @Override
    public Response createAccount(User user) {
        shared.referenceClasses.Response<String> response = Database.createAccount(user);
        Any anyData = ORB.init().create_any();
        anyData.insert_string(response.getData());
        return new Response(anyData, response.isSuccess());
    }

    @Override
    public Response createLobby(User creator) {
        shared.referenceClasses.Response<String> dataBaseResponse = Database.createNewLobby(creator.userID);
        Any responseData = ORB.init().create_any();

        if(dataBaseResponse.isSuccess()) {
            responseData.insert_string(dataBaseResponse.getData());
            return new Response(responseData, true);
        }

        responseData.insert_string("Failed to create a new game lobby");
        return new Response(responseData, false);
    }

    @Override
    public boolean joinLobby(User user, String lobbyId) {
        return Database.addPlayer(user.userID, lobbyId);
    }

    @Override
    public Response leaveLobby(User user, String lobbyId) {
        return null;
    }

    @Override
    public Lobby[] getLobbies() {
        return Database.getLobbies().toArray(new Lobby[0]);
    }

    @Override
    public User[] getPlayers(String lobbyId) {
        return Database.lobbyPlayers(lobbyId);
    }

}
