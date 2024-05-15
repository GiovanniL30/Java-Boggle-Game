package Server_Java;

import App.*;
import Server_Java.dataBase.Database;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;


import javax.xml.crypto.Data;
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

           if(controllerHashMap.containsKey(user.userID)) {
               return new User("You are already logged in on another machine", "", "", "", "", 0);
           }

           controllerHashMap.put(user.userID, controller);
           System.out.println("A new user Logged in total users online: " + controllerHashMap.size() );
       }

        return user;
    }

    @Override
    public void logout(String userID) {
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
    public Response joinLobby(User user, String lobbyId, Controller clientController) {

        Any any = ORB.init().create_any();

        if(clientController == null) {
            any.insert_string("Connection CORBA Error!");
            return new Response(any, false);
        }

        if(!Database.lobbyExist(lobbyId)) {
            any.insert_string("Lobby not found");
            return new Response(any, false);
        }

        if(Database.isLobbyStarted(lobbyId)) {
            any.insert_string("Lobby already started, (CANNOT JOIN)");
            return new Response(any, false);
        }

        if(lobbyServant.joinLobby(user, lobbyId, clientController)) {
            for(Controller controller : controllerHashMap.values()) {

                if(!controller.equals(clientController)) {
                    controller.updatePlayerListView();
                }

            }

            any.insert_string("SUCCESS");
            return new Response(any, true);
        }


        any.insert_string("Having error joining the lobby");
        return new Response(any, false);
    }


    @Override
    public Response leaveLobby(String user, String lobbyId) {
        Response response = lobbyServant.leaveLobby(user, lobbyId);

        if(response.isSuccess) {
            for(Controller controller : controllerHashMap.values()) {
                controller.updatePlayerListView();
            }

        }

        return response;
    }

    @Override
    public Lobby[] getLobbies() {
        return Database.getLobbies().toArray(new Lobby[0]);
    }

    @Override
    public User[] getPlayers(String lobbyId) {
        return Database.lobbyPlayers(lobbyId);
    }

    @Override
    public Response submitWord(String word, String playerId, String lobbyId) {
        return lobbyServant.submitWord(word, playerId, lobbyId);
    }

    @Override
    public void startGame(String lobbyId) {
        Database.startGame(lobbyId);
    }

    @Override
    public User[] getAllUsers() {
        return Database.getPlayers();
    }

    @Override
    public int[] getTime() {
        return Database.getTime();
    }

    @Override
    public void updateGameTime(int time) {
        Database.updateGameTime(time);
    }

    @Override
    public void updateWaitingTime(int time) {
        Database.updateWaitingTime(time);
    }

    @Override
    public Response banUser(String userId) {
        Response response = Database.banUser(userId);

        if(response.isSuccess) {

            if(controllerHashMap.containsKey(userId)) {
                controllerHashMap.get(userId).receiveBanNotification();
            }

        }
        return response;
    }

    @Override
    public Response unBanUser(String userId) {
        return Database.unBanUser(userId);
    }

    @Override
    public Response deleteUserAccount(String userId) {
        Response response = Database.deleteUser(userId);

        if(response.isSuccess) {

            if(controllerHashMap.containsKey(userId)) {
                controllerHashMap.get(userId).receiveDeleteAccountNotification();
            }

        }
        return response;
    }

}
