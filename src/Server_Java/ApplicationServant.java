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
    private final LobbyServant lobbyServant = new LobbyServant();

    @Override
    public User login(String userName, String password, Controller controller) {
        shared.referenceClasses.Response<User> response =  Database.logIn(userName, password);
        User user = response.getData();

       if(response.isSuccess()) {
           controllerHashMap.put(user.userID, controller);
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
    public Response createLobby(User creator, Controller clientController) {
        return lobbyServant.createLobby(creator, clientController);
    }

    @Override
    public boolean joinLobby(User user, String lobbyId, Controller clientController) {
        for(Controller controller : controllerHashMap.values()) {

            if(!controller.equals(clientController)) {
                controller.updateWaitingLobbyView(user);
            }

        }
        return lobbyServant.joinLobby(user, lobbyId, clientController);
    }


    @Override
    public Response leaveLobby(User user, String lobbyId) {
        return lobbyServant.leaveLobby(user, lobbyId);
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
